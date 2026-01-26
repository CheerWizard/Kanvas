//
// Created by cheerwizard on 18.10.25.
//

#ifndef VK_BUFFER_HPP
#define VK_BUFFER_HPP

#include "../api/Vk.h"
#include "VkCommon.hpp"

#define PTR_OFFSET(type_size) (void*)((char*)mapped + frame * type_size + offset * type_size)
#define PTR_OFFSET_T(T) PTR_OFFSET(sizeof(T))

struct VkBufferResource {
    VkDevice device = nullptr;
    VkBuffer buffer = nullptr;
    VmaAllocation allocation = {};
    VkBufferInfo info;
    void* mapped = nullptr;

    VkBufferResource(VkDevice device, const VkBufferInfo& info);
    ~VkBufferResource();

    void* map();
    void unmap();

    void updateBinding(u32 frame);

private:
    static constexpr auto TAG = "VkBufferResource";
};

#endif //VK_BUFFER_HPP