//
// Created by cheerwizard on 18.10.25.
//

#include "VkDescriptors.hpp"
#include "VkBufferResource.hpp"

#include "VkContext.hpp"

VkBufferResource* VkBufferResource_create(VkContext* context, VkBufferInfo* info) {
    return new VkBufferResource(context->device, *info);
}

void VkBufferResource_destroy(VkBufferResource* buffer_resource) {
    delete buffer_resource;
}

void* VkBufferResource_map(VkBufferResource* buffer_resource) {
    return buffer_resource->map();
}

void VkBufferResource_unmap(VkBufferResource* buffer_resource) {
    buffer_resource->unmap();
}

void VkBufferResource_updateBinding(VkBufferResource* buffer_resource, u32 frame) {
    buffer_resource->updateBinding(frame);
}

VkBufferResource::VkBufferResource(
    VkDevice device,
    const VkBufferInfo& info
) : device(device), info(info) {

    VkBufferCreateInfo createInfo = {
        .sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO,
        .size = info.size,
        .usage = info.usages,
        .sharingMode = VK_SHARING_MODE_EXCLUSIVE,
    };

    VmaMemoryUsage memoryUsage;

    switch (info.memoryType) {
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

    VK_CHECK(vmaCreateBuffer(VK_ALLOCATOR, &createInfo, &allocInfo, &buffer, &allocation, nullptr));
    VK_DEBUG_NAME(device, VK_OBJECT_TYPE_BUFFER, buffer, info.name);

    if (info.mapOnCreate) {
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

void VkBufferResource::updateBinding(u32 frame) {
    VkBinding* binding = info.binding;
    VkBindingLayout* binding_layout = info.binding_layout;
    VkDescriptorSet set = VkDescriptors::getSet(binding_layout, frame);

    if (binding && binding_layout && set) {
        // TODO need to make it multiframe buffer

        VkDescriptorBufferInfo bufferInfo = {
            .buffer = buffer,
            .offset = 0,
            .range = info.size,
        };

        VkWriteDescriptorSet descriptorWrite = {
            .sType = VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET,
            .dstSet = set,
            .dstBinding = binding->binding,
            .dstArrayElement = 0,
            .descriptorCount = binding->count,
            .descriptorType = (VkDescriptorType) binding->type,
            .pBufferInfo = &bufferInfo,
        };

        vkUpdateDescriptorSets(device, 1, &descriptorWrite, 0, nullptr);
    }
}
