//
// Created by cheerwizard on 18.10.25.
//

#include "../Buffer.hpp"
#include "backend/Device.hpp"
#include "core/logger.hpp"

namespace stc {

    Buffer::Buffer(const Device& device, const BufferCreateInfo& create_info) : info(create_info) {
        New(device.handle, WGPUBufferDescriptor {
            .label = "Buffer",
            .size = create_info.size,
            .usage = create_info.usages,
            .mappedAtCreation = create_info.mapOnCreate,
        });
    }

    Buffer::~Buffer() {
        unmap();
        Delete();
    }

    void* Buffer::map() {
        ASSERT(!mapped, TAG, "Failed to map buffer memory");
        wgpuBufferMapAsync(
            handle,
            WGPUMapMode_Write,
            0,
            info.size,
            [](WGPUBufferMapAsyncStatus status, void* userdata) {
                Buffer* buffer = (Buffer*) userdata;
                if (status != WGPUBufferMapAsyncStatus_Success) {
                    LOG_ERROR(TAG, "Failed to async map buffer status=%d", status);
                    return;
                }
                buffer->mapped = wgpuBufferGetMappedRange(buffer->handle, 0, buffer->info.size);
        }, this);
        return mapped;
    }

    void Buffer::unmap() {
        if (mapped) {
            wgpuBufferUnmap(handle);
            mapped = nullptr;
        }
    }

    void Buffer::updateBinding(const Device& device, const Binding& binding) {
        WGPUBindGroupEntry entry = {};
        entry.binding = binding.slot;
        entry.offset = 0;
        entry.size = info.size;

        WGPUBindGroupDescriptor bindGroupDesc = {};
        bindGroupDesc.layout = binding_layout.layout.handle;
        bindGroupDesc.entryCount = 1;
        bindGroupDesc.entries = &entry;

        group.New(device, bindGroupDesc);
    }

}
