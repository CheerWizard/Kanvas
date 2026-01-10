//
// Created by cheerwizard on 07.11.25.
//

#ifndef STC_RESOURCE_HPP
#define STC_RESOURCE_HPP

#include "Handle.hpp"

namespace stc {

#ifdef VK

    struct Resource {
        VkDescriptorSet set = null;
    };

#elif METAL

    struct Resource {
        MTL::ArgumentEncoder* handle = null;
    };

#elif WEBGPU

    struct Resource {
        BindGroupHandle group;
    };

#endif

}

#endif //STC_RESOURCE_HPP