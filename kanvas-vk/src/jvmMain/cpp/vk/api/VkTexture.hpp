//
// Created by cheerwizard on 18.10.25.
//

#ifndef TEXTURE_HPP
#define TEXTURE_HPP

#include "VkCommon.hpp"
#include "Vk.h"

struct VkSamplerResource {
    VkDevice device = nullptr;
    VkSampler sampler = nullptr;
    VkDescriptorSet set = nullptr;
    VkSamplerInfo info;

    VkSamplerResource(VkDevice device, const VkSamplerInfo& info);
    ~VkSamplerResource();

private:
    static constexpr auto TAG = "VkSamplerResource";
};

struct VkTextureResource {
    VkDevice device = nullptr;
    VkImage image = nullptr;
    VkImageView view = nullptr;
    VmaAllocation allocation = {};
    void* mapped = nullptr;
    VkTextureInfo info;

    VkTextureResource(VkDevice device, const VkTextureInfo& info);
    ~VkTextureResource();

    void* map();
    void unmap();

private:
    static constexpr auto TAG = "VkTextureResource";
};

#endif //TEXTURE_HPP