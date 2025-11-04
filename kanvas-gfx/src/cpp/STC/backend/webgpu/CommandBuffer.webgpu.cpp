//
// Created by cheerwizard on 18.10.25.
//

#include "../CommandBuffer.hpp"
#include "backend/DeviceQueue.hpp"
#include "backend/Pipeline.hpp"
#include "frontend/Viewport.hpp"

stc::CommandBuffer::CommandBuffer(DeviceQueue& device_queue, bool isPrimary) : device_queue(device_queue) {
    encoder.New(device_queue.device, WGPUCommandEncoderDescriptor {
        .label = "CommandBufferEncoder",
    });
}

stc::CommandBuffer::~CommandBuffer() {
    // no-op
}

void stc::CommandBuffer::reset() const {
    // no-op
}

void stc::CommandBuffer::begin() const {
    // no-op
}

void stc::CommandBuffer::end() {
    WGPUCommandBufferDescriptor descriptor = {
        .label = "CommandBuffer",
    };
    handle = wgpuCommandEncoderFinish(encoder.handle, &descriptor);
}

void stc::CommandBuffer::beginRenderPass(const RenderPassCommand& command) {
    render_pass = command.renderTarget->render_pass;
}

void stc::CommandBuffer::endRenderPass() const {
    wgpuRenderPassEncoderEnd(render_pass.handle);
}

void stc::CommandBuffer::setViewport(const Viewport &viewport) const {
    wgpuRenderPassEncoderSetViewport(
        render_pass.handle,
        viewport.x, viewport.y,
        (float) viewport.width, (float) viewport.height,
        viewport.minDepth, viewport.maxDepth
    );
}

void stc::CommandBuffer::setPipeline(const Pipeline &pipeline) const {
    wgpuRenderPassEncoderSetPipeline(render_pass.handle, pipeline.handle);
}

void stc::CommandBuffer::setVertexBuffer(const Buffer& buffer) const {
    wgpuRenderPassEncoderSetVertexBuffer(render_pass.handle, buffer.slot, buffer.handle, 0, buffer.size);
}

void stc::CommandBuffer::setIndexBuffer(const Buffer& buffer) const {
    wgpuRenderPassEncoderSetIndexBuffer(render_pass.handle, buffer.handle, WGPUIndexFormat_Uint16, 0, buffer.size);
}

void stc::CommandBuffer::setPipelineBinding(const Pipeline& pipeline, const BindingLayout &binding_layout) const {
    wgpuRenderPassEncoderSetBindGroup(render_pass.handle, 0, binding_layout.group.handle, 0, nullptr);
}

void stc::CommandBuffer::setScissor(int x, int y, u32 w, u32 h) const {
    wgpuRenderPassEncoderSetScissorRect(render_pass.handle, x, y, w, h);
}

void stc::CommandBuffer::draw(const DrawCommand &command) const {
    wgpuRenderPassEncoderDraw(render_pass.handle, command.vertices, command.instances, command.vertexOffset, command.instanceOffset);
}

void stc::CommandBuffer::drawIndexed(const DrawIndexedCommand &command) const {
    wgpuRenderPassEncoderDrawIndexed(render_pass.handle, command.indices, command.instances, command.indexOffset, command.vertexOffset, command.instanceOffset);
}

void stc::CommandBuffer::drawIndexedIndirect(const DrawIndexedIndirectCommand &command) const {
    wgpuRenderPassEncoderDrawIndirect(render_pass.handle, command.indirectBuffer.handle, command.offset);
}

void stc::CommandBuffer::addToSubmit(const AddToSubmitCommand &command) const {
    // no-op
}

void stc::CommandBuffer::submit(const SubmitCommand& command) const {
    wgpuQueueSubmit(device_queue.handle, 1, &handle);
}

bool stc::CommandBuffer::present(const PresentCommand& command) const {
    // no-op
    return true;
}

void stc::CommandBuffer::copyBuffer(const CopyBufferCommand &command) const {
    wgpuCommandEncoderCopyBufferToBuffer(
        encoder.handle,
        command.srcBuffer.handle,
        command.srcOffset,
        command.dstBuffer.handle,
        command.dstOffset,
        command.size
    );
}

void stc::CommandBuffer::copyBufferToImage(const CopyBufferToImageCommand &command) const {
    WGPUImageCopyBuffer src = {
        .buffer = command.srcBuffer.handle,
    };

    WGPUImageCopyTexture dst = {
        .texture = command.dstImage.handle,
        .mipLevel = command.dstMipLevel,
    };

    WGPUExtent3D extent = {
        .width = command.dstWidth,
        .height = command.dstHeight,
        .depthOrArrayLayers = command.dstDepth,
    };

    wgpuCommandEncoderCopyBufferToTexture(encoder.handle, &src, &dst, &extent);
}