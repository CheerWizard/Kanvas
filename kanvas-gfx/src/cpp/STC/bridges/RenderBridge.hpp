//
// Created by cheerwizard on 04.07.25.
//

#ifndef RENDER_BRIDGE_HPP
#define RENDER_BRIDGE_HPP

#include "RenderConfig.hpp"

#include "../frontend/RenderThread.hpp"

namespace stc {

    struct RenderBridge {

        RenderBridge(const RenderConfig& render_config);
        ~RenderBridge();

        void resize(int width, int height);

        void render(const Mesh& mesh);

    private:
        Scope<RenderThread> renderThread;
    };

}

#endif //RENDER_BRIDGE_HPP
