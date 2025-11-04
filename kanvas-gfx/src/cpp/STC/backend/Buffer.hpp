//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_BUFFER_HPP
#define STC_BUFFER_HPP

#include "Handle.hpp"
#include "Binding.hpp"

#define PTR_OFFSET(T) (void*)((char*)mapped + frame * sizeof(T) + offset * sizeof(T))

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

    struct Buffer : BufferBackend {
        u32 slot = 0;
        size_t size = 0;
        void* mapped = nullptr;

        Buffer(const Device& device, MemoryType memoryType, u32 usages, size_t size);
        ~Buffer();

        void* map();
        void unmap();

        void updateBinding(const Device& device, const BindingLayout& binding_layout, Binding& binding);

    private:
        static constexpr auto TAG = "Buffer";
    };

}

#endif //STC_BUFFER_HPP