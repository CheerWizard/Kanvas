//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_ITEMBUFFER_HPP
#define STC_ITEMBUFFER_HPP

#include "backend/Buffer.hpp"
#include "backend/Binding.hpp"
#include "backend/Device.hpp"

namespace stc {

    template<typename T, Binding binding>
    struct ItemBuffer : Buffer {
        BindingLayout binding_layout;
        BindingSetPool binding_set_pool;
        BindingSet binding_set;

        ItemBuffer(const Device& device);
        void update(u32 frame, const T& data);
    };

    template<typename T, Binding binding>
    ItemBuffer<T, binding>::ItemBuffer(const Device &device)
    : binding_layout(device, { binding }),
    binding_set_pool(device, binding.type, sizeof(T)),
    binding_set(binding_set_pool, binding_layout),
    Buffer(MEMORY_TYPE_HOST, BUFFER_USAGE_UNIFORM_BUFFER, sizeof(T) * MAX_FRAMES)
    {
        updateBinding(binding, binding_set, sizeof(T));
        map();
    }

    template<typename T, Binding binding>
    void ItemBuffer<T, binding>::update(u32 frame, const T &data) {
        memcpy((void*)((char*)mapped + frame * sizeof(T)), &data, sizeof(T));
    }

}

#endif //STC_ITEMBUFFER_HPP