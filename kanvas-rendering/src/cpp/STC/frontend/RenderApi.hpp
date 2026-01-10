//
// Created by cheerwizard on 05.07.25.
//

#ifndef VULKAN_RESOURCE_HPP
#define VULKAN_RESOURCE_HPP

#include "Frame.hpp"
#include "generated/render_api.pb.h"
#include "ShaderManager.hpp"

#include "buffers/CameraBuffer.hpp"
#include "buffers/InstanceBuffer.hpp"
#include "buffers/MaterialBuffer.hpp"
#include "buffers/MeshBuffer.hpp"

#include "backend/Context.hpp"
#include "backend/Surface.hpp"
#include "backend/CommandBuffer.hpp"

namespace stc {
    class RenderApiCreateInfo;

    struct RenderApi {
        RenderApi(void* nativeWindow, const RenderApiCreateInfo& create_info);
        ~RenderApi();

        void beginFrame();
        void endFrame();

        void resize(int w, int h);

        void uploadMesh(const Mesh& mesh, bool& ready);

    private:
        void run();
        void renderFrame(const Frame& frame);
        CommandBuffer& getCommandBuffer(std::thread::id threadId);
        void render(Scope<CommandBuffer> &command_buffer, u32 frame);

        Scope<Context> context;
        Scope<ShaderManager> shaderManager;
        Scope<MeshBuffer> meshBuffer;
        Scope<CameraBuffer> cameraBuffer;
        Scope<InstanceBuffer> instanceBuffer;
        Scope<MaterialBuffer> materialBuffer;
        Scope<IndirectIndexBuffer> indirectIndexBuffer;

        Scope<ThreadPool> threadPool;
        std::unordered_map<std::thread::id, Scope<CommandBuffer>> commandBufferMap;

        bool running = false;
        std::array<Scope<Semaphore>, MAX_FRAMES> imageSemaphores;
        std::array<Scope<Semaphore>, MAX_FRAMES> renderFinishedSemaphores;
        std::array<Scope<Fence>, MAX_FRAMES> fences;
        std::array<Scope<CommandBuffer>, MAX_FRAMES> commandBuffers;

        Scope<std::thread> thread;
        FrameQueue frame_queue;
        u32 currentFrame = 0;
        Frame currentFrameState;

        /* TODO: custom pipelines
        Scope<Pipeline> pipeline;
        ShaderModuleHandle testVertShader;
        ShaderModuleHandle testFragShader;
        */
    };

}

#endif //VULKAN_RESOURCE_HPP