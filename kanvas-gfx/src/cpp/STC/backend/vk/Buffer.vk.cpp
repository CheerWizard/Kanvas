//
// Created by cheerwizard on 18.10.25.
//

#include "../Buffer.hpp"
#include "backend/Device.hpp"

namespace stc {

    Buffer::Buffer(const Device& device, MemoryType memoryType, u32 usages, size_t size)
    : size(size) {
        VkBufferCreateInfo createInfo = {
            .sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO,
            .size = size,
            .usage = usages,
            .sharingMode = VK_SHARING_MODE_EXCLUSIVE,
        };

        VmaMemoryUsage memoryUsage;
        switch (memoryType) {
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

    void Buffer::updateBinding(const Device& device, const BindingLayout& binding_layout, Binding& binding) {
        VkDescriptorBufferInfo bufferInfo = {
            .buffer = handle,
            .offset = 0,
            .range = size,
        };

        VkWriteDescriptorSet descriptorWrite = {
            .sType = VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET,
            .dstSet = binding_layout.set,
            .dstBinding = binding.slot,
            .dstArrayElement = 0,
            .descriptorCount = 1,
            .descriptorType = (VkDescriptorType) binding.type,
            .pBufferInfo = &bufferInfo,
        };

        vkUpdateDescriptorSets(device.handle, 1, &descriptorWrite, 0, nullptr);
    }

}
