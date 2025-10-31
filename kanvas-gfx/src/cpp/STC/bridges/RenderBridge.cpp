//
// Created by cheerwizard on 04.07.25.
//

#include "RenderBridge.hpp"

stc::RenderBridge::RenderBridge(const RenderConfig& render_config) {
    // currentFrameState.models.emplace_back(Model3D());
    // auto& newModel = currentFrameState.models.back();
    // renderUploader->uploadMesh({}, newModel.visible, [this](const Command& command) {
    //             currentFrameState.predrawCommands.emplace_back(command);
    //         });
    frameQueue.New();
    renderer.New(render_config);
    renderThread.New(*renderer, *frameQueue);
    renderUploader.New(*renderer);
}

stc::RenderBridge::~RenderBridge() {
    renderer->context->device->wait();
}

void stc::RenderBridge::beginFrame() {
}

void stc::RenderBridge::endFrame() {
    frameQueue->push(currentFrameState);
    currentFrame = (currentFrame + 1) % frameQueue->getSize();
}

void stc::RenderBridge::resize(int width, int height) {
    renderer->resize(width, height);
}

// void stc::RenderBridge::update() {
//     // MemoryManager::getInstance().log();
//
//     // filter and fill models into rendering buffers
//     // for (u32 i = 0 ; i < currentFrameState.models.size() ; i++) {
//     //     const auto& model = currentFrameState.models[i];
//     //     if (model.visible) {
//     //         renderContext->instanceBuffer.update(currentFrame, i, model.instance);
//     //         renderContext->materialBuffer.update(currentFrame, i, model.material);
//     //         renderContext->indirectIndexBuffer.update(currentFrame, i, model.meshRegion.indirectData);
//     //     }
//     // }
// }