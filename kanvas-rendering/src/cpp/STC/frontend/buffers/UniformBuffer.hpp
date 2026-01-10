//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_ITEMBUFFER_HPP
#define STC_ITEMBUFFER_HPP

#include "backend/Buffer.hpp"
#include "backend/Device.hpp"

namespace stc {

    struct UniformBufferCreateInfo {
        Binding binding;
        size_t itemSize = 0;
    };

    struct UniformBuffer : Buffer {
        UniformBufferCreateInfo info;

        UniformBuffer(const Device& device, const UniformBufferCreateInfo& create_info);

        void update(u32 frame, void* item);
    };

}

#endif //STC_ITEMBUFFER_HPP