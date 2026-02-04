//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_FRAMEBUFFER_HPP
#define STC_FRAMEBUFFER_HPP

#include "../api/Vk.h"
#include "VkCommon.hpp"

struct VkRenderTarget {
    VkContext* context = nullptr;
    VkRenderPass render_pass;
    std::vector<VkFramebuffer> framebuffers;
    VkRenderTargetInfo info;

    VkRenderTarget(VkContext* context, const VkRenderTargetInfo& info);
    ~VkRenderTarget();

    void resize(u32 width, u32 height);
};

#endif //STC_FRAMEBUFFER_HPP