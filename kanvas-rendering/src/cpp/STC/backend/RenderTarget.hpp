//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_FRAMEBUFFER_HPP
#define STC_FRAMEBUFFER_HPP

#include "Handle.hpp"
#include "Blending.hpp"

namespace stc {

#ifdef VK

    struct ColorAttachmentBackend : ImageViewHandle {};

    struct DepthAttachmentBackend : ImageViewHandle {};

    struct StencilAttachmentBackend : ImageViewHandle {};

    struct RenderTargetBackend {
        RenderPassHandle render_pass;
        FramebufferHandle frame_buffer;
    };

#elif METAL

    struct ColorAttachmentBackend : TextureHandle {};

    struct DepthAttachmentBackend : TextureHandle {};

    struct StencilAttachmentBackend : TextureHandle {};

    struct RenderTargetBackend {
        RenderPassHandle render_pass;
    };

#elif WEBGPU

    struct ColorAttachmentBackend : TextureViewHandle {};

    struct DepthAttachmentBackend : TextureViewHandle {};

    struct StencilAttachmentBackend : TextureViewHandle {};

    struct RenderTargetBackend {
        RenderPassHandle render_pass;
        CommandEncoderHandle command_encoder;
    };

#endif

    struct Device;
    struct CommandBuffer;

    struct ColorAttachment : ColorAttachmentBackend {
        TextureFormat format;
        u32 samples = 1;
        vec4<float> clearColor = { 0, 0, 0, 1 };
        bool enableBlending = false;
        Blending blendColor;
        Blending blendAlpha;
    };

    struct DepthAttachment : DepthAttachmentBackend {
        bool enabled = false;
        TextureFormat format;
        u32 samples = 1;
        float depthClearValue = 1.0f;
        CompareOp depthCompareOp = COMPARE_OP_LESS;
        u32 stencilClearValue = 1.0;
        bool depthReadOnly = false;
        bool depthWriteEnabled = false;
        bool stencilReadOnly = false;
    };

    struct StencilAttachment : StencilAttachmentBackend {
        bool enabled = false;
        TextureFormat format;
        u32 samples = 1;
        float depthClearValue = 1.0f;
        CompareOp depthCompareOp = COMPARE_OP_LESS;
        u32 stencilClearValue = 1.0;
        bool depthReadOnly = false;
        bool depthWriteEnabled = false;
        bool stencilReadOnly = false;
    };

    struct RenderTargetCreateInfo {
        int x = 0;
        int y = 0;
        u32 width;
        u32 height;
        u32 depth = 1;
        std::vector<ColorAttachment> colorAttachments;
        DepthAttachment depthAttachment;
    };

    struct RenderTarget : RenderTargetBackend {
        RenderTargetCreateInfo info;

        RenderTarget(const Device& device, const RenderTargetCreateInfo& create_info);
        ~RenderTarget();
    };

}

#endif //STC_FRAMEBUFFER_HPP