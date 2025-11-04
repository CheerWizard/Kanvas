//
// Created by cheerwizard on 07.07.25.
//

#include "RenderThread.hpp"

#include "backend/Sync.hpp"

stc::RenderThread::RenderThread(const RenderConfig& render_config) : renderer(render_config) {
    // testVertShader = context.createShader("shaders/spv/model_vert.glsl.spv", VK_SHADER_STAGE_VERTEX_BIT);
    // testVertShader = context.createShader("shaders/spv/model_frag.glsl.spv", VK_SHADER_STAGE_FRAGMENT_BIT);

    for (auto& fence : fences) {
        fence.New(*renderer.context->device, true);
    }

    for (auto& semaphore : imageSemaphores) {
        semaphore.New(*renderer.context->device);
    }

    for (auto& semaphore : renderFinishedSemaphores) {
        semaphore.New(*renderer.context->device);
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

stc::RenderThread::~RenderThread() {
    running = false;
}

void stc::RenderThread::resize(int w, int h) {
    renderer.resize(w, h);
}

void stc::RenderThread::runLoop() {
    running = true;
    auto& deviceQueue = renderer.context->device->getQueue(
        renderer.context->device->queue_family_indices.graphics
    );
    for (auto& commandBuffer : commandBuffers) {
        commandBuffer.New(*deviceQueue, true);
    }
    while (running) {
        Frame frame;
        frame_queue.pop(frame);
        renderFrame(frame);
    }
    renderer.context->device->wait();
}

void stc::RenderThread::renderFrame(const Frame &frame) {
    const auto& predrawCommands = frame.predrawCommands;
    const auto& postdrawCommands = frame.postdrawCommands;
    auto& fence = fences[currentFrame];
    auto& imageSemaphore = imageSemaphores[currentFrame];
    auto& renderFinishedSemaphore = renderFinishedSemaphores[currentFrame];
    auto& commandBuffer = commandBuffers[currentFrame];

    // wait for frame
    fence->wait();
    u32 surfaceImageIndex;
    bool imageGet = renderer.surface->getImage(*imageSemaphore);
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
        .secondaryCount = static_cast<u32>(frame.predrawBuffers.size())
    });

    for (const auto& command : predrawCommands) {
        command.onDone();
    }

    // run rendering
    renderer.render(commandBuffer, currentFrame, surfaceImageIndex);

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
        .swapchain = renderer.surface->swapchain,
        .imageIndex = surfaceImageIndex,
        .waitSemaphore = renderFinishedSemaphore
    });

    if (!presented || renderer.surface->needsResize) {
        renderer.surface->recreateSwapChain();
    }

    // next frame
    currentFrame = (currentFrame + 1) % frame_queue.getSize();
}