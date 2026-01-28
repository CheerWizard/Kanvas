//
// Created by cheerwizard on 18.10.25.
//

#include "VkTextureResource.hpp"
#include "VkContext.hpp"
#include "VkDescriptors.hpp"

VkTextureResource* VkTextureResource_create(VkContext* context, VkTextureInfo* info) {
    return new VkTextureResource(context, *info);
}

void VkTextureResource_destroy(VkTextureResource* texture_resource) {
    delete texture_resource;
}

void* VkTextureResource_map(VkTextureResource* texture_resource) {
    return texture_resource->map();
}

void VkTextureResource_unmap(VkTextureResource* texture_resource) {
    texture_resource->unmap();
}

void VkTextureResource_updateBinding(VkTextureResource* texture_resource, u32 frame) {
    texture_resource->updateBinding(frame);
}

VkTextureResource::VkTextureResource(VkContext* context, const VkTextureInfo &info)
: context(context), info(info) {
    VkImageType imageType;
    VkImageViewType imageViewType;

    u32 frameCount = context->info.frameCount;
    if (info.isStatic) {
        frameCount = 1;
    }

    switch (info.type) {
        case VK_IMAGE_VIEW_TYPE_2D:
            imageType = VK_IMAGE_TYPE_2D;
            imageViewType = VK_IMAGE_VIEW_TYPE_2D;
            break;
        default:
            imageType = VK_IMAGE_TYPE_2D;
            imageViewType = VK_IMAGE_VIEW_TYPE_2D;
            break;
    }

    VkImageCreateInfo imageInfo = {
            .sType = VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO,
            .flags = 0,
            .imageType = imageType,
            .format = info.format,
            .extent = {
                    .width = info.width,
                    .height = info.height,
                    .depth = info.depth,
            },
            .mipLevels = info.mips,
            .arrayLayers = frameCount,
            .samples = static_cast<VkSampleCountFlagBits>(info.samples),
            .tiling = VK_IMAGE_TILING_OPTIMAL,
            .usage = VK_IMAGE_USAGE_TRANSFER_DST_BIT | VK_IMAGE_USAGE_SAMPLED_BIT,
            .sharingMode = VK_SHARING_MODE_EXCLUSIVE,
            .initialLayout = VK_IMAGE_LAYOUT_UNDEFINED,
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

    VK_CHECK(vmaCreateImage(VK_ALLOCATOR, &imageInfo, &allocInfo, &image, &allocation, nullptr));
    VK_DEBUG_NAME(context->device, VK_OBJECT_TYPE_IMAGE, image, info.name);

    views.resize(frameCount);
    for (u32 i = 0 ; i < frameCount ; i++) {
        auto view = views[i];
        VkImageViewCreateInfo viewInfo = {
            .sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO,
            .image = image,
            .viewType = imageViewType,
            .format = info.format,
            .subresourceRange = {
                .aspectMask = VK_IMAGE_ASPECT_COLOR_BIT,
                .baseMipLevel = info.baseMip,
                .levelCount = info.mips,
                .baseArrayLayer = i,
                .layerCount = 1,
            }
        };
        VK_CHECK(vkCreateImageView(context->device, &viewInfo, VK_CALLBACKS, &view));
        VK_DEBUG_NAME(context->device, VK_OBJECT_TYPE_IMAGE_VIEW, view, info.name);
    }
}

VkTextureResource::~VkTextureResource() {
    for (int i = 0 ; i < views.size() ; i++) {
        auto view = views[i];
        if (view) {
            vkDestroyImageView(context->device, view, VK_CALLBACKS);
        }
    }
    views.clear();

    if (image) {
        vmaDestroyImage(VK_ALLOCATOR, image, allocation);
        image = nullptr;
    }
}

void* VkTextureResource::map() {
    VK_CHECK(vmaMapMemory(VK_ALLOCATOR, allocation, &mapped));
    ASSERT(!mapped, TAG, "Failed to map VkTextureResource memory");
    return mapped;
}

void VkTextureResource::unmap() {
    if (mapped) {
        vmaUnmapMemory(VK_ALLOCATOR, allocation);
        mapped = nullptr;
    }
}

void VkTextureResource::resize(u32 width, u32 height) {
    info.width = width;
    info.height = height;
    this->~VkTextureResource();
    new (this) VkTextureResource(context, info);
}

void VkTextureResource::updateBinding(u32 frame) {
    VkBinding* binding = info.binding;
    VkBindingLayout* binding_layout = info.binding_layout;
    VkDescriptorSet set = VkDescriptors::getSet(binding_layout, frame);

    u32 actualFrame = frame;
    if (info.isStatic) {
        actualFrame = 0;
    }

    if (binding && binding_layout && set) {
        VkDescriptorImageInfo imageInfo = {
            .sampler = VK_NULL_HANDLE,
            .imageView = views[actualFrame],
            .imageLayout = VK_IMAGE_LAYOUT_GENERAL,
        };

        VkWriteDescriptorSet descriptorWrite = {
            .sType = VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET,
            .dstSet = set,
            .dstBinding = binding->binding,
            .dstArrayElement = 0,
            .descriptorCount = binding->count,
            .descriptorType = (VkDescriptorType) binding->type,
            .pImageInfo = &imageInfo,
        };

        vkUpdateDescriptorSets(context->device, 1, &descriptorWrite, 0, nullptr);
    }
}
