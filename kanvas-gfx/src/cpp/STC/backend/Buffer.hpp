//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_BUFFER_HPP
#define STC_BUFFER_HPP

#include "Shader.hpp"

#define PTR_OFFSET(type_size) (void*)((char*)mapped + frame * type_size + offset * type_size)
#define PTR_OFFSET_T(T) PTR_OFFSET(sizeof(T))

namespace stc {

#ifdef VK

    struct BufferBackend {
        BufferHandle handle = null;
        VmaAllocation allocation = {};
    };

    enum BufferUsage : u32 {
        BUFFER_USAGE_TRANSFER_SRC = VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
        BUFFER_USAGE_TRANSFER_DST = VK_BUFFER_USAGE_TRANSFER_DST_BIT,
        BUFFER_USAGE_UNIFORM_TEXEL = VK_BUFFER_USAGE_UNIFORM_TEXEL_BUFFER_BIT,
        BUFFER_USAGE_STORAGE_TEXEL = VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT,
        BUFFER_USAGE_UNIFORM_BUFFER = VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT,
        BUFFER_USAGE_STORAGE_BUFFER = VK_BUFFER_USAGE_STORAGE_BUFFER_BIT,
        BUFFER_USAGE_INDEX_BUFFER = VK_BUFFER_USAGE_INDEX_BUFFER_BIT,
        BUFFER_USAGE_VERTEX_BUFFER = VK_BUFFER_USAGE_VERTEX_BUFFER_BIT,
        BUFFER_USAGE_INDIRECT_BUFFER = VK_BUFFER_USAGE_INDIRECT_BUFFER_BIT,
        // BUFFER_USAGE_MAP_READ = VK_BUFFER_USAGE_MAPREAD,
        // BUFFER_USAGE_MAP_WRITE = WGPUBufferUsage_MapWrite,
    };

#elif METAL

#elif WEBGPU

    struct BufferBackend : BufferHandle {};

    enum BufferUsage : u32 {
        BUFFER_USAGE_TRANSFER_SRC = WGPUBufferUsage_CopySrc,
        BUFFER_USAGE_TRANSFER_DST = WGPUBufferUsage_CopyDst,
        BUFFER_USAGE_UNIFORM_TEXEL = WGPUBufferUsage_Uniform,
        BUFFER_USAGE_STORAGE_TEXEL = WGPUBufferUsage_Storage,
        BUFFER_USAGE_UNIFORM_BUFFER = WGPUBufferUsage_Uniform,
        BUFFER_USAGE_STORAGE_BUFFER = WGPUBufferUsage_Storage,
        BUFFER_USAGE_INDEX_BUFFER = WGPUBufferUsage_Index,
        BUFFER_USAGE_VERTEX_BUFFER = WGPUBufferUsage_Vertex,
        BUFFER_USAGE_INDIRECT_BUFFER = WGPUBufferUsage_Indirect,
        BUFFER_USAGE_MAP_READ = WGPUBufferUsage_MapRead,
        BUFFER_USAGE_MAP_WRITE = WGPUBufferUsage_MapWrite,
    };

#endif

    struct BufferCreateInfo {
        MemoryType memoryType;
        u32 usages;
        size_t size;
        bool mapOnCreate = false;
    };

    struct Buffer : BufferBackend, Resource {
        BufferCreateInfo info;
        void* mapped = nullptr;

        Buffer(const Device& device, const BufferCreateInfo& create_info);
        ~Buffer();

        void* map();
        void unmap();

        operator BufferHandle() const {
            return handle;
        }

    private:
        static constexpr auto TAG = "Buffer";
    };

}

#endif //STC_BUFFER_HPP