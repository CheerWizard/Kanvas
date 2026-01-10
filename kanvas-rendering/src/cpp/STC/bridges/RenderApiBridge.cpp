//
// Created by cheerwizard on 04.07.25.
//

#include "RenderApiBridge.hpp"

using namespace stc;

static inline RenderApi* api = nullptr;

void RenderApi_init(u8* createInfo, usize createInfoSize) {
    api = new RenderApi();
}

void RenderApi_release() {
    delete api;
}

void RenderApi_resize(int width, int height) {
    api->resize(width, height);
}

void RenderApi_beginFrame() {
    api->beginFrame();
}

void RenderApi_endFrame() {
    api->endFrame();
}

u32 RenderApi_createShader(u8 *createInfo, usize createInfoSize) {
}

u32 RenderApi_createTexture(u8 *createInfo, usize createInfoSize) {
}

u32 RenderApi_createSampler(u8 *createInfo, usize createInfoSize) {
}

u32 RenderApi_createBuffer(u8 *createInfo, usize createInfoSize) {
}

u32 RenderApi_createUniformBuffer(u8 *createInfo, usize createInfoSize) {
}

u32 RenderApi_createStorageBuffer(u8 *createInfo, usize createInfoSize) {
}

u32 RenderApi_createRenderTarget(u8 *createInfo, usize createInfoSize) {
}

u32 RenderApi_createPipeline(u8 *createInfo, usize createInfoSize) {
}

void RenderApi_destroyShader(u32 id) {
}

void RenderApi_destroyTexture(u32 id) {
}

void RenderApi_destroySampler(u32 id) {
}

void RenderApi_destroyBuffer(u32 id) {
}

void RenderApi_destroyUniformBuffer(u32 id) {
}

void RenderApi_destroyStorageBuffer(u32 id) {
}

void RenderApi_destroyRenderTarget(u32 id) {
}

void RenderApi_destroyPipeline(u32 id) {
}

// void stc::Render::update() {
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