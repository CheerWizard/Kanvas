//
// Created by cheerwizard on 18.10.25.
//

#include "VkCommandBufferResource.hpp"

#include "VkBufferResource.hpp"
#include "VkDeviceQueue.hpp"
#include "VkPipe.hpp"
#include "VkTexture.hpp"

void VkCommandBufferResource_reset(VkCommandBufferResource* command_buffer_resource) {
    command_buffer_resource->reset();
}

void VkCommandBufferResource_begin(VkCommandBufferResource* command_buffer_resource) {
    command_buffer_resource->begin();
}

void VkCommandBufferResource_end(VkCommandBufferResource* command_buffer_resource) {
    command_buffer_resource->end();
}

void VkCommandBufferResource_beginRenderPass(
    VkCommandBufferResource* command_buffer_resource,
    VkRenderTarget* render_target,
    u32 colorAttachmentIndex
) {
    command_buffer_resource->beginRenderPass(render_target, colorAttachmentIndex);
}

void VkCommandBufferResource_endRenderPass(VkCommandBufferResource* command_buffer_resource) {
    command_buffer_resource->endRenderPass();
}

void VkCommandBufferResource_setPipe(
    VkCommandBufferResource* command_buffer_resource,
    VkPipe* pipe
) {
    command_buffer_resource->setPipe(pipe);
}

void VkCommandBufferResource_setViewport(
    VkCommandBufferResource* command_buffer_resource,
    float x, float y, float width, float height, float minDepth, float maxDepth
) {
    command_buffer_resource->setViewport(x, y, width, height, minDepth, maxDepth);
}

void VkCommandBufferResource_setScissor(
    VkCommandBufferResource* command_buffer_resource,
    int x, int y, u32 w, u32 h
) {
    command_buffer_resource->setScissor(x, y, w, h);
}

void VkCommandBufferResource_addSecondaryBuffer(
    VkCommandBufferResource* command_buffer_resource,
    VkCommandBufferResource* secondary_buffer
) {
    command_buffer_resource->addSecondaryBuffer(secondary_buffer);
}

void VkCommandBufferResource_addSecondaryBuffers(
    VkCommandBufferResource* command_buffer_resource,
    VkCommandBufferResource** secondary_buffers,
    size_t secondary_buffers_count
) {
    command_buffer_resource->addSecondaryBuffers(secondary_buffers, secondary_buffers_count);
}

void VkCommandBufferResource_draw(
    VkCommandBufferResource* command_buffer_resource,
    u32 vertices, u32 vertexOffset, u32 instances, u32 instanceOffset
) {
    command_buffer_resource->draw(vertices, vertexOffset, instances, instanceOffset);
}

void VkCommandBufferResource_drawIndexed(
    VkCommandBufferResource* command_buffer_resource,
    u32 vertices, u32 vertexOffset, u32 indices, u32 indexOffset, u32 instances, u32 instanceOffset
) {
    command_buffer_resource->drawIndexed(vertices, vertexOffset, indices, indexOffset, instances, instanceOffset);
}

void VkCommandBufferResource_drawIndexedIndirect(
    VkCommandBufferResource* command_buffer_resource,
    VkBufferResource* indirectBuffer, size_t offset, u32 drawCount
) {
    command_buffer_resource->drawIndexedIndirect(indirectBuffer, offset, drawCount);
}

void VkCommandBufferResource_copyBufferToBuffer(
    VkCommandBufferResource* command_buffer_resource,
    VkBufferResource* srcBuffer, VkBufferResource* dstBuffer, size_t srcOffset, size_t dstOffset, size_t size
) {
    command_buffer_resource->copyBufferToBuffer(srcBuffer, dstBuffer, srcOffset, dstOffset, size);
}

void VkCommandBufferResource_copyBufferToImage(
    VkCommandBufferResource* command_buffer_resource,
    VkBufferResource* srcBuffer, VkTextureResource* dstImage, u32 dstMipLevel, u32 dstWidth, u32 dstHeight, u32 dstDepth
) {
    command_buffer_resource->copyBufferToImage(srcBuffer, dstImage, dstMipLevel, dstWidth, dstHeight, dstDepth);
}

void VkCommandBufferResource_copyImageToImage(
    VkCommandBufferResource* command_buffer_resource,
    VkTextureResource* srcImage, VkTextureResource* dstImage,
    int srcX, int srcY, int srcZ,
    int dstX, int dstY, int dstZ,
    u32 width, u32 height, u32 depth
) {
    command_buffer_resource->copyImageToImage(srcImage, dstImage, srcX, srcY, srcZ, dstX, dstY, dstZ, width, height, depth);
}

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

