//
// Created by cheerwizard on 05.07.25.
//

#include "RenderApi.hpp"
#include "Viewport.hpp"

namespace stc {

    RenderApi::RenderApi(void* native_window, const RenderApiCreateInfo& create_info) {
        context.New(ContextCreateInfo {
            .native_window = native_window,
            .render_api_create_info = create_info,
        });
        shaderManager.New(*context->device);
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

        thread.New([this] { run(); });
        thread->detach();
    }

    RenderApi::~RenderApi() {
        commandBufferMap.clear();
        // wait until thread gracefully shutdown
        running = false;
        thread->join();
    }

    void RenderApi::run() {
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

    void RenderApi::renderFrame(const Frame &frame) {
        const auto& predrawCommands = frame.predrawCommands;
        const auto& postdrawCommands = frame.postdrawCommands;
        auto& fence = fences[currentFrame];
        auto& imageSemaphore = imageSemaphores[currentFrame];
        auto& renderFinishedSemaphore = renderFinishedSemaphores[currentFrame];
        auto& commandBuffer = commandBuffers[currentFrame];

        // wait for frame
        fence->wait();
        if (!context->surface->getImage(*imageSemaphore)) {
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
            command();
        }

        // run rendering
        render(commandBuffer, currentFrame);

        // execute post draw commands
        commandBuffer->addToSubmit({
            .secondary = const_cast<CommandBufferHandle*>(frame.postdrawBuffers.data()),
            .secondaryCount = static_cast<u32>(frame.postdrawBuffers.size())
        });

        for (const auto& command : postdrawCommands) {
            command();
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
            .surface = context->surface.Get(),
            .waitSemaphore = renderFinishedSemaphore
        });

        if (!presented || context->surface->needsResize) {
            context->surface->recreateSwapChain();
        }

        // next frame
        currentFrame = (currentFrame + 1) % frame_queue.getSize();
    }

    void RenderApi::resize(int w, int h) {
        context->surface->resize(w, h);
    }

    void RenderApi::render(Scope<CommandBuffer> &commandBuffer, u32 frame) {
        u32 width = context->surface->render_target->info.width;
        u32 height = context->surface->render_target->info.height;

        commandBuffer->beginRenderPass({
            .renderTarget = context->surface->render_target,
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

    void RenderApi::uploadMesh(const Mesh &mesh, bool &ready) {
        ready = false;
        threadPool->submit({
            .task = [&, this, mesh]() {
                Buffer stagingVertexBuffer(*context->device, BufferCreateInfo {
                    .memoryType = MEMORY_TYPE_HOST,
                    .usages = BUFFER_USAGE_TRANSFER_SRC,
                    .size = sizeof(Vertex) * mesh.vertices.size(),
                });
                void* stageVertexMemory = stagingVertexBuffer.map();
                memcpy(stageVertexMemory, mesh.vertices.data(), sizeof(Vertex) * mesh.vertices.size());
                stagingVertexBuffer.unmap();

                Buffer stagingIndexBuffer(*context->device, BufferCreateInfo {
                    .memoryType = MEMORY_TYPE_HOST,
                    .usages = BUFFER_USAGE_TRANSFER_SRC,
                    .size = sizeof(u32) * mesh.indices.size(),
                });
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

                currentFrameState.predrawCommands.emplace_back([&]{ ready = true; });
                currentFrameState.predrawBuffers.emplace_back(commandBuffer);
                frame_queue.push(currentFrameState);
            }
        });
    }

    CommandBuffer& RenderApi::getCommandBuffer(std::thread::id threadId) {
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

    void RenderApi::beginFrame() {
        currentFrameState = {};
    }

    void RenderApi::endFrame() {
        frame_queue.push(currentFrameState);
    }

}
