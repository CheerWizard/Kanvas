//
// Created by cheerwizard on 05.07.25.
//

#include "Renderer.hpp"
#include "Viewport.hpp"

namespace stc {

    Renderer::Renderer(const RenderConfig& render_config) {
        context.New(ContextCreateInfo {
            .render_config = render_config,
        });
        surface.New(*context->device, context->surface, render_config.width, render_config.height);
        meshBuffer.New(1000, 1000);
        cameraBuffer.New(*context->device);
        instanceBuffer.New(*context->device);
        materialBuffer.New(*context->device);
        indirectIndexBuffer.New(1000);
    }

    void Renderer::resize(int w, int h) {
        surface->resize(w, h);
    }

    void Renderer::render(Scope<CommandBuffer>& commandBuffer, u32 frame, u32 surfaceImageIndex) {
        auto extent = surface->extent;

        commandBuffer->beginRenderPass({
            .renderPass = surface->renderPass,
            .framebuffer = surface->framebuffers[surfaceImageIndex],
            .clearColor = { 1.0f, 0.0f, 0.0, 1.0f },
            .clearDepth = 1,
            .renderAreaOffset = { 0, 0 },
            .renderAreaExtent = extent
        });
        commandBuffer->setPipeline(pipeline->handle);
        commandBuffer->setVertexBuffer(meshBuffer->vertexBuffer);
        commandBuffer->setIndexBuffer(meshBuffer->indexBuffer);
        commandBuffer->setViewport({ extent.x, extent.y });
        commandBuffer->setScissor({}, extent);
        commandBuffer->drawIndexedIndirect({
            .indirectBuffer = indirectIndexBuffer->handle,
            .offset = frame * sizeof(IndirectIndexData) * indirectIndexBuffer->count,
            .drawCount = indirectIndexBuffer->count
        });
        commandBuffer->endRenderPass();
    }
}
