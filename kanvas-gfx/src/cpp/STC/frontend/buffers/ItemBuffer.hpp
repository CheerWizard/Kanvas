//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_ITEMBUFFER_HPP
#define STC_ITEMBUFFER_HPP

#include "backend/Buffer.hpp"
#include "backend/Binding.hpp"
#include "backend/Device.hpp"

namespace stc {

    template<typename T, Binding _binding>
    struct ItemBuffer : Buffer {
        BindingLayout binding_layout;
        Binding binding = _binding;

        ItemBuffer(const Device& device);
        void update(u32 frame, const T& data);
    };

    template<typename T, Binding _binding>
    ItemBuffer<T, _binding>::ItemBuffer(const Device &device)
    : binding_layout(device, { binding }),
      Buffer(device, MEMORY_TYPE_HOST, BUFFER_USAGE_UNIFORM_BUFFER, sizeof(T) * MAX_FRAMES)
    {
        updateBinding(device, binding_layout, binding);
        map();
    }

    template<typename T, Binding _binding>
    void ItemBuffer<T, _binding>::update(u32 frame, const T &data) {
        memcpy((void*)((char*) mapped + frame * sizeof(T)), &data, sizeof(T));
    }

}

#endif //STC_ITEMBUFFER_HPP