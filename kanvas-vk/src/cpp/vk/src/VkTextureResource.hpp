//
// Created by cheerwizard on 18.10.25.
//

#ifndef TEXTURE_HPP
#define TEXTURE_HPP

#include "VkCommon.hpp"
#include "../api/Vk.h"

struct VkTextureResource {
    VkContext* context = nullptr;
    VkImage image = nullptr;
    std::vector<VkImageView> views;
    VmaAllocation allocation = {};
    void* mapped = nullptr;
    VkTextureInfo info;

    VkTextureResource(VkContext* context, const VkTextureInfo& info);
    ~VkTextureResource();

    void* map();
    void unmap();

    void resize(u32 width, u32 height);

    void updateBinding(u32 frame);

private:
    static constexpr auto TAG = "VkTextureResource";
};

#endif //TEXTURE_HPP