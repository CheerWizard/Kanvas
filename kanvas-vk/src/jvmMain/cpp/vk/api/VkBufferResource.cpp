//
// Created by cheerwizard on 18.10.25.
//

#include "DescriptorPools.hpp"
#include "VkBufferResource.hpp"

VkBufferResource::VkBufferResource(
        VkDevice device,
        VkMemoryType memoryType,
        u32 usages,
        size_t size,
        bool mapOnCreate
) : device(device), memoryType(memoryType), usages(usages), size(size), mapOnCreate(mapOnCreate) {
    VkBufferCreateInfo createInfo = {
            .sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO,
            .size = size,
            .usage = usages,
            .sharingMode = VK_SHARING_MODE_EXCLUSIVE,
    };

    VmaMemoryUsage memoryUsage;

    switch (memoryType) {
        case VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT:
            memoryUsage = VMA_MEMORY_USAGE_AUTO_PREFER_HOST;
            break;
        case VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT:
            memoryUsage = VMA_MEMORY_USAGE_AUTO_PREFER_DEVICE;
            break;
        default:
            memoryUsage = VMA_MEMORY_USAGE_AUTO;
            break;
    }

    VmaAllocationCreateInfo allocInfo = {
            .usage = memoryUsage
    };

    CALL(vmaCreateBuffer(VK_ALLOCATOR, &createInfo, &allocInfo, &buffer, &allocation, nullptr));

    if (mapOnCreate) {
        map();
    }
}

VkBufferResource::~VkBufferResource() {
    unmap();
    if (buffer) {
        vmaDestroyBuffer(VK_ALLOCATOR, buffer, allocation);
        buffer = nullptr;
    }
}

void* VkBufferResource::map() {
    VK_CHECK(vmaMapMemory(VK_ALLOCATOR, allocation, &mapped));
    ASSERT(!mapped, TAG, "Failed to map buffer memory");
    return mapped;
}

void VkBufferResource::unmap() {
    if (mapped) {
        vmaUnmapMemory(VK_ALLOCATOR, allocation);
        mapped = nullptr;
    }
}