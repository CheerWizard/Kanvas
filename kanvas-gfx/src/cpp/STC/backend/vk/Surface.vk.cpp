//
// Created by cheerwizard on 18.10.25.
//

#include "../Surface.hpp"
#include "../Device.hpp"
#include "backend/RenderTarget.hpp"

namespace stc {

    Surface::Surface(Device &device, void* surface, u32 width, u32 height) : device(device) {
        if (!surface) return;

        handle = (VkSurfaceKHR) surface;
        extent.x = width;
        extent.y = height;

        vkGetPhysicalDeviceSurfaceCapabilitiesKHR(device.physicalDevice, handle, &capabilities);

        {
            u32 count;
            vkGetPhysicalDeviceSurfaceFormatsKHR(device.physicalDevice, handle, &count, nullptr);
            surfaceFormats.resize(count);
            vkGetPhysicalDeviceSurfaceFormatsKHR(device.physicalDevice, handle, &count, surfaceFormats.data());
        }

        {
            u32 count;
            vkGetPhysicalDeviceSurfacePresentModesKHR(device.physicalDevice, handle, &count, nullptr);
            presentModes.resize(count);
            vkGetPhysicalDeviceSurfacePresentModesKHR(device.physicalDevice, handle, &count, presentModes.data());
        }

        {
            surfaceFormat = surfaceFormats[0];
            for (const auto& format : surfaceFormats) {
                if (format.format == VK_FORMAT_B8G8R8A8_UNORM && format.colorSpace == VK_COLOR_SPACE_SRGB_NONLINEAR_KHR) {
                    surfaceFormat = format;
                    break;
                }
            }
        }

        {
            presentMode = VK_PRESENT_MODE_FIFO_KHR;
            for (const auto& mode : presentModes) {
                if (mode == VK_PRESENT_MODE_MAILBOX_KHR) {
                    presentMode = mode;
                    break;
                }
            }
        }

        swapchain.handle = (VkSwapchainKHR) createSwapChain(extent);

        createImages(extent);
    }

    Surface::~Surface() {
        freeImages();
        freeSwapChain();
    }

    void Surface::freeImages() {
        render_target.Delete();
        for (auto imageView : imageViews) {
            imageView.Delete();
        }
    }

    void Surface::recreateSwapChain() {
        vkDeviceWaitIdle(device);
        auto extent = this->extent;
        auto newSwapchain = (VkSwapchainKHR) createSwapChain(extent);
        freeImages();
        freeSwapChain();
        swapchain.handle = newSwapchain;
        createImages(extent);
        needsResize = false;
    }

    void* Surface::createSwapChain(const vec2<u32>& extent) const {
        u32 imageCount = capabilities.minImageCount + 1;
        if (capabilities.maxImageCount > 0 && imageCount > capabilities.maxImageCount) {
            imageCount = capabilities.maxImageCount;
        }

        VkSwapchainCreateInfoKHR createInfo {
            .sType = VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR,
            .surface = handle,
            .minImageCount = imageCount,
            .imageFormat = surfaceFormat.format,
            .imageColorSpace = surfaceFormat.colorSpace,
            .imageExtent = { .width = extent.x, .height = extent.y },
            .imageArrayLayers = 1,
            .imageUsage = VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT,
            .preTransform = capabilities.currentTransform,
            .compositeAlpha = VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR,
            .presentMode = presentMode,
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
        return newSwapchain.handle;
    }

    void Surface::freeSwapChain() {
        if (swapchain.handle) {
            vkDestroySwapchainKHR(device.handle, swapchain.handle, &VulkanAllocator::getInstance().callbacks);
            swapchain.handle = null;
        }
    }

    void Surface::resize(int width, int height) {
        extent.x = width;
        extent.y = height;
        needsResize = true;
    }

    bool Surface::getImage(const Semaphore &semaphore, uint32_t& index) {
        VkResult result = vkAcquireNextImageKHR(device, swapchain.handle, UINT64_MAX, semaphore.handle, VK_NULL_HANDLE, &index);
        if (result == VK_ERROR_OUT_OF_DATE_KHR || needsResize) {
            recreateSwapChain();
            return false;
        }
        ASSERT(result == VK_SUCCESS || result == VK_SUBOPTIMAL_KHR, TAG, "Failed to acquire next image from swap chain");
        return true;
    }

    void Surface::createImages(const vec2<uint32_t>& extent) {
        uint32_t swapImageCount;
        vkGetSwapchainImagesKHR(device, swapchain.handle, &swapImageCount, nullptr);
        images.resize(swapImageCount);
        vkGetSwapchainImagesKHR(device, swapchain.handle, &swapImageCount, images.data());

        imageViews.resize(swapImageCount);

        std::vector<ColorAttachment> color_attachments;
        color_attachments.resize(swapImageCount);

        for (size_t i = 0; i < swapImageCount; ++i) {
            VkImageViewCreateInfo createInfo {
                .sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO,
                .image = images[i],
                .viewType = VK_IMAGE_VIEW_TYPE_2D,
                .format = surfaceFormat.format,
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
            imageViews[i].New(device, createInfo);
            color_attachments[i] = ColorAttachment { imageViews[i] };
        }

        render_target.New(device, RenderTargetCreateInfo {
            .format = (TextureFormat) surfaceFormat.format,
            .extent = extent,
            .colorAttachments = color_attachments,
        });
    }

}