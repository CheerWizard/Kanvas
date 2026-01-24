//
// Created by cheerwizard on 18.10.25.
//

#ifndef VULKANALLOCATOR_HPP
#define VULKANALLOCATOR_HPP

#include <vulkan/vulkan_core.h>
#include "vk_mem_alloc.h"

#define VK_CALLBACKS &VulkanAllocator::getInstance().callbacks
#define VK_ALLOCATOR VulkanAllocator::getInstance().allocator

struct VulkanAllocator {

    VkAllocationCallbacks callbacks = {};
    VmaAllocator allocator = {};

    VulkanAllocator();

    static VulkanAllocator& getInstance();

    void New(VkInstance instance, VkPhysicalDevice physicalDevice, VkDevice device);
    void Delete() const;

    void* allocate(size_t size, size_t alignment, VkSystemAllocationScope scope);
    void free(void* address);
    void* reallocate(void* oldAddress, size_t size, size_t alignment, VkSystemAllocationScope scope);
    void allocateNotification(size_t size, VkInternalAllocationType type, VkSystemAllocationScope scope);
    void freeNotification(size_t size, VkInternalAllocationType type, VkSystemAllocationScope scope);

private:
    static constexpr auto TAG = "VulkanAllocator";
};

#endif //VULKANALLOCATOR_HPP