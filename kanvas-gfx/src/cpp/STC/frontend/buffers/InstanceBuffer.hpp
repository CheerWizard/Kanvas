//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_INSTANCEBUFFER_HPP
#define STC_INSTANCEBUFFER_HPP

#include "StorageBuffer.hpp"

namespace stc {

    struct InstanceData {
        float4x4 model;
        float4x4 normal;
    };

    static constexpr Binding INSTANCE_BINDING = {
        .type = BINDING_TYPE_STORAGE_BUFFER,
        .slot = 1,
        .shader_stages = SHADER_STAGE_VERTEX,
    };

    struct InstanceBuffer : StorageBuffer {
        InstanceBuffer(const Device& device) : StorageBuffer(device, StorageBufferCreateInfo {
            .binding = INSTANCE_BINDING,
            .itemSize = sizeof(InstanceData),
            .itemCount = 100,
        }) {}
    };

}

#endif //STC_INSTANCEBUFFER_HPP