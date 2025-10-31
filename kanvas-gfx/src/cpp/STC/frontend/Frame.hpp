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

namespace stc {

    struct Command {
        std::function<void()> onDone;
    };

    struct Model3D {
        bool visible = true;
        InstanceData instance;
        MeshRegion meshRegion;
        MaterialData material;
    };

    struct Frame {
        std::vector<Command> predrawCommands;
        std::vector<CommandBufferBackend> predrawBuffers;

        std::vector<Command> postdrawCommands;
        std::vector<CommandBufferBackend> postdrawBuffers;

        CameraData camera;
        std::vector<Model3D> models;
    };

    using FrameQueue = ConcurrentQueue<Frame, MAX_FRAMES>;

}

#endif //FRAME_HPP