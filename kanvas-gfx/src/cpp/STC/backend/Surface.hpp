//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_SURFACE_HPP
#define STC_SURFACE_HPP

#include "Handle.hpp"
#include "Sync.hpp"
#include "RenderTarget.hpp"

namespace stc {

#ifdef VK

    struct SurfaceBackend {
        VkSurfaceKHR handle = null;
        VkSurfaceCapabilitiesKHR capabilities = {};
        VkSurfaceFormatKHR surface_format;
        std::vector<VkImage> images;
    };

    enum PresentMode {
        PRESENT_MODE_FIFO = VK_PRESENT_MODE_FIFO_KHR,
        PRESENT_MODE_IMMEDIATE = VK_PRESENT_MODE_IMMEDIATE_KHR,
        PRESENT_MODE_MAILBOX = VK_PRESENT_MODE_MAILBOX_KHR,
    };

#elif METAL



#elif WEBGPU

    struct SurfaceBackend {
        WGPUSurface handle = null;
    };

    enum PresentMode {
        PRESENT_MODE_FIFO = WGPUPresentMode_Fifo,
        PRESENT_MODE_IMMEDIATE = WGPUPresentMode_Immediate,
        PRESENT_MODE_MAILBOX = WGPUPresentMode_Mailbox,
    };

#endif

    struct Context;
    struct Device;
    struct CommandBuffer;

    struct SurfaceCreateInfo {
        void* surface = nullptr;
        u32 width = 0;
        u32 height = 0;
    };

    struct Surface : SurfaceBackend {
        Device& device;
        PresentMode present_mode;
        SwapchainHandle swapchain;
        Ptr<RenderTarget> render_target;
        bool needsResize = false;

        Surface(Device& device, const SurfaceCreateInfo& create_info);
        ~Surface();

        void resize(int width, int height);
        void* getImage(const Semaphore& semaphore);
        void recreateSwapChain();
        void present(const CommandBuffer& command_buffer);

    private:
        void initSurface(void* surface);
        void releaseSurface();

        SwapchainHandle initSwapChain(u32 width, u32 height) const;
        void releaseSwapChain();

        void initImages(u32 width, u32 height);
        void releaseImages();

        static constexpr auto TAG = "Surface";
    };

}

#endif //STC_SURFACE_HPP