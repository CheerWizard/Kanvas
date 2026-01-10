//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_LISTBUFFER_HPP
#define STC_LISTBUFFER_HPP

#include "backend/Buffer.hpp"
#include "backend/Binding.hpp"

namespace stc {

    struct StorageBufferCreateInfo {
        Binding binding;
        size_t itemCount = 0;
        size_t itemSize = 0;
    };

    struct StorageBuffer : Buffer {
        StorageBufferCreateInfo info;

        StorageBuffer(const Device& device, const StorageBufferCreateInfo& create_info);

        void update(u32 frame, size_t offset, void* item);
        void update(u32 frame, size_t offset, void** items, size_t itemCount);
    };

}

#endif //STC_LISTBUFFER_HPP