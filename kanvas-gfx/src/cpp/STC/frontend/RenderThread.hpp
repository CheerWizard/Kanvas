//
// Created by cheerwizard on 04.07.25.
//

#ifndef RENDER_THREAD_HPP
#define RENDER_THREAD_HPP

#include "Renderer.hpp"
#include "Frame.hpp"

namespace stc {

    struct RenderThread {
        Renderer renderer;
        FrameQueue frame_queue;
        u32 currentFrame = 0;

        RenderThread(const RenderConfig& render_config);
        ~RenderThread();

        void resize(int w, int h);

    private:
        void runLoop();
        void renderFrame(const Frame& frame);

        bool running = false;
        std::array<Scope<Semaphore>, MAX_FRAMES> imageSemaphores;
        std::array<Scope<Semaphore>, MAX_FRAMES> renderFinishedSemaphores;
        std::array<Scope<Fence>, MAX_FRAMES> fences;
        std::array<Scope<CommandBuffer>, MAX_FRAMES> commandBuffers;
    };

}

#endif //RENDER_THREAD_HPP
