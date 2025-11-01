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
        std::vector<VkImage> images;
        std::vector<ImageViewHandle> imageViews;
    };

#elif METAL



#elif WEBGPU

    struct SurfaceBackend {
        WGPUSurface handle = null;
    };

    enum SurfaceFormat {
        SURFACE_FORMAT_R8 = WGPUTextureFormat_R8Unorm,
        SURFACE_FORMAT_RG8 = WGPUTextureFormat_RG8Unorm,
        SURFACE_FORMAT_RGB8 = WGPUTextureFormat_Undefined,
        SURFACE_FORMAT_RGBA8 = WGPUTextureFormat_RGBA8Unorm,

        SURFACE_FORMAT_R16 = WGPUTextureFormat_R16Float,
        SURFACE_FORMAT_RG16 = WGPUTextureFormat_RG16Float,
        SURFACE_FORMAT_RGB16 = WGPUTextureFormat_Undefined,
        SURFACE_FORMAT_RGBA16 = WGPUTextureFormat_RGBA16Float,

        SURFACE_FORMAT_R32 = WGPUTextureFormat_R32Float,
        SURFACE_FORMAT_RG32 = WGPUTextureFormat_RG32Float,
        SURFACE_FORMAT_RGB32 = WGPUTextureFormat_Undefined,
        SURFACE_FORMAT_RGBA32 = WGPUTextureFormat_RGBA32Float,

        SURFACE_FORMAT_DEPTH16 = WGPUTextureFormat_Depth16Unorm,
        SURFACE_FORMAT_DEPTH24 = WGPUTextureFormat_Depth24UnormStencil8,
        SURFACE_FORMAT_DEPTH32 = WGPUTextureFormat_Depth32Float,

        SURFACE_FORMAT_STENCIL = WGPUTextureFormat_Stencil8,
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

    struct Surface : SurfaceBackend {
        Device& device;
        u32 width;
        u32 height;
        SurfaceFormat format;
        PresentMode present_mode;
        std::vector<SurfaceFormat> formats;
        std::vector<PresentMode> present_modes;
        void* swapchain = nullptr;
        Ptr<RenderTarget> render_target;
        bool needsResize = false;

        Surface(Device& device, void* surface, u32 width, u32 height);
        ~Surface();

        void resize(int width, int height);
        void* getImage(const Semaphore& semaphore);
        void recreateSwapChain();
        void present(const CommandBuffer& command_buffer);

    private:
        void initSurface(void* surface);
        void releaseSurface();

        void* initSwapChain(u32 width, u32 height) const;
        void releaseSwapChain();

        void initImages(u32 width, u32 height);
        void releaseImages();

        static constexpr auto TAG = "Surface";
    };

}

#endif //STC_SURFACE_HPP