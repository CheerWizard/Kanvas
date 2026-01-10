//
// Created by cheerwizard on 18.10.25.
//

#include "DescriptorPools.hpp"
#include "../Buffer.hpp"
#include "backend/Device.hpp"

namespace stc {

    Buffer::Buffer(const Device& device, const BufferCreateInfo& create_info)
    : info(create_info) {
        VkBufferCreateInfo createInfo = {
            .sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO,
            .size = create_info.size,
            .usage = create_info.usages,
            .sharingMode = VK_SHARING_MODE_EXCLUSIVE,
        };

        VmaMemoryUsage memoryUsage;
        switch (create_info.memoryType) {
            case MEMORY_TYPE_HOST:
                memoryUsage = VMA_MEMORY_USAGE_AUTO_PREFER_HOST;
                break;
            case MEMORY_TYPE_DEVICE_LOCAL:
                memoryUsage = VMA_MEMORY_USAGE_AUTO_PREFER_DEVICE;
                break;
            default:
                memoryUsage = VMA_MEMORY_USAGE_AUTO;
                break;
        }

        VmaAllocationCreateInfo allocInfo = {
            .usage = memoryUsage
        };

        CALL(vmaCreateBuffer(VulkanAllocator::getInstance().allocator, &createInfo, &allocInfo, &handle, &allocation, nullptr));

        if (create_info.mapOnCreate) {
            map();
        }
    }

    Buffer::~Buffer() {
        unmap();
        if (handle) {
            vmaDestroyBuffer(VulkanAllocator::getInstance().allocator, handle, allocation);
            handle = null;
        }
    }

    void* Buffer::map() {
        CALL(vmaMapMemory(VulkanAllocator::getInstance().allocator, allocation, &mapped));
        ASSERT(!mapped, TAG, "Failed to map buffer memory");
        return mapped;
    }

    void Buffer::unmap() {
        if (mapped) {
            vmaUnmapMemory(VulkanAllocator::getInstance().allocator, allocation);
            mapped = nullptr;
        }
    }

}
