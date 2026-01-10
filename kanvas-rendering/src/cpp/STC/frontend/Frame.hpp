//
// Created by cheerwizard on 12.07.25.
//

#ifndef FRAME_HPP
#define FRAME_HPP

#include "backend/CommandBuffer.hpp"

#include "buffers/MeshBuffer.hpp"
#include "buffers/CameraBuffer.hpp"
#include "buffers/InstanceBuffer.hpp"
#include "buffers/MaterialBuffer.hpp"

#include "core/thread_pool.hpp"

#include <functional>

namespace stc {

    using Command = std::function<void()>;

    struct Model3D {
        bool visible = true;
        InstanceData instance;
        MeshRegion meshRegion;
        MaterialData material;
    };

    struct Frame {
        std::vector<Command> predrawCommands;
        std::vector<CommandBufferHandle> predrawBuffers;

        std::vector<Command> postdrawCommands;
        std::vector<CommandBufferHandle> postdrawBuffers;

        CameraData camera;
        std::vector<Model3D> models;
    };

    using FrameQueue = ConcurrentQueue<Frame, MAX_FRAMES>;

}

#endif //FRAME_HPP