//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_LISTBUFFER_HPP
#define STC_LISTBUFFER_HPP

#include "backend/Buffer.hpp"
#include "backend/Binding.hpp"

namespace stc {

    template<typename T, size_t count, Binding _binding>
    struct ListBuffer : Buffer {
        BindingLayout binding_layout;
        Binding binding = _binding;

        ListBuffer(const Device& device);

        void update(u32 frame, u32 offset, const T& data);
        void update(u32 frame, u32 offset, T* data, u32 dataCount);
    };

    template<typename T, size_t count, Binding _binding>
    ListBuffer<T, count, _binding>::ListBuffer(const Device& device)
    : binding_layout(device, { binding }),
    Buffer(device, MEMORY_TYPE_HOST, BUFFER_USAGE_STORAGE_BUFFER, sizeof(T) * count) {
        updateBinding(device, binding_layout, binding);
        map();
    }

    template<typename T, size_t count, Binding _binding>
    void ListBuffer<T, count, _binding>::update(u32 frame, u32 offset, const T &data) {
        memcpy(PTR_OFFSET(T), &data, sizeof(T));
    }

    template<typename T, size_t count, Binding _binding>
    void ListBuffer<T, count, _binding>::update(u32 frame, u32 offset, T *data, u32 dataCount) {
        memcpy(PTR_OFFSET(T), &data, sizeof(T) * dataCount);
    }

}

#endif //STC_LISTBUFFER_HPP