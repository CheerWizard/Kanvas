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
        meshBuffer.New(*context->device, 1000, 1000);
        cameraBuffer.New(*context->device);
        instanceBuffer.New(*context->device);
        materialBuffer.New(*context->device);
        indirectIndexBuffer.New(*context->device, 1000);
        threadPool.New(4);

        for (auto& fence : fences) {
            fence.New(*context->device, true);
        }

        for (auto& semaphore : imageSemaphores) {
            semaphore.New(*context->device);
        }

        for (auto& semaphore : renderFinishedSemaphores) {
            semaphore.New(*context->device);
        }

        // pipeline = PipelineBuilder {
        //     .vertexAttributes = Vertex::attributes,
        //     .vertexShader = testVertShader,
        //     .fragmentShader = testFragShader,
        //     .viewport = Viewport(window.width, window.height),
        //     .renderPass = context.surface->renderPass,
        // }.build(context.device);

        std::thread thread([this] { runLoop(); });
        thread.detach();
    }

    Renderer::~Renderer() {
        commandBufferMap.clear();
        running = false;
    }

    void Renderer::runLoop() {
        running = true;

        auto& deviceQueue = context->device->getQueue(
            context->device->queue_family_indices.graphics
        );

        for (auto& commandBuffer : commandBuffers) {
            commandBuffer.New(*deviceQueue, true);
        }

        while (running) {
            Frame frame;
            frame_queue.pop(frame);
            renderFrame(frame);
        }

        context->device->wait();
    }

    void Renderer::renderFrame(const Frame &frame) {
        const auto& predrawCommands = frame.predrawCommands;
        const auto& postdrawCommands = frame.postdrawCommands;
        auto& fence = fences[currentFrame];
        auto& imageSemaphore = imageSemaphores[currentFrame];
        auto& renderFinishedSemaphore = renderFinishedSemaphores[currentFrame];
        auto& commandBuffer = commandBuffers[currentFrame];

        // wait for frame
        fence->wait();
        u32 surfaceImageIndex;
        bool imageGet = surface->getImage(*imageSemaphore);
        if (!imageGet) {
            // we can't proceed rendering without image to present
            return;
        }
        fence->reset();

        // begin main command
        commandBuffer->reset();
        commandBuffer->begin();

        // execute pre draw commands
        commandBuffer->addToSubmit({
            .secondary = (CommandBufferHandle*) frame.predrawBuffers.data(),
            .secondaryCount = (u32) frame.predrawBuffers.size()
        });

        for (const auto& command : predrawCommands) {
            command.onDone();
        }

        // run rendering
        render(commandBuffer, currentFrame, surfaceImageIndex);

        // execute post draw commands
        commandBuffer->addToSubmit({
            .secondary = (CommandBufferHandle*) frame.postdrawBuffers.data(),
            .secondaryCount = static_cast<u32>(frame.postdrawBuffers.size())
        });

        for (const auto& command : postdrawCommands) {
            command.onDone();
        }

        commandBuffer->end();

        // submit frame
        commandBuffer->submit({
            .waitSemaphore = imageSemaphore,
            .signalSemaphore = renderFinishedSemaphore,
            .fence = fence
        });

        // present frame
        bool presented = commandBuffer->present({
            .swapchain = surface->swapchain,
            .imageIndex = surfaceImageIndex,
            .waitSemaphore = renderFinishedSemaphore
        });

        if (!presented || surface->needsResize) {
            surface->recreateSwapChain();
        }

        // next frame
        currentFrame = (currentFrame + 1) % frame_queue.getSize();
    }

    void Renderer::resize(int w, int h) {
        surface->resize(w, h);
    }

    void Renderer::render(Scope<CommandBuffer>& commandBuffer, u32 frame, u32 surfaceImageIndex) {
        u32 width = surface->render_target->info.width;
        u32 height = surface->render_target->info.height;

        commandBuffer->beginRenderPass({
            .renderTarget = surface->render_target,
            .colorAttachmentIndex = 0,
        });

        // commandBuffer->setPipeline(pipeline->handle);
        commandBuffer->setVertexBuffer(meshBuffer->vertexBuffer);
        commandBuffer->setIndexBuffer(meshBuffer->indexBuffer);
        commandBuffer->setViewport({ width, height });
        commandBuffer->setScissor(0, 0, width, height);
        commandBuffer->drawIndexedIndirect({
            .indirectBuffer = indirectIndexBuffer,
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
                    *context->device,
                    MEMORY_TYPE_HOST,
                    BUFFER_USAGE_TRANSFER_SRC,
                    sizeof(Vertex) * mesh.vertices.size(),
                };
                void* stageVertexMemory = stagingVertexBuffer.map();
                memcpy(stageVertexMemory, mesh.vertices.data(), sizeof(Vertex) * mesh.vertices.size());
                stagingVertexBuffer.unmap();

                Buffer stagingIndexBuffer = {
                    *context->device,
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
                    .srcBuffer = stagingVertexBuffer.handle,
                    .srcOffset = 0,
                    .dstBuffer = meshBuffer->vertexBuffer.handle,
                    .dstOffset = meshRegion.indirectData.vertexOffset,
                    .size = meshRegion.vertices
                });
                commandBuffer.copyBuffer({
                    .srcBuffer = stagingIndexBuffer.handle,
                    .srcOffset = 0,
                    .dstBuffer = meshBuffer->indexBuffer.handle,
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
        auto commandBuffer = commandBufferMap.find(threadId);
        if (commandBuffer == commandBufferMap.end()) {
            auto& queue = context->device->getQueue(
                context->device->queue_family_indices.transfer
            );
            auto newCommandBuffer = commandBufferMap.insert(
                { threadId, Scope<CommandBuffer>() }
            ).first->second;
            newCommandBuffer.New(*queue, false);
            return *newCommandBuffer;
        }
        return *commandBuffer->second;
    }

}
