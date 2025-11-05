//
// Created by cheerwizard on 18.10.25.
//

#include "../Surface.hpp"
#include "../Device.hpp"
#include "backend/RenderTarget.hpp"

namespace stc {

    void Surface::initSurface(void* surface) {
        handle = (VkSurfaceKHR) surface;

        vkGetPhysicalDeviceSurfaceCapabilitiesKHR(device.physicalDevice, handle, &capabilities);

        std::vector<VkSurfaceFormatKHR> surface_formats;
        {
            u32 count;
            vkGetPhysicalDeviceSurfaceFormatsKHR(device.physicalDevice, handle, &count, nullptr);
            surface_formats.resize(count);
            vkGetPhysicalDeviceSurfaceFormatsKHR(device.physicalDevice, handle, &count, surface_formats.data());
        }

        std::vector<VkPresentModeKHR> present_modes;
        {
            u32 count;
            vkGetPhysicalDeviceSurfacePresentModesKHR(device.physicalDevice, handle, &count, nullptr);
            present_modes.resize(count);
            vkGetPhysicalDeviceSurfacePresentModesKHR(device.physicalDevice, handle, &count, present_modes.data());
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
            present_mode = (PresentMode) VK_PRESENT_MODE_FIFO_KHR;
            for (const auto& mode : present_modes) {
                if (mode == VK_PRESENT_MODE_MAILBOX_KHR) {
                    present_mode = (PresentMode) mode;
                    break;
                }
            }
        }
    }

    void Surface::initImages(u32 width, u32 height) {
        uint32_t swapImageCount;
        vkGetSwapchainImagesKHR(device, swapchain.handle, &swapImageCount, nullptr);
        images.resize(swapImageCount);
        vkGetSwapchainImagesKHR(device, swapchain.handle, &swapImageCount, images.data());

        std::vector<ColorAttachment> color_attachments;
        color_attachments.resize(swapImageCount);

        for (u32 i = 0; i < swapImageCount; i++) {
            color_attachments[i].New(device, VkImageViewCreateInfo {
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
            });
        }

        render_target.New(device, RenderTargetCreateInfo {
            .width = width,
            .height = height,
            .colorAttachments = color_attachments,
        });
    }

    void Surface::releaseImages() {
        for (auto& color_attachment : render_target->info.colorAttachments) {
            color_attachment.Delete();
        }
        render_target.Delete();
    }

    SwapchainHandle Surface::initSwapChain(u32 width, u32 height) const {
        u32 imageCount = capabilities.minImageCount + 1;
        if (capabilities.maxImageCount > 0 && imageCount > capabilities.maxImageCount) {
            imageCount = capabilities.maxImageCount;
        }

        VkSwapchainCreateInfoKHR createInfo {
            .sType = VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR,
            .surface = handle,
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
            .oldSwapchain = swapchain.handle,
        };

        std::array<u32, 2> queue_family_indices = {
            device.queue_family_indices.graphics,
            device.queue_family_indices.present
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

        SwapchainHandle newSwapchain;
        newSwapchain.New(device, createInfo);
        return newSwapchain;
    }

    void Surface::releaseSwapChain() {
        if (swapchain.handle) {
            vkDestroySwapchainKHR(device.handle, swapchain.handle, &VulkanAllocator::getInstance().callbacks);
            swapchain.handle = null;
        }
    }

    void Surface::resize(int width, int height) {
        render_target->info.width = width;
        render_target->info.height = height;
        needsResize = true;
    }

    void* Surface::getImage(const Semaphore &semaphore) {
        // TODO needs refactor on how to get surface image per API
        VkResult result = vkAcquireNextImageKHR(device, swapchain.handle, UINT64_MAX, semaphore.handle, VK_NULL_HANDLE, &index);
        if (result == VK_ERROR_OUT_OF_DATE_KHR || needsResize) {
            recreateSwapChain();
            return false;
        }
        ASSERT(result == VK_SUCCESS || result == VK_SUBOPTIMAL_KHR, TAG, "Failed to acquire next image from swap chain");
        return true;
    }

}