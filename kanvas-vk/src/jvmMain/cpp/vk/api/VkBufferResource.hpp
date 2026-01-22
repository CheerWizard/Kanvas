//
// Created by cheerwizard on 18.10.25.
//

#ifndef VK_BUFFER_HPP
#define VK_BUFFER_HPP

#include "VkCommon.hpp"

#define PTR_OFFSET(type_size) (void*)((char*)mapped + frame * type_size + offset * type_size)
#define PTR_OFFSET_T(T) PTR_OFFSET(sizeof(T))

struct VkBufferResource {
    VkDevice device = nullptr;
    VkBuffer buffer = nullptr;
    VmaAllocation allocation = {};
    VkMemoryType memoryType;
    u32 usages;
    size_t size;
    bool mapOnCreate = false;
    void* mapped = nullptr;

    VkBufferResource(
        VkDevice device,
        VkMemoryType memoryType,
        u32 usages,
        size_t size,
        bool mapOnCreate = false
    );
    ~VkBufferResource();

    void* map();
    void unmap();

private:
    static constexpr auto TAG = "VkBufferResource";
};

#endif //VK_BUFFER_HPP