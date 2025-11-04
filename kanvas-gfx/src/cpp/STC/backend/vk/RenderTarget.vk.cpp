//
// Created by cheerwizard on 18.10.25.
//

#include "../RenderTarget.hpp"
#include "backend/Device.hpp"

namespace stc {

    RenderTarget::RenderTarget(const Device &device, const RenderTargetCreateInfo &create_info)
    : info(create_info) {
        usize color_attachment_count = create_info.colorAttachments.size();
        std::vector<VkAttachmentDescription> vk_color_attachments(color_attachment_count);
        std::vector<VkAttachmentReference> vk_color_references(color_attachment_count);
        std::vector<VkImageView> vk_image_views(color_attachment_count);

        for (u32 i = 0 ; i < color_attachment_count ; i++) {
            auto& vk_color_attachment = vk_color_attachments[i];
            auto& vk_color_reference = vk_color_references[i];
            const auto& color_attachment = create_info.colorAttachments[i];

            vk_color_attachment.format = (VkFormat) color_attachment.format;
            vk_color_attachment.samples = (VkSampleCountFlagBits) color_attachment.samples;
            // TODO expose more params
            vk_color_attachment.loadOp = VK_ATTACHMENT_LOAD_OP_CLEAR;
            vk_color_attachment.storeOp = VK_ATTACHMENT_STORE_OP_STORE;
            vk_color_attachment.stencilLoadOp = VK_ATTACHMENT_LOAD_OP_DONT_CARE;
            vk_color_attachment.stencilStoreOp = VK_ATTACHMENT_STORE_OP_DONT_CARE;
            vk_color_attachment.initialLayout = VK_IMAGE_LAYOUT_UNDEFINED;
            vk_color_attachment.finalLayout = VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;

            vk_color_reference.attachment = i;
            vk_color_reference.layout = VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;

            vk_image_views[i] = create_info.colorAttachments[i].handle;
        }

        VkSubpassDescription subpass = {
            .pipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS,
            .colorAttachmentCount = (u32) vk_color_attachments.size(),
            .pColorAttachments = vk_color_references.data(),
        };

        render_pass.New(device.handle, VkRenderPassCreateInfo {
            .sType = VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO,
            .attachmentCount = (u32) vk_color_attachments.size(),
            .pAttachments = vk_color_attachments.data(),
            .subpassCount = 1,
            .pSubpasses = &subpass,
        });

        frame_buffer.New(device.handle, VkFramebufferCreateInfo {
            .sType = VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO,
            .renderPass = render_pass.handle,
            .attachmentCount = (u32) vk_image_views.size(),
            .pAttachments = vk_image_views.data(),
            .width = create_info.width,
            .height = create_info.height,
            .layers = create_info.depth,
        });
    }

    RenderTarget::~RenderTarget() {
        frame_buffer.Delete();
        render_pass.Delete();
    }

}
