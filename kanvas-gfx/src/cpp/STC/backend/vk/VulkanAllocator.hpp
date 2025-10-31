//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_VULKANALLOCATOR_HPP
#define STC_VULKANALLOCATOR_HPP

#ifdef VK

#include <vulkan/vulkan_core.h>
#include "vk_mem_alloc.h"

namespace stc {

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

}

#endif

#endif //STC_VULKANALLOCATOR_HPP