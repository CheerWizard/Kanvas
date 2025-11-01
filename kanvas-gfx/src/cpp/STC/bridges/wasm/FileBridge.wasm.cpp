//
// Created by cheerwizard on 01.11.25.
//

#include <emscripten/emscripten.h>

#include "../FileBridge.hpp"

extern "C" {

    EM_JS(char*, loadFileAsync, (const char* filepath), {
        const url = UTF8ToString(filepath);
        try {
          const response = await fetch(url);
          if (!response.ok) {
            console.error("Failed to load file: ", url, response.status);
            return 0;
          }
          const arrayBuffer = await response.arrayBuffer();
          const size = arrayBuffer.byteLength;
          const ptr = _malloc(size + 1);
          const heap = new Uint8Array(arrayBuffer);
          HEAPU8.set(heap, ptr);
          HEAPU8[ptr + size] = 0;
          return ptr;
        } catch (err) {
          console.error("Fetch error: ", err);
          return 0;
        }
    });

    EM_JS(u8*, loadBinaryFileAsync, (const char* filepath, int* outSize), {
          const url = UTF8ToString(filepath);
          const response = await fetch(url);
          const buffer = await response.arrayBuffer();
          const size = buffer.byteLength;
          const ptr = _malloc(size);
          HEAPU8.set(new Uint8Array(buffer), ptr);
          HEAP32[outSize >> 2] = size;
          return ptr;
    });

}

namespace stc {

    std::string FileBridge::loadFile(const char* filepath) {
        char* data = loadFileAsync(filepath);
        if (!data) {
            LOG_ERROR(TAG, "Failed to load file from %s", filepath);
            return "";
        }
        std::string string(data);
        free(data);
        return string;
    }

    std::vector<u32> FileBridge::loadBinaryFile(const char* filepath) {
        int size = 0;
        u8* data = loadBinaryFileAsync(filepath, &size);
        std::vector<u32> bytecode(size / 4);
        memcpy(bytecode.data(), data, size);
        free(data);
        return bytecode;
    }

}