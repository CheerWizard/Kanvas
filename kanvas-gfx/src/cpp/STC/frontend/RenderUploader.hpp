//
// Created by cheerwizard on 15.07.25.
//

#ifndef RENDER_UPLOADER_HPP
#define RENDER_UPLOADER_HPP

#include "Frame.hpp"
#include "Renderer.hpp"

namespace stc {

    struct RenderUploader {

        RenderUploader(Renderer& renderer);
        ~RenderUploader();

        void uploadMesh(const Mesh& mesh, bool& ready, const std::function<void(const Command&)>& onDone);

    private:
        CommandBuffer& getCommandBuffer(std::thread::id threadId);

        Renderer& renderer;
        Scope<ThreadPool> threadPool;
        std::unordered_map<std::thread::id, Scope<CommandBuffer>> commandBuffers;
    };

}

#endif //RENDER_UPLOADER_HPP
