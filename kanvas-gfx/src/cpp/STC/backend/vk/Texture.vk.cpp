//
// Created by cheerwizard on 18.10.25.
//

#include "../Texture.hpp"
#include "backend/Device.hpp"

namespace stc {

    Sampler::Sampler(const Device &device, const SamplerCreateInfo &create_info) {
        VkSamplerCreateInfo createInfo = {
            .sType = VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO,
            .magFilter = (VkFilter) create_info.magFilter,
            .minFilter = (VkFilter) create_info.minFilter,
            .mipmapMode = (VkSamplerMipmapMode) create_info.mipmapMode,
            .addressModeU = (VkSamplerAddressMode) create_info.addressModeU,
            .addressModeV = (VkSamplerAddressMode) create_info.addressModeV,
            .addressModeW = (VkSamplerAddressMode) create_info.addressModeW,
            .mipLodBias = create_info.mipLodBias,
            .anisotropyEnable = create_info.enableAnisotropy,
            .maxAnisotropy = create_info.maxAnisotropy,
            .compareEnable = create_info.enableCompare,
            .compareOp = (VkCompareOp) create_info.compareOp,
            .minLod = create_info.minLod,
            .maxLod = create_info.maxLod,
            .borderColor = VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK, // can't be ported
            .unnormalizedCoordinates = create_info.unnormalizedCoordinates,
        };
        New(device.handle, createInfo);
    }

    Sampler::~Sampler() {
        Delete();
    }

    Texture::Texture(const Device& device, const TextureCreateInfo &create_info) : info(create_info) {
        VkImageType imageType;
        VkImageViewType imageViewType;

        switch (create_info.type) {
            case TEXTURE_2D:
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
            .format = (VkFormat) create_info.format,
            .extent = {
                .width = create_info.width,
                .height = create_info.height,
                .depth = create_info.depth,
            },
            .mipLevels = create_info.mips,
            .arrayLayers = 1,
            .samples = VK_SAMPLE_COUNT_1_BIT,
            .tiling = VK_IMAGE_TILING_OPTIMAL,
            .usage = VK_IMAGE_USAGE_TRANSFER_DST_BIT | VK_IMAGE_USAGE_SAMPLED_BIT,
            .sharingMode = VK_SHARING_MODE_EXCLUSIVE,
            .initialLayout = VK_IMAGE_LAYOUT_UNDEFINED,
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

        CALL(vmaCreateImage(VulkanAllocator::getInstance().allocator, &imageInfo, &allocInfo, &handle, &allocation, nullptr));

        VkImageViewCreateInfo viewInfo = {
            .sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO,
            .image = handle,
            .viewType = imageViewType,
            .format = (VkFormat) create_info.format,
            .subresourceRange = {
                .aspectMask = VK_IMAGE_ASPECT_COLOR_BIT,
                .baseMipLevel = create_info.baseMip,
                .levelCount = create_info.mips,
                .baseArrayLayer = 0,
                .layerCount = 1,
            }
        };

        view.New(device, viewInfo);
    }

    Texture::~Texture() {
        view.Delete();
        if (handle) {
            vmaDestroyImage(VulkanAllocator::getInstance().allocator, handle, allocation);
            handle = null;
        }
    }

    // TODO consider cross platform way to update texture

    // void* Texture::map() {
    //     CALL(vmaMapMemory(VulkanAllocator::getInstance().allocator, allocation, &mapped));
    //     ASSERT(!mapped, TAG, "Failed to map buffer memory");
    //     return mapped;
    // }
    //
    // void Texture::unmap() {
    //     if (mapped) {
    //         vmaUnmapMemory(VulkanAllocator::getInstance().allocator, allocation);
    //         mapped = nullptr;
    //     }
    // }

}