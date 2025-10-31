//
// Created by cheerwizard on 04.07.25.
//

#ifndef RENDER_BRIDGE_HPP
#define RENDER_BRIDGE_HPP

#include "RenderConfig.hpp"

#include "../frontend/RenderThread.hpp"
#include "../frontend/RenderUploader.hpp"

namespace stc {

    struct RenderBridge {

        RenderBridge(const RenderConfig& render_config);
        ~RenderBridge();

        void beginFrame();
        void endFrame();

        void resize(int width, int height);

    private:
        u32 currentFrame = 0;
        Frame currentFrameState = {};
        Scope<FrameQueue> frameQueue;
        Scope<Renderer> renderer;
        Scope<RenderThread> renderThread;
        Scope<RenderUploader> renderUploader;
    };

}

#endif //RENDER_BRIDGE_HPP
