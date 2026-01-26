//
// Created by cheerwizard on 18.10.25.
//

#ifndef SURFACE_HPP
#define SURFACE_HPP

#include "VkSync.hpp"
#include "VkRenderTarget.hpp"

struct VkContext;

struct VkSurface {
    VkContext* context = nullptr;
    VkSurfaceKHR surface = nullptr;
    VkSurfaceCapabilitiesKHR capabilities = {};
    VkSurfaceFormatKHR surface_format;
    std::vector<VkImage> images;
    u32 currentImageIndex = 0;
    u32 width = 0;
    u32 height = 0;
    VkPresentModeKHR present_mode;
    VkSwapchainKHR swapchain = nullptr;
    VkRenderTarget* render_target = nullptr;
    bool needsResize = false;

    VkSurface(VkContext* context, VkSurfaceKHR surface, u32 width, u32 height);
    ~VkSurface();

    void resize(int width, int height);
    bool getImage(const VkSemaphoreResource& semaphore);
    void recreateSwapChain();

private:
    void initSurface(VkSurfaceKHR surface);

    VkSwapchainKHR initSwapChain(u32 width, u32 height) const;
    void releaseSwapChain();

    void initImages(u32 width, u32 height);
    void releaseImages();

    static constexpr auto TAG = "VkSurface";
};

#endif //SURFACE_HPP