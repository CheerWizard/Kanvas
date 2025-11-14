//
// Created by cheerwizard on 18.10.25.
//

#include "UniformBuffer.hpp"

namespace stc {

    UniformBuffer::UniformBuffer(const Device &device, const UniformBufferCreateInfo& create_info)
    : info(create_info), Buffer(device, BufferCreateInfo {
        MEMORY_TYPE_HOST,
        BUFFER_USAGE_UNIFORM_BUFFER,
        create_info.itemSize * MAX_FRAMES
    }) {}

    void UniformBuffer::update(u32 frame, void* item) {
        if (!mapped) map();
        memcpy(static_cast<char*>(mapped) + frame * info.itemSize, item, info.itemSize);
    }

}
