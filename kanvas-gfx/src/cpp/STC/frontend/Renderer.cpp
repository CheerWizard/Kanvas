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
        threadPool.New(4);
    }

    Renderer::~Renderer() {
        commandBuffers.clear();
    }

    void Renderer::resize(int w, int h) {
        surface->resize(w, h);
    }

    void Renderer::render(Scope<CommandBuffer>& commandBuffer, u32 frame, u32 surfaceImageIndex) {
        u32 width = surface->width;
        u32 height = surface->height;

        commandBuffer->beginRenderPass({
            .renderTarget = surface->render_target,
            // .renderPass = surface->renderPass,
            // .framebuffer = surface->framebuffers[surfaceImageIndex],
            .clearColor = { 1.0f, 0.0f, 0.0, 1.0f },
            .clearDepth = 1,
            .renderAreaX = 0,
            .renderAreaY = 0,
            .renderAreaW = width,
            .renderAreaH = height,
        });
        // commandBuffer->setPipeline(pipeline->handle);
        commandBuffer->setVertexBuffer(meshBuffer->vertexBuffer);
        commandBuffer->setIndexBuffer(meshBuffer->indexBuffer);
        commandBuffer->setViewport({ width, height });
        commandBuffer->setScissor(0, 0, width, height);
        commandBuffer->drawIndexedIndirect({
            .indirectBuffer = indirectIndexBuffer->handle,
            .offset = frame * sizeof(IndirectIndexData) * indirectIndexBuffer->count,
            .drawCount = indirectIndexBuffer->count
        });
        commandBuffer->endRenderPass();
    }

    void Renderer::uploadMesh(const Mesh &mesh, bool &ready, const std::function<void(const Command &)> &onDone) {
        ready = false;
        threadPool->submit({
            .task = [&, this, mesh]() {
                Buffer stagingVertexBuffer = {
                    MEMORY_TYPE_HOST,
                    BUFFER_USAGE_TRANSFER_SRC,
                    sizeof(Vertex) * mesh.vertices.size(),
                };
                void* stageVertexMemory = stagingVertexBuffer.map();
                memcpy(stageVertexMemory, mesh.vertices.data(), sizeof(Vertex) * mesh.vertices.size());
                stagingVertexBuffer.unmap();

                Buffer stagingIndexBuffer = {
                    MEMORY_TYPE_HOST,
                    BUFFER_USAGE_TRANSFER_SRC,
                    sizeof(u32) * mesh.indices.size(),
                };
                void* stageIndexMemory = stagingIndexBuffer.map();
                memcpy(stageIndexMemory, mesh.indices.data(), sizeof(u32) * mesh.indices.size());
                stagingIndexBuffer.unmap();

                MeshRegion meshRegion = meshBuffer->allocate(mesh);

                auto& commandBuffer = getCommandBuffer(std::this_thread::get_id());
                commandBuffer.begin();
                commandBuffer.copyBuffer({
                    .srcBuffer = stagingVertexBuffer,
                    .srcOffset = 0,
                    .dstBuffer = meshBuffer->vertexBuffer,
                    .dstOffset = meshRegion.indirectData.vertexOffset,
                    .size = meshRegion.vertices
                });
                commandBuffer.copyBuffer({
                    .srcBuffer = stagingIndexBuffer,
                    .srcOffset = 0,
                    .dstBuffer = meshBuffer->indexBuffer,
                    .dstOffset = meshRegion.indirectData.indexOffset,
                    .size = meshRegion.indirectData.indices
                });
                commandBuffer.end();

                onDone({
                    .buffer = &commandBuffer,
                    .onDone = [&] {
                        ready = true;
                    }
                });
            }
        });
    }

    CommandBuffer& Renderer::getCommandBuffer(std::thread::id threadId) {
        auto commandBuffer = commandBuffers.find(threadId);
        if (commandBuffer == commandBuffers.end()) {
            auto& queue = context->device->getQueue(
                context->device->queue_family_indices.transfer
            );
            auto newCommandBuffer = commandBuffers.insert({ threadId, Scope<CommandBuffer>() }).first->second;
            newCommandBuffer.New(*queue, false);
            return *newCommandBuffer;
        }
        return *commandBuffer->second;
    }

}
