//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_FRAMEBUFFER_HPP
#define STC_FRAMEBUFFER_HPP

#include "Vk.h"
#include "VkCommon.hpp"

struct VkRenderTarget {
    VkDevice device = nullptr;
    VkRenderPass render_pass = nullptr;
    VkFramebuffer framebuffer = nullptr;
    VkRenderTargetInfo info;

    VkRenderTarget(VkDevice device, const VkRenderTargetInfo& info);
    ~VkRenderTarget();
};

#endif //STC_FRAMEBUFFER_HPP