//
// Created by cheerwizard on 18.10.25.
//

#ifndef VK_SAMPLER_RESOURCE_HPP
#define VK_SAMPLER_RESOURCE_HPP

#include "VkCommon.hpp"
#include "../api/Vk.h"

struct VkSamplerResource {
    VkContext* context = nullptr;
    VkSampler sampler = nullptr;
    VkSamplerInfo info;

    VkSamplerResource(VkContext* context, const VkSamplerInfo& info);
    ~VkSamplerResource();

    void updateBinding(u32 frame);

private:
    static constexpr auto TAG = "VkSamplerResource";
};

#endif //VK_SAMPLER_RESOURCE_HPP