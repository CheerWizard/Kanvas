//
// Created by cheerwizard on 04.07.25.
//

#ifndef RENDER_BRIDGE_HPP
#define RENDER_BRIDGE_HPP

using namespace stc;

// Render API exposed to Kotlin Native and Kotlin WASM
extern "C" {

    void RenderApiBridge_init(
        u8* buffer,

    );

    void RenderApiBridge_release();

    void RenderApiBridge_resize(int width, int height);

    u32 RenderApiBridge_createMesh(
        float* vertices,
        u32 vertices_size,
        u32* indices,
        u32 indices_size
    );

    void RenderAPIBridge_loadShaderText(ShaderSourceType type, const std::string& name, const std::string& textSource);

    void RenderAPIBridge_loadShaderSPIRV(
        u8* buffer,
        u32 name_offset,
        u32 name_size,
        u32 spirv_offset,
        u32 spirv_size
    );

}

#endif //RENDER_BRIDGE_HPP