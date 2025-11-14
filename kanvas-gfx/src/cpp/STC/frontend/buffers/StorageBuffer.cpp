//
// Created by cheerwizard on 07.11.25.
//

#include "StorageBuffer.hpp"

namespace stc {

    StorageBuffer::StorageBuffer(const Device& device, const StorageBufferCreateInfo& create_info)
    : info(create_info), Buffer(device, BufferCreateInfo {
        MEMORY_TYPE_HOST,
        BUFFER_USAGE_STORAGE_BUFFER,
        create_info.itemSize * create_info.itemCount
    }) {}

    void StorageBuffer::update(u32 frame, size_t offset, void* item) {
        if (!mapped) map();
        memcpy(PTR_OFFSET(info.itemSize), item, info.itemSize);
    }

    void StorageBuffer::update(u32 frame, size_t offset, void** items, size_t itemCount) {
        if (!mapped) map();
        memcpy(PTR_OFFSET(info.itemSize), items[0], info.itemSize * itemCount);
    }

}