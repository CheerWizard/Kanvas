//
// Created by cheerwizard on 04.07.25.
//

#ifndef RENDER_BRIDGE_HPP
#define RENDER_BRIDGE_HPP

#include "../frontend/RenderApi.hpp"

using namespace stc;

// Render API exposed to Kotlin Native and Kotlin WASM
extern "C" {

    void RenderApi_init(u8* createInfo, usize createInfoSize);
    void RenderApi_release();

    void RenderApi_resize(int width, int height);

    void RenderApi_beginFrame();
    void RenderApi_endFrame();

    u32 RenderApi_createShader(u8* createInfo, usize createInfoSize);
    u32 RenderApi_createTexture(u8* createInfo, usize createInfoSize);
    u32 RenderApi_createSampler(u8* createInfo, usize createInfoSize);
    u32 RenderApi_createBuffer(u8* createInfo, usize createInfoSize);
    u32 RenderApi_createUniformBuffer(u8* createInfo, usize createInfoSize);
    u32 RenderApi_createStorageBuffer(u8* createInfo, usize createInfoSize);
    u32 RenderApi_createRenderTarget(u8* createInfo, usize createInfoSize);
    u32 RenderApi_createPipeline(u8* createInfo, usize createInfoSize);

    void RenderApi_destroyShader(u32 id);
    void RenderApi_destroyTexture(u32 id);
    void RenderApi_destroySampler(u32 id);
    void RenderApi_destroyBuffer(u32 id);
    void RenderApi_destroyUniformBuffer(u32 id);
    void RenderApi_destroyStorageBuffer(u32 id);
    void RenderApi_destroyRenderTarget(u32 id);
    void RenderApi_destroyPipeline(u32 id);

}

#endif //RENDER_BRIDGE_HPP