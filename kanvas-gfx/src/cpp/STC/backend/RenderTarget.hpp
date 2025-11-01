//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_FRAMEBUFFER_HPP
#define STC_FRAMEBUFFER_HPP

#include "Handle.hpp"
#include "Texture.hpp"

namespace stc {

#ifdef VK

    struct ColorAttachment : ImageViewHandle {};

    struct DepthAttachment : ImageViewHandle {};

    struct RenderTargetBackend {
        RenderPassHandle render_pass;
        std::vector<FramebufferHandle> frame_buffers;
    };

#elif METAL



#elif WEBGPU

    struct ColorAttachment : TextureViewHandle {};

    struct DepthAttachment : TextureViewHandle {};

    struct RenderTargetBackend {
        RenderPassHandle render_pass;
    };

#endif

    struct Device;

    struct RenderTargetCreateInfo {
        TextureFormat format;
        u32 width;
        u32 height;
        std::vector<ColorAttachment> colorAttachments;
    };

    struct RenderTarget : RenderTargetBackend {
        RenderTarget(const Device& device, const RenderTargetCreateInfo& create_info);
        ~RenderTarget();
    };

}

#endif //STC_FRAMEBUFFER_HPP