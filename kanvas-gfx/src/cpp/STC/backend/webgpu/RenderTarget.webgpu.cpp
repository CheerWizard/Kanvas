//
// Created by cheerwizard on 18.10.25.
//

#include "../RenderTarget.hpp"
#include "backend/CommandBuffer.hpp"
#include "backend/Device.hpp"

namespace stc {

    RenderTarget::RenderTarget(const Device &device, const RenderTargetCreateInfo &create_info)
    : info(create_info) {
        std::vector<WGPURenderPassColorAttachment> wgpuColorAttachments(create_info.colorAttachments.size());

        for (u32 i = 0 ; i < create_info.colorAttachments.size() ; i++) {
            auto& wgpuColorAttachment = wgpuColorAttachments[i];
            const auto& colorAttachment = create_info.colorAttachments[i];
            wgpuColorAttachment.view = colorAttachment.handle;
            wgpuColorAttachment.clearColor = {
                colorAttachment.clearColor.x,
                colorAttachment.clearColor.y,
                colorAttachment.clearColor.z,
                colorAttachment.clearColor.w,
            };
            // TODO not sure how to use it yet
            wgpuColorAttachment.loadOp = WGPULoadOp_Clear;
            wgpuColorAttachment.storeOp = WGPUStoreOp_Store;
        }

        const auto& depth_attachment = create_info.depthAttachment;

        WGPURenderPassDepthStencilAttachment wgpuDepthAttachment = {
            .view = depth_attachment.handle,
            .depthLoadOp = WGPULoadOp_Clear,
            .depthStoreOp = WGPUStoreOp_Store,
            .clearDepth = depth_attachment.depthClearValue,
            .depthReadOnly = depth_attachment.depthReadOnly,
            .stencilLoadOp = WGPULoadOp_Undefined,
            .stencilStoreOp = WGPUStoreOp_Undefined,
            .clearStencil = depth_attachment.stencilClearValue,
            .stencilReadOnly = depth_attachment.stencilReadOnly,
        };

        command_encoder.New(device.handle, WGPUCommandEncoderDescriptor {
            .label = "RenderTarget_CommandEncoder",
        });

        render_pass.New(command_encoder.handle, WGPURenderPassDescriptor {
            .label = "RenderTarget_RenderPass",
            .colorAttachmentCount = (u32) wgpuColorAttachments.size(),
            .colorAttachments = wgpuColorAttachments.data(),
            .depthStencilAttachment = &wgpuDepthAttachment,
        });
    }

    RenderTarget::~RenderTarget() {
        render_pass.Delete();
        command_encoder.Delete();
    }

}
