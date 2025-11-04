//
// Created by cheerwizard on 18.10.25.
//

#include "../CommandBuffer.hpp"
#include "backend/DeviceQueue.hpp"
#include "backend/Pipeline.hpp"
#include "frontend/Viewport.hpp"

stc::CommandBuffer::CommandBuffer(DeviceQueue& device_queue, bool isPrimary) : device_queue(device_queue) {
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

    CALL(vkAllocateCommandBuffers(device_queue.pool.device, &allocInfo, &handle));
}

stc::CommandBuffer::~CommandBuffer() {
    if (handle != VK_NULL_HANDLE) {
        vkFreeCommandBuffers(device_queue.pool.device, device_queue.pool, 1, &handle);
        handle = VK_NULL_HANDLE;
    }
}

void stc::CommandBuffer::reset() const {
    CALL(vkResetCommandBuffer(handle, 0));
}

void stc::CommandBuffer::begin() const {
    VkCommandBufferBeginInfo beginInfo { VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO };
    CALL(vkBeginCommandBuffer(handle, &beginInfo));
}

void stc::CommandBuffer::end() const {
    CALL(vkEndCommandBuffer(handle));
}

void stc::CommandBuffer::beginRenderPass(const RenderPassCommand& command) const {
    VkRenderPassBeginInfo beginInfo = {
        .sType = VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO,
        .renderPass = command.renderTarget.render_pass.handle,
        .framebuffer = command.renderTarget.frame_buffer.handle,
        .renderArea = {
            .offset = { .x = command.renderAreaOffset.x, .y = command.renderAreaOffset.y },
            .extent = { .width = command.renderAreaExtent.x, .height = command.renderAreaExtent.y }
        },
    };

    VkClearValue clearValue = {
        .color = {
            .float32 = command.clearColor[0]
        }
    };

    beginInfo.clearValueCount = 1;
    beginInfo.pClearValues = &clearValue;

    vkCmdBeginRenderPass(handle, &beginInfo, VK_SUBPASS_CONTENTS_INLINE);
}

void stc::CommandBuffer::endRenderPass() const {
    vkCmdEndRenderPass(handle);
}

void stc::CommandBuffer::setViewport(const Viewport &viewport) const {
    VkViewport vkViewport = {
        .x = viewport.x,
        .y = viewport.y,
        .width = static_cast<float>(viewport.width),
        .height = static_cast<float>(viewport.height),
        .minDepth = viewport.minDepth,
        .maxDepth = viewport.maxDepth,
    };
    vkCmdSetViewport(handle, 0, 1, &vkViewport);
}

void stc::CommandBuffer::setPipeline(const Pipeline &pipeline) const {
    vkCmdBindPipeline(handle, VK_PIPELINE_BIND_POINT_GRAPHICS, pipeline.handle);
}

void stc::CommandBuffer::setVertexBuffer(const Buffer& buffer) const {
    VkBuffer buffers[] = { buffer.handle };
    VkDeviceSize offsets[] = { 0 };
    vkCmdBindVertexBuffers(handle, 0, 1, buffers, offsets);
}

void stc::CommandBuffer::setIndexBuffer(const Buffer& buffer) const {
    vkCmdBindIndexBuffer(handle, buffer.handle, 0, VK_INDEX_TYPE_UINT32);
}

void stc::CommandBuffer::setPipelineBinding(const Pipeline& pipeline, const BindingLayout &binding_layout) const {
    vkCmdBindDescriptorSets(
        handle,
        VK_PIPELINE_BIND_POINT_GRAPHICS,
        pipeline.layout.handle,
        0,
        1, &binding_layout.set,
        0, nullptr
    );
}

void stc::CommandBuffer::setScissor(int x, int y, u32 w, u32 h) const {
    VkRect2D scissor = {
        .offset = { .x = x, .y = y },
        .extent = { .width = w, .height = h },
    };
    vkCmdSetScissor(handle, 0, 1, &scissor);
}

void stc::CommandBuffer::draw(const DrawCommand &command) const {
    vkCmdDraw(handle, command.vertices, command.instances, command.vertexOffset, command.instanceOffset);
}

void stc::CommandBuffer::drawIndexed(const DrawIndexedCommand &command) const {
    vkCmdDrawIndexed(handle, command.indices, command.instances, command.indexOffset, command.vertexOffset, command.instanceOffset);
}

void stc::CommandBuffer::drawIndexedIndirect(const DrawIndexedIndirectCommand &command) const {
    vkCmdDrawIndexedIndirect(handle, command.indirectBuffer.handle, command.offset, command.drawCount, sizeof(VkDrawIndexedIndirectCommand));
}

void stc::CommandBuffer::addToSubmit(const AddToSubmitCommand &command) const {
    vkCmdExecuteCommands(handle, command.secondaryCount, command.secondary);
}

void stc::CommandBuffer::submit(const SubmitCommand& command) const {
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

bool stc::CommandBuffer::present(const PresentCommand& command) const {
    VkSemaphore waitSemaphores[] = { command.waitSemaphore.handle };
    VkSwapchainKHR swapchains[] = { command.swapchain.handle };
    VkPresentInfoKHR presentInfo = {
        .sType = VK_STRUCTURE_TYPE_PRESENT_INFO_KHR,
        .waitSemaphoreCount = 1,
        .pWaitSemaphores = waitSemaphores,
        .swapchainCount = 1,
        .pSwapchains = swapchains,
        .pImageIndices = &command.imageIndex,
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

void stc::CommandBuffer::copyBuffer(const CopyBufferCommand &command) const {
    VkBufferCopy copyRegion = {
        .srcOffset = command.srcOffset,
        .dstOffset = command.dstOffset,
        .size = command.size,
    };
    vkCmdCopyBuffer(handle, command.srcBuffer, command.dstBuffer, 1, &copyRegion);
}

void stc::CommandBuffer::copyBufferToImage(const CopyBufferToImageCommand &command) const {
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
    vkCmdCopyBufferToImage(handle, command.srcBuffer.handle, command.dstImage.handle, VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, 1, &copyRegion);
}