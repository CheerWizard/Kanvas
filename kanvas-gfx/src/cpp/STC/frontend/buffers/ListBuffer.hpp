//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_LISTBUFFER_HPP
#define STC_LISTBUFFER_HPP

#include "backend/Buffer.hpp"
#include "backend/Binding.hpp"
#include "backend/Device.hpp"

namespace stc {

    template<typename T, size_t count, Binding binding>
    struct ListBuffer : Buffer {
        BindingLayout binding_layout;
        BindingSetPool binding_set_pool;
        BindingSet binding_set;

        ListBuffer(const Device& device);

        void update(u32 frame, u32 offset, const T& data);
        void update(u32 frame, u32 offset, T* data, u32 dataCount);
    };

    template<typename T, size_t count, Binding binding>
    ListBuffer<T, count, binding>::ListBuffer(const Device& device)
    : binding_layout(device, { binding }),
    binding_set_pool(device, binding.type, sizeof(T) * count),
    binding_set(binding_set_pool, binding_layout),
    Buffer(MEMORY_TYPE_HOST, BUFFER_USAGE_STORAGE_BUFFER, sizeof(T) * count) {
        updateBinding(binding, binding_set, sizeof(T) * count);
        map();
    }

    template<typename T, size_t count, Binding binding>
    void ListBuffer<T, count, binding>::update(u32 frame, u32 offset, const T &data) {
        memcpy(PTR_OFFSET(T), &data, sizeof(T));
    }

    template<typename T, size_t count, Binding binding>
    void ListBuffer<T, count, binding>::update(u32 frame, u32 offset, T *data, u32 dataCount) {
        memcpy(PTR_OFFSET(T), &data, sizeof(T) * dataCount);
    }

}

#endif //STC_LISTBUFFER_HPP