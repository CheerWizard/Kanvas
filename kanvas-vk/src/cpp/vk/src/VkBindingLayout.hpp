//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_BINDING_HPP
#define STC_BINDING_HPP

#include "../api/Vk.h"

struct VkBindingLayout {
    VkDevice device = nullptr;
    VkDescriptorSetLayout layout;
    VkBindingInfo info;

    VkBindingLayout(VkDevice device, const VkBindingInfo& info);
    ~VkBindingLayout();

    void update(const VkBindingInfo& newInfo);
};

#endif //STC_BINDING_HPP