void VkCommandBufferResource::beginRenderPass(VkRenderTarget *render_target, u32 colorAttachmentIndex) {
    VkRenderPassBeginInfo beginInfo = {
        .sType = VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO,
        .renderPass = render_target->render_pass,
        .framebuffer = render_target->framebuffer,
        .renderArea = {
            .offset = { .x = render_target->info.x, .y = render_target->info.y },
            .extent = { .width = render_target->info.width, .height = render_target->info.height }
        },
    };

    VkClearValue clearValue = {};
    u32 clearValueCount = 0;

    if (colorAttachmentIndex < render_target->info.colorAttachmentsCount) {
        const auto& color_attachment = render_target->info.colorAttachments[colorAttachmentIndex];
        const auto& depth_attachment = render_target->info.depthAttachment;
        const auto& clearColor = color_attachment.clearColor;

        clearValue.color.float32[0] = clearColor[0];
        clearValue.color.float32[1] = clearColor[1];
        clearValue.color.float32[2] = clearColor[2];
        clearValue.color.float32[3] = clearColor[3];

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

void VkCommandBufferResource::setViewport(float x, float y, float width, float height, float minDepth, float maxDepth) const {
    VkViewport viewport = {
        .x = x,
        .y = y,
        .width = width,
        .height = height,
        .minDepth = minDepth,
        .maxDepth = maxDepth,
    };
    vkCmdSetViewport(command_buffer, 0, 1, &viewport);
}

void VkCommandBufferResource::setPipe(VkPipe* pipe) const {
    setPipeline(VK_PIPELINE_BIND_POINT_GRAPHICS, pipe->pipeline);
    setVertexBuffer(pipe->info.vertexBuffer);
    setIndexBuffer(pipe->info.indexBuffer);
    // TODO set descriptor sets
}

void VkCommandBufferResource::setPipeline(VkPipelineBindPoint pipeline_bind_point, VkPipeline pipeline) const {
    vkCmdBindPipeline(command_buffer, pipeline_bind_point, pipeline);
}

void VkCommandBufferResource::setVertexBuffer(VkBufferResource *buffer) const {
    VkBuffer buffers[] = { buffer->buffer };
    VkDeviceSize offsets[] = { 0 };
    vkCmdBindVertexBuffers(command_buffer, 0, 1, buffers, offsets);
}

void VkCommandBufferResource::setIndexBuffer(VkBufferResource *buffer) const {
    vkCmdBindIndexBuffer(command_buffer, buffer->buffer, 0, VK_INDEX_TYPE_UINT32);
}

void VkCommandBufferResource::setDescriptorSet(
    VkPipelineBindPoint pipeline_bind_point,
    VkPipelineLayout pipeline_layout,
    VkDescriptorSet descriptorSet,
    uint32_t set
) const {
    vkCmdBindDescriptorSets(
      command_buffer,
      pipeline_bind_point,
      pipeline_layout,
      set,
      1, &descriptorSet,
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

void VkCommandBufferResource::draw(u32 vertices, u32 vertexOffset, u32 instances, u32 instanceOffset) const {
    vkCmdDraw(command_buffer, vertices, instances, vertexOffset, instanceOffset);
}

void VkCommandBufferResource::drawIndexed(u32 vertices, u32 vertexOffset, u32 indices, u32 indexOffset, u32 instances, u32 instanceOffset) const {
    vkCmdDrawIndexed(command_buffer, indices, instances, indexOffset, vertexOffset, instanceOffset);
}

void VkCommandBufferResource::drawIndexedIndirect(VkBufferResource *indirectBuffer, size_t offset, u32 drawCount) const {
    vkCmdDrawIndexedIndirect(command_buffer, indirectBuffer->buffer, offset, drawCount, sizeof(VkDrawIndexedIndirectCommand));
}

void VkCommandBufferResource::addSecondaryBuffers(VkCommandBufferResource** secondaryBuffers, size_t secondaryBuffersCount) const {
    for (int i = 0 ; i < secondaryBuffersCount ; i++) {
        addSecondaryBuffer(secondaryBuffers[i]);
    }
}

void VkCommandBufferResource::addSecondaryBuffer(VkCommandBufferResource* secondaryBuffer) const {
    vkCmdExecuteCommands(command_buffer, 1, &secondaryBuffer->command_buffer);
}

void VkCommandBufferResource::submit(VkSemaphoreResource *waitSemaphore, VkSemaphoreResource *signalSemaphore, VkFenceResource *fence) const {
    VkPipelineStageFlags waitStages[] = { VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT };
    VkSemaphore waitSemaphores[] = { waitSemaphore->semaphore };
    VkSemaphore signalSemaphores[] = { signalSemaphore->semaphore };

    VkSubmitInfo submitInfo {
        .sType = VK_STRUCTURE_TYPE_SUBMIT_INFO,
        .waitSemaphoreCount = 1,
        .pWaitSemaphores = waitSemaphores,
        .pWaitDstStageMask = waitStages,
        .commandBufferCount = 1,
        .pCommandBuffers = &command_buffer,
        .signalSemaphoreCount = 1,
        .pSignalSemaphores = signalSemaphores,
    };

    VK_CHECK(vkQueueSubmit(device_queue.queue, 1, &submitInfo, fence->fence));
}

bool VkCommandBufferResource::present(VkSurface *surface, VkSemaphoreResource *waitSemaphore) const {
    if (surface == nullptr) {
        // surface must be created
        return false;
    }

    VkSemaphore waitSemaphores[] = { waitSemaphore->semaphore };
    VkSwapchainKHR swapchains[] = { surface->swapchain };

    VkPresentInfoKHR presentInfo = {
        .sType = VK_STRUCTURE_TYPE_PRESENT_INFO_KHR,
        .waitSemaphoreCount = 1,
        .pWaitSemaphores = waitSemaphores,
        .swapchainCount = 1,
        .pSwapchains = swapchains,
        .pImageIndices = &surface->currentImageIndex,
    };

    VkResult result = vkQueuePresentKHR(device_queue.queue, &presentInfo);

    if (result == VK_ERROR_OUT_OF_DATE_KHR || result == VK_SUBOPTIMAL_KHR) {
        return false;
    }

    if (result != VK_SUCCESS) {
        ASSERT(false, TAG, "Failed to present image");
    }

    return true;
}

void VkCommandBufferResource::copyBufferToBuffer(VkBufferResource *srcBuffer, VkBufferResource *dstBuffer, size_t srcOffset, size_t dstOffset, size_t size) const {
    VkBufferCopy copyRegion = {
        .srcOffset = srcOffset,
        .dstOffset = dstOffset,
        .size = size,
    };
    vkCmdCopyBuffer(command_buffer, srcBuffer->buffer, dstBuffer->buffer, 1, &copyRegion);
}

void VkCommandBufferResource::copyBufferToImage(VkBufferResource *srcBuffer, VkTextureResource *dstImage, u32 dstMipLevel, u32 dstWidth, u32 dstHeight, u32 dstDepth) const {
    VkBufferImageCopy copyRegion = {
        .bufferOffset = 0,
        .bufferRowLength = 0,
        .bufferImageHeight = 0,
        .imageSubresource = {
            .aspectMask = VK_IMAGE_ASPECT_COLOR_BIT,
            .mipLevel = dstMipLevel,
            .baseArrayLayer = 0,
            .layerCount = 1,
        },
        .imageOffset = { 0, 0, 0 },
        .imageExtent = {
            .width = dstWidth,
            .height = dstHeight,
            .depth = dstDepth
        },
    };
    vkCmdCopyBufferToImage(command_buffer, srcBuffer->buffer, dstImage->image, VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, 1, &copyRegion);
}

void VkCommandBufferResource::copyImageToImage(
    VkTextureResource *srcImage, VkTextureResource *dstImage,
    int srcX, int srcY, int srcZ,
    int dstX, int dstY, int dstZ,
    u32 width, u32 height, u32 depth
) const {
    VkImageCopy copyRegion = {
        .srcSubresource = {
            .aspectMask = VK_IMAGE_ASPECT_COLOR_BIT,
            .mipLevel = 1,
            .baseArrayLayer = 0,
            .layerCount = 1,
        },
        .srcOffset = {
            .x = srcX,
            .y = srcY,
            .z = srcZ,
        },
        .dstSubresource = {
            .aspectMask = VK_IMAGE_ASPECT_COLOR_BIT,
            .mipLevel = 1,
            .baseArrayLayer = 0,
            .layerCount = 1,
        },
        .dstOffset = {
            .x = dstX,
            .y = dstY,
            .z = dstZ,
        },
        .extent = {
            .width = width,
            .height = height,
            .depth = depth
        },
    };

    vkCmdCopyImage(command_buffer,
        srcImage->image, VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL,
        dstImage->image, VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL,
        1, &copyRegion
    );
}

