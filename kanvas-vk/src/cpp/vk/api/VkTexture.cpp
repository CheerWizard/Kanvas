//
// Created by cheerwizard on 18.10.25.
//

#include "VkTexture.hpp"

#include "VkBindingLayout.hpp"
#include "VkContext.hpp"
#include "VkDescriptors.hpp"

VkSamplerResource* VkSamplerResource_create(VkContext* context, VkSamplerInfo* info) {
    return new VkSamplerResource(context->device, *info);
}

void VkSamplerResource_destroy(VkSamplerResource* sampler_resource) {
    delete sampler_resource;
}

void VkSamplerResource_updateBinding(VkSamplerResource* sampler_resource, u32 frame) {
    sampler_resource->updateBinding(frame);
}

VkSamplerResource::VkSamplerResource(VkDevice device, const VkSamplerInfo &info)
: device(device), info(info) {
    VkSamplerCreateInfo createInfo = {
            .sType = VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO,
            .magFilter = info.magFilter,
            .minFilter = info.minFilter,
            .mipmapMode = info.mipmapMode,
            .addressModeU = info.addressModeU,
            .addressModeV = info.addressModeV,
            .addressModeW = info.addressModeW,
            .mipLodBias = info.mipLodBias,
            .anisotropyEnable = info.enableAnisotropy,
            .maxAnisotropy = info.maxAnisotropy,
            .compareEnable = info.enableCompare,
            .compareOp = info.compareOp,
            .minLod = info.minLod,
            .maxLod = info.maxLod,
            .borderColor = info.borderColor,
            .unnormalizedCoordinates = info.unnormalizedCoordinates,
    };
    VK_CHECK(vkCreateSampler(device, &createInfo, VK_CALLBACKS, &sampler));
}

VkSamplerResource::~VkSamplerResource() {
    if (sampler) {
        vkDestroySampler(device, sampler, VK_CALLBACKS);
        sampler = nullptr;
    }
}

void VkSamplerResource::updateBinding(u32 frame) {
    VkBinding* binding = info.binding;
    VkBindingLayout* binding_layout = info.binding_layout;
    VkDescriptorSet set = VkDescriptors::getSet(binding_layout, frame);

    if (binding && binding_layout && set) {
        VkDescriptorImageInfo imageInfo = {
            .sampler = sampler,
            .imageView = VK_NULL_HANDLE,
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

        vkUpdateDescriptorSets(device, 1, &descriptorWrite, 0, nullptr);
    }
}

VkTextureResource* VkTextureResource_create(VkContext* context, VkTextureInfo* info) {
    return new VkTextureResource(context->device, *info);
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

VkTextureResource::VkTextureResource(VkDevice device, const VkTextureInfo &info)
: device(device), info(info) {
    VkImageType imageType;
    VkImageViewType imageViewType;

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
            .arrayLayers = 1,
            .samples = VK_SAMPLE_COUNT_1_BIT,
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

    VkImageViewCreateInfo viewInfo = {
            .sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO,
            .image = image,
            .viewType = imageViewType,
            .format = info.format,
            .subresourceRange = {
                    .aspectMask = VK_IMAGE_ASPECT_COLOR_BIT,
                    .baseMipLevel = info.baseMip,
                    .levelCount = info.mips,
                    .baseArrayLayer = 0,
                    .layerCount = 1,
            }
    };

    VK_CHECK(vkCreateImageView(device, &viewInfo, VK_CALLBACKS, &view));
}

VkTextureResource::~VkTextureResource() {
    if (view) {
        vkDestroyImageView(device, view, VK_CALLBACKS);
        view = nullptr;
    }
    if (image) {
        vmaDestroyImage(VK_ALLOCATOR, image, allocation);
        image = nullptr;
    }
}

void* VkTextureResource::map() {
    VK_CHECK(vmaMapMemory(VK_ALLOCATOR, allocation, &mapped));
    ASSERT(!mapped, TAG, "Failed to map buffer memory");
    return mapped;
}

void VkTextureResource::unmap() {
    if (mapped) {
        vmaUnmapMemory(VK_ALLOCATOR, allocation);
        mapped = nullptr;
    }
}

void VkTextureResource::updateBinding(u32 frame) {
    VkBinding* binding = info.binding;
    VkBindingLayout* binding_layout = info.binding_layout;
    VkDescriptorSet set = VkDescriptors::getSet(binding_layout, frame);

    if (binding && binding_layout && set) {
        VkDescriptorImageInfo imageInfo = {
            .sampler = VK_NULL_HANDLE,
            .imageView = view,
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

        vkUpdateDescriptorSets(device, 1, &descriptorWrite, 0, nullptr);
    }
}
