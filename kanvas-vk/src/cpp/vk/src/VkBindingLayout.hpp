//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_BINDING_HPP
#define STC_BINDING_HPP

#include "../api/Vk.h"

#include <vector>

struct VkBindingLayout {
    VkDevice device = nullptr;
    VkDescriptorSetLayout layout;
    VkDescriptorSet set = VK_NULL_HANDLE;
    u32 setIndex = 0;
    VkBindingInfo info;
    std::vector<u32> dynamicOffsets;

    VkBindingLayout(VkDevice device, const VkBindingInfo& info);
    ~VkBindingLayout();

    void update(const VkBindingInfo& newInfo);
};

#endif //STC_BINDING_HPP