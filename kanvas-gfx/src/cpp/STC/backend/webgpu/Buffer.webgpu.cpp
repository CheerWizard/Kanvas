//
// Created by cheerwizard on 18.10.25.
//

#include "../Buffer.hpp"
#include "backend/Device.hpp"
#include "core/logger.hpp"

namespace stc {

    Buffer::Buffer(const Device& device, MemoryType memoryType, u32 usages, size_t size) : size(size) {
        New(device.handle, WGPUBufferDescriptor {
            .label = "Buffer",
            .size = size,
            .usage = (WGPUBufferUsage) usages,
            .mappedAtCreation = false,
        });
    }

    Buffer::~Buffer() {
        unmap();
        if (handle) {
            vmaDestroyBuffer(VulkanAllocator::getInstance().allocator, handle, allocation);
            handle = null;
        }
    }

    void* Buffer::map() {
        ASSERT(!mapped, TAG, "Failed to map buffer memory");
        wgpuBufferMapAsync(
            handle,
            WGPUMapMode_Write,
            0,
            size,
            [](WGPUBufferMapAsyncStatus status, void* userdata) {
                Buffer* buffer = (Buffer*) userdata;
                if (status != WGPUBufferMapAsyncStatus_Success) {
                    LOG_ERROR(TAG, "Failed to async map buffer status=%d", status);
                    return;
                }
                buffer->mapped = wgpuBufferGetMappedRange(buffer->handle, 0, buffer->size);
        }, this);
        return mapped;
    }

    void Buffer::unmap() {
        if (mapped) {
            wgpuBufferUnmap(handle);
            mapped = nullptr;
        }
    }

    void Buffer::updateBinding(BindingLayout& binding_layout, size_t size) {
        WGPUBufferBinding bufferBinding = {};
        bufferBinding.buffer = handle;
        bufferBinding.offset = 0;
        bufferBinding.size = size;

        WGPUBindGroupEntry entry = {};
        entry.binding = binding.slot;
        entry.buffer = bufferBinding;

        WGPUBindGroupDescriptor bindGroupDesc = {};
        bindGroupDesc.layout = binding_layout.layout.handle;
        bindGroupDesc.entryCount = 1;
        bindGroupDesc.entries = &entry;

        binding_layout.group.Delete();
        binding_layout.group.New(device, bindGroupDesc);
    }

}
