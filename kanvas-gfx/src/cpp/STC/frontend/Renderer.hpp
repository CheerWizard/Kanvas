//
// Created by cheerwizard on 05.07.25.
//

#ifndef VULKAN_RESOURCE_HPP
#define VULKAN_RESOURCE_HPP

#include "backend/CommandBuffer.hpp"
#include "buffers/CameraBuffer.hpp"
#include "buffers/InstanceBuffer.hpp"
#include "buffers/MaterialBuffer.hpp"
#include "buffers/MeshBuffer.hpp"

#include "backend/Context.hpp"
#include "backend/Surface.hpp"

namespace stc {

    struct Renderer {
        Scope<Context> context;
        Scope<Surface> surface;
        Scope<MeshBuffer> meshBuffer;
        Scope<CameraBuffer> cameraBuffer;
        Scope<InstanceBuffer> instanceBuffer;
        Scope<MaterialBuffer> materialBuffer;
        Scope<IndirectIndexBuffer> indirectIndexBuffer;

        Renderer(const RenderConfig& render_config);

        void resize(int w, int h);
        void render(Scope<CommandBuffer>& command_buffer, u32 frame, u32 surfaceImageIndex);

    private:
        /* TODO: custom pipelines
        Scope<Pipeline> pipeline;
        ShaderModuleHandle testVertShader;
        ShaderModuleHandle testFragShader;
        */
    };

}

#endif //VULKAN_RESOURCE_HPP