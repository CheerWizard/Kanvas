//
// Created by cheerwizard on 18.10.25.
//

#include "VkCommandBufferResource.hpp"

VkCommandBufferResource::VkCommandBufferResource(VkDeviceQueue& device_queue, bool isPrimary)
: device_queue(device_queue) {
    VkCommandBufferLevel level;
    if (isPrimary) {
        level = VK_COMMAND_BUFFER_LEVEL_PRIMARY;
    } else {
        level = VK_COMMAND_BUFFER_LEVEL_SECONDARY;
    }

    VkCommandBufferAllocateInfo allocInfo {
        .sType = VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO,
        .commandPool = device_queue.pool,
        .level = level,
        .commandBufferCount = 1
    };

    VK_CHECK(vkAllocateCommandBuffers(device_queue.device, &allocInfo, &command_buffer));
}

VkCommandBufferResource::~VkCommandBufferResource() {
    if (command_buffer) {
        vkFreeCommandBuffers(device_queue.device, device_queue.pool, 1, &command_buffer);
        command_buffer = nullptr;
    }
}

void VkCommandBufferResource::reset() const {
    VK_CHECK(vkResetCommandBuffer(command_buffer, 0));
}

void VkCommandBufferResource::begin() const {
    VkCommandBufferBeginInfo beginInfo { VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO };
    VK_CHECK(vkBeginCommandBuffer(command_buffer, &beginInfo));
}

void VkCommandBufferResource::end() {
    VK_CHECK(vkEndCommandBuffer(command_buffer));
}

void VkCommandBufferResource::beginRenderPass(const RenderPassCommand& command) {
    VkRenderPassBeginInfo beginInfo = {
        .sType = VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO,
        .renderPass = command.renderTarget->render_pass,
        .framebuffer = command.renderTarget->framebuffer,
        .renderArea = {
            .offset = { .x = command.renderTarget->info.x, .y = command.renderTarget->info.y },
            .extent = { .width = command.renderTarget->info.width, .height = command.renderTarget->info.height }
        },
    };

    VkClearValue clearValue = {};
    u32 clearValueCount = 0;

    if (command.colorAttachmentIndex < command.renderTarget->info.colorAttachmentsCount) {
        const auto& color_attachment = command.renderTarget->info.colorAttachments[command.colorAttachmentIndex];
        const auto& depth_attachment = command.renderTarget->info.depthAttachment;
        const auto& clearColor = color_attachment.clearColor;

        clearValue.color.float32 = clearColor;

        if (depth_attachment && depth_attachment->enabled) {
            clearValue.depthStencil.depth = depth_attachment->depthClearValue;
            clearValue.depthStencil.stencil = depth_attachment->stencilClearValue;
        }

        clearValueCount = 1;
    }

    beginInfo.clearValueCount = clearValueCount;
    beginInfo.pClearValues = &clearValue;

    vkCmdBeginRenderPass(command_buffer, &beginInfo, VK_SUBPASS_CONTENTS_INLINE);
}

void VkCommandBufferResource::endRenderPass() const {
    vkCmdEndRenderPass(command_buffer);
}

void VkCommandBufferResource::setViewport(const VkViewport &viewport) const {
    vkCmdSetViewport(command_buffer, 0, 1, &viewport);
}

void VkCommandBufferResource::setPipeline(const VkPipeline &pipeline) const {
    vkCmdBindPipeline(command_buffer, VK_PIPELINE_BIND_POINT_GRAPHICS, pipeline);
}

void VkCommandBufferResource::setVertexBuffer(const VkBufferResource& buffer) const {
    VkBuffer buffers[] = { buffer.buffer };
    VkDeviceSize offsets[] = { 0 };
    vkCmdBindVertexBuffers(command_buffer, 0, 1, buffers, offsets);
}

void VkCommandBufferResource::setIndexBuffer(const VkBufferResource& buffer) const {
    vkCmdBindIndexBuffer(command_buffer, buffer.buffer, 0, VK_INDEX_TYPE_UINT32);
}

void VkCommandBufferResource::setDescriptorSet(VkPipelineLayout pipeline, VkDescriptorSet set) const {
    vkCmdBindDescriptorSets(
        command_buffer,
        VK_PIPELINE_BIND_POINT_GRAPHICS,
        pipeline,
        0,
        1, &set,
        0, nullptr
    );
}

void VkCommandBufferResource::setScissor(int x, int y, u32 w, u32 h) const {
    VkRect2D scissor = {
        .offset = { .x = x, .y = y },
        .extent = { .width = w, .height = h },
    };
    vkCmdSetScissor(command_buffer, 0, 1, &scissor);
}

void VkCommandBufferResource::draw(const DrawCommand &command) const {
    vkCmdDraw(command_buffer, command.vertices, command.instances, command.vertexOffset, command.instanceOffset);
}

void VkCommandBufferResource::drawIndexed(const DrawIndexedCommand &command) const {
    vkCmdDrawIndexed(command_buffer, command.indices, command.instances, command.indexOffset, command.vertexOffset, command.instanceOffset);
}

void VkCommandBufferResource::drawIndexedIndirect(const DrawIndexedIndirectCommand &command) const {
    vkCmdDrawIndexedIndirect(command_buffer, command.indirectBuffer, command.offset, command.drawCount, sizeof(VkDrawIndexedIndirectCommand));
}

void VkCommandBufferResource::addToSubmit(const AddToSubmitCommand &command) const {
    vkCmdExecuteCommands(command_buffer, command.secondaryCount, command.secondary->);
}

void VkCommandBufferResource::submit(const SubmitCommand& command) const {
    VkPipelineStageFlags waitStages[] = { VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT };
    VkSemaphore waitSemaphores[] = { command.waitSemaphore.handle };
    VkSemaphore signalSemaphores[] = { command.signalSemaphore.handle };

    VkSubmitInfo submitInfo {
        .sType = VK_STRUCTURE_TYPE_SUBMIT_INFO,
        .waitSemaphoreCount = 1,
        .pWaitSemaphores = waitSemaphores,
        .pWaitDstStageMask = waitStages,
        .commandBufferCount = 1,
        .pCommandBuffers = &handle,
        .signalSemaphoreCount = 1,
        .pSignalSemaphores = signalSemaphores,
    };

    CALL(vkQueueSubmit(device_queue.handle, 1, &submitInfo, command.fence.handle));
}

bool VkCommandBufferResource::present(const PresentCommand& command) const {
    if (command.surface == nullptr) {
        // surface must be created
        return false;
    }

    VkSemaphore waitSemaphores[] = { command.waitSemaphore.handle };
    VkSwapchainKHR swapchains[] = { command.surface->swapchain };

    VkPresentInfoKHR presentInfo = {
        .sType = VK_STRUCTURE_TYPE_PRESENT_INFO_KHR,
        .waitSemaphoreCount = 1,
        .pWaitSemaphores = waitSemaphores,
        .swapchainCount = 1,
        .pSwapchains = swapchains,
        .pImageIndices = &command.surface->currentImageIndex,
    };

    VkResult result = vkQueuePresentKHR(device_queue.handle, &presentInfo);

    if (result == VK_ERROR_OUT_OF_DATE_KHR || result == VK_SUBOPTIMAL_KHR) {
        return false;
    }

    if (result != VK_SUCCESS) {
        ASSERT(false, TAG, "Failed to present image");
    }

    return true;
}

void VkCommandBufferResource::copyBuffer(const CopyBufferCommand &command) const {
    VkBufferCopy copyRegion = {
        .srcOffset = command.srcOffset,
        .dstOffset = command.dstOffset,
        .size = command.size,
    };
    vkCmdCopyBuffer(handle, command.srcBuffer, command.dstBuffer, 1, &copyRegion);
}

void VkCommandBufferResource::copyBufferToImage(const CopyBufferToImageCommand &command) const {
    VkBufferImageCopy copyRegion = {
        .bufferOffset = 0,
        .bufferRowLength = 0,
        .bufferImageHeight = 0,
        .imageSubresource = {
            .aspectMask = VK_IMAGE_ASPECT_COLOR_BIT,
            .mipLevel = command.dstMipLevel,
            .baseArrayLayer = 0,
            .layerCount = 1,
        },
        .imageOffset = { 0, 0, 0 },
        .imageExtent = {
            .width = command.dstWidth,
            .height = command.dstHeight,
            .depth = command.dstDepth
        },
    };
    vkCmdCopyBufferToImage(handle, command.srcBuffer, command.dstImage, VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, 1, &copyRegion);
}