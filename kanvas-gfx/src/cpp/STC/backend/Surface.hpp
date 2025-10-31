//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_SURFACE_HPP
#define STC_SURFACE_HPP

#include "Handle.hpp"
#include "Sync.hpp"

namespace stc {

    struct RenderTarget;

#ifdef VK

    struct SwapchainBackend {
        VkSwapchainKHR handle = null;
    };

    struct SurfaceBackend {
        VkSurfaceKHR handle = null; // is owned by window system, so we can't own it, we only use it
        VkSurfaceFormatKHR surfaceFormat = {};
        VkPresentModeKHR presentMode = {};
        VkSurfaceCapabilitiesKHR capabilities = {};
        vec2<uint32_t> extent;
        std::vector<VkSurfaceFormatKHR> surfaceFormats;
        std::vector<VkPresentModeKHR> presentModes;
        SwapchainBackend swapchain;
        std::vector<VkImage> images;
        std::vector<ImageViewHandle> imageViews;
        Ptr<RenderTarget> render_target;
        bool needsResize = false;
    };

#elif METAL



#elif WEBGPU



#endif

    struct Context;
    struct Device;

    struct Surface : SurfaceBackend {
        Device& device;

        Surface(Device& device, void* surface, u32 width, u32 height);
        ~Surface();

        void resize(int width, int height);
        bool getImage(const Semaphore& semaphore, u32& index);
        void recreateSwapChain();

    private:
        void* createSwapChain(const vec2<u32>& extent) const;
        void createImages(const vec2<u32>& extent);
        void freeSwapChain();
        void freeImages();

        static constexpr auto TAG = "Surface";
    };

}

#endif //STC_SURFACE_HPP