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
    renderThread.New(render_config);
}

stc::RenderBridge::~RenderBridge() {
    renderThread->renderer.context->device->wait();
}

void stc::RenderBridge::resize(int width, int height) {
    renderThread->renderer.resize(width, height);
}

void stc::RenderBridge::render(const Mesh &mesh) {
    // renderThread->renderer.uploadMesh(mesh);
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