//
// Created by cheerwizard on 18.10.25.
//

#include "../RenderTarget.hpp"
#include "backend/Device.hpp"

namespace stc {

    RenderTarget::RenderTarget(const Device &device, const RenderTargetCreateInfo &create_info) {
        VkAttachmentDescription colorAttachment = {
            .format = (VkFormat) create_info.format,
            .samples = VK_SAMPLE_COUNT_1_BIT,
            .loadOp = VK_ATTACHMENT_LOAD_OP_CLEAR,
            .storeOp = VK_ATTACHMENT_STORE_OP_STORE,
            .stencilLoadOp = VK_ATTACHMENT_LOAD_OP_DONT_CARE,
            .stencilStoreOp = VK_ATTACHMENT_STORE_OP_DONT_CARE,
            .initialLayout = VK_IMAGE_LAYOUT_UNDEFINED,
            .finalLayout = VK_IMAGE_LAYOUT_PRESENT_SRC_KHR,
        };

        VkAttachmentReference colorAttachmentRef = {
            .attachment = 0,
            .layout = VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL,
        };

        VkSubpassDescription subpass = {
            .pipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS,
            .colorAttachmentCount = 1,
            .pColorAttachments = &colorAttachmentRef,
        };

        VkRenderPassCreateInfo render_pass_create_info = {
            .sType = VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO,
            .attachmentCount = 1,
            .pAttachments = &colorAttachment,
            .subpassCount = 1,
            .pSubpasses = &subpass,
        };

        std::vector<VkImageView> vk_attachments(create_info.colorAttachments.size());

        for (uint32_t i = 0 ; i < create_info.colorAttachments.size() ; i++) {
            vk_attachments[i] = create_info.colorAttachments[i].handle;
        }

        VkFramebufferCreateInfo frame_buffer_create_info = {
            .sType = VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO,
            .renderPass = render_pass.handle,
            .attachmentCount = static_cast<uint32_t>(vk_attachments.size()),
            .pAttachments = vk_attachments.data(),
            .width = create_info.extent.x,
            .height = create_info.extent.y,
            .layers = 1,
        };

        render_pass.New(device.handle, render_pass_create_info);
        auto& frame_buffer = frame_buffers.emplace_back();
        frame_buffer.New(device.handle, frame_buffer_create_info);
    }

    RenderTarget::~RenderTarget() {
        for (auto& frame_buffer : frame_buffers) {
            frame_buffer.Delete();
        }
        frame_buffers.clear();
        render_pass.Delete();
    }

}
