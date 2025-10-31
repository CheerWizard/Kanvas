//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_INSTANCEBUFFER_HPP
#define STC_INSTANCEBUFFER_HPP

#include "ListBuffer.hpp"

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

    struct InstanceBuffer : ListBuffer<InstanceData, 100, INSTANCE_BINDING> {};

}

#endif //STC_INSTANCEBUFFER_HPP