//
// Created by cheerwizard on 18.10.25.
//

#include "VkRenderTarget.hpp"

#include <vector>

#include "VkContext.hpp"

VkRenderTarget* VkRenderTarget_create(VkContext* context, VkRenderTargetInfo* info) {
    return new VkRenderTarget(context->device, *info);
}

void VkRenderTarget_destroy(VkRenderTarget* render_target) {
    delete render_target;
}

void VkRenderTarget_resize(VkRenderTarget* render_target, u32 width, u32 height) {
    render_target->resize(width, height);
}

VkRenderTarget::VkRenderTarget(VkDevice device, const VkRenderTargetInfo &info)
: device(device), info(info) {
    std::vector<VkAttachmentDescription> vk_color_attachments(info.colorAttachmentsCount);
    std::vector<VkAttachmentReference> vk_color_references(info.colorAttachmentsCount);
    std::vector<VkImageView> vk_image_views(info.colorAttachmentsCount);

    for (u32 i = 0 ; i < info.colorAttachmentsCount ; i++) {
        auto& vk_color_attachment = vk_color_attachments[i];
        auto& vk_color_reference = vk_color_references[i];
        const auto& color_attachment = info.colorAttachments[i];

        vk_color_attachment.format = color_attachment.format;
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

        vk_image_views[i] = info.colorAttachments[i].view;
    }

    VkSubpassDescription subpass = {
            .pipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS,
            .colorAttachmentCount = (u32) vk_color_attachments.size(),
            .pColorAttachments = vk_color_references.data(),
    };

    VkRenderPassCreateInfo renderPassCreateInfo = {
            .sType = VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO,
            .attachmentCount = (u32) vk_color_attachments.size(),
            .pAttachments = vk_color_attachments.data(),
            .subpassCount = 1,
            .pSubpasses = &subpass,
    };

    VK_CHECK(vkCreateRenderPass(device, &renderPassCreateInfo, VK_CALLBACKS, &render_pass));

    VkFramebufferCreateInfo framebufferCreateInfo = {
            .sType = VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO,
            .renderPass = render_pass,
            .attachmentCount = (u32) vk_image_views.size(),
            .pAttachments = vk_image_views.data(),
            .width = info.width,
            .height = info.height,
            .layers = info.depth,
    };

    VK_CHECK(vkCreateFramebuffer(device, &framebufferCreateInfo, VK_CALLBACKS, &framebuffer));
}

VkRenderTarget::~VkRenderTarget() {
    for (int i = 0 ; i < info.colorAttachmentsCount ; i++) {
        vkDestroyImageView(device, info.colorAttachments[i].view, VK_CALLBACKS);
    }
    info.colorAttachments = nullptr;
    info.colorAttachmentsCount = 0;

    if (framebuffer) {
        vkDestroyFramebuffer(device, framebuffer, VK_CALLBACKS);
        framebuffer = nullptr;
    }

    if (render_pass) {
        vkDestroyRenderPass(device, render_pass, VK_CALLBACKS);
    }
}

void VkRenderTarget::resize(int width, int height) {
    // TODO: not implemented yet!
}