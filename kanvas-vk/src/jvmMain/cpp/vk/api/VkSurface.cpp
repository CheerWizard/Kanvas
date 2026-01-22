//
// Created by cheerwizard on 18.10.25.
//

#include "VkSurface.hpp"
#include "VkContext.hpp"

void VkSurface::initSurface(VkSurfaceKHR surface) {
    this->surface = surface;

    vkGetPhysicalDeviceSurfaceCapabilitiesKHR(context->physical_device, surface, &capabilities);

    std::vector<VkSurfaceFormatKHR> surface_formats;
    {
        u32 count;
        vkGetPhysicalDeviceSurfaceFormatsKHR(context->physical_device, surface, &count, nullptr);
        surface_formats.resize(count);
        vkGetPhysicalDeviceSurfaceFormatsKHR(context->physical_device, surface, &count, surface_formats.data());
    }

    std::vector<VkPresentModeKHR> present_modes;
    {
        u32 count;
        vkGetPhysicalDeviceSurfacePresentModesKHR(context->physical_device, surface, &count, nullptr);
        present_modes.resize(count);
        vkGetPhysicalDeviceSurfacePresentModesKHR(context->physical_device, surface, &count, present_modes.data());
    }

    {
        surface_format = surface_formats[0];
        for (const auto& format : surface_formats) {
            if (format.format == VK_FORMAT_B8G8R8A8_UNORM && format.colorSpace == VK_COLOR_SPACE_SRGB_NONLINEAR_KHR) {
                surface_format = format;
                break;
            }
        }
    }

    {
        present_mode = VK_PRESENT_MODE_FIFO_KHR;
        for (const auto& mode : present_modes) {
            if (mode == VK_PRESENT_MODE_MAILBOX_KHR) {
                present_mode = mode;
                break;
            }
        }
    }
}

void VkSurface::initImages(u32 width, u32 height) {
    uint32_t swapImageCount;
    vkGetSwapchainImagesKHR(context->device, swapchain, &swapImageCount, nullptr);
    images.resize(swapImageCount);
    vkGetSwapchainImagesKHR(context->device, swapchain, &swapImageCount, images.data());

    std::vector<VkColorAttachment> color_attachments;
    color_attachments.resize(swapImageCount);

    for (u32 i = 0; i < swapImageCount; i++) {
        VkImageViewCreateInfo color_attachment_info = {
                .sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO,
                .image = images[i],
                .viewType = VK_IMAGE_VIEW_TYPE_2D,
                .format = surface_format.format,
                .components = {
                        VK_COMPONENT_SWIZZLE_IDENTITY,
                        VK_COMPONENT_SWIZZLE_IDENTITY,
                        VK_COMPONENT_SWIZZLE_IDENTITY,
                        VK_COMPONENT_SWIZZLE_IDENTITY
                },
                .subresourceRange = {
                        .aspectMask = VK_IMAGE_ASPECT_COLOR_BIT,
                        .baseMipLevel = 0,
                        .levelCount = 1,
                        .baseArrayLayer = 0,
                        .layerCount = 1,
                }
        };

        VK_CHECK(vkCreateImageView(context->device, &color_attachment_info, VK_CALLBACKS, &color_attachments[i].view));
    }

    if (render_target) {
        delete render_target;
    }

    render_target = new VkRenderTarget(context->device, VkRenderTargetInfo {
            .width = width,
            .height = height,
            .colorAttachments = color_attachments.data(),
            .colorAttachmentsCount = color_attachments.size()
    });
}

void VkSurface::releaseImages() {
    delete render_target;
}

VkSwapchainKHR VkSurface::initSwapChain(u32 width, u32 height) const {
    u32 imageCount = capabilities.minImageCount + 1;
    if (capabilities.maxImageCount > 0 && imageCount > capabilities.maxImageCount) {
        imageCount = capabilities.maxImageCount;
    }

    VkSwapchainCreateInfoKHR createInfo {
            .sType = VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR,
            .surface = surface,
            .minImageCount = imageCount,
            .imageFormat = surface_format.format,
            .imageColorSpace = surface_format.colorSpace,
            .imageExtent = { .width = width, .height = height },
            .imageArrayLayers = 1,
            .imageUsage = VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT,
            .preTransform = capabilities.currentTransform,
            .compositeAlpha = VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR,
            .presentMode = (VkPresentModeKHR) present_mode,
            .clipped = VK_TRUE,
            .oldSwapchain = swapchain,
    };

    std::array<u32, 2> queue_family_indices = {
            context->queue_family_indices.graphics,
            context->queue_family_indices.present
    };

    if (queue_family_indices[0] != queue_family_indices[1]) {
        createInfo.imageSharingMode = VK_SHARING_MODE_CONCURRENT;
        createInfo.queueFamilyIndexCount = queue_family_indices.size();
        createInfo.pQueueFamilyIndices = queue_family_indices.data();
    } else {
        createInfo.imageSharingMode = VK_SHARING_MODE_EXCLUSIVE;
        createInfo.queueFamilyIndexCount = 0;
        createInfo.pQueueFamilyIndices = nullptr;
    }

    VkSwapchainKHR newSwapchain;
    VK_CHECK(vkCreateSwapchainKHR(context->device, &createInfo, VK_CALLBACKS, &newSwapchain));
    return newSwapchain;
}

void VkSurface::releaseSwapChain() {
    if (swapchain) {
        vkDestroySwapchainKHR(context->device, swapchain, VK_CALLBACKS);
        swapchain = nullptr;
    }
}

void VkSurface::resize(int width, int height) {
    render_target->info.width = width;
    render_target->info.height = height;
    needsResize = true;
}

bool VkSurface::getImage(const VkSemaphoreSync &semaphore) {
    VkResult result = vkAcquireNextImageKHR(context->device, swapchain, UINT64_MAX, semaphore.semaphore, VK_NULL_HANDLE, &currentImageIndex);
    if (result == VK_ERROR_OUT_OF_DATE_KHR || needsResize) {
        recreateSwapChain();
        return false;
    }
    ASSERT(result == VK_SUCCESS || result == VK_SUBOPTIMAL_KHR, TAG, "Failed to acquire next image from swap chain");
    return true;
}