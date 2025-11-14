//
// Created by cheerwizard on 04.07.25.
//

#include "RenderApiBridge.hpp"

using namespace stc;

static inline Scope<RenderApi> api;

void RenderApiBridge_init(const RenderConfig &render_config) {
    api.New(render_config);
}

void RenderApiBridge_release() {
    api.Delete();
}

void RenderApiBridge_resize(int width, int height) {
    api->resize(width, height);
}

void RenderApiBridge_render(const Mesh &mesh) {
    bool uploaded = false;
    api->uploadMesh(mesh, uploaded);
    // TODO consider how to just render mesh
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