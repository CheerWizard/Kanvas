//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_CAMERABUFFER_HPP
#define STC_CAMERABUFFER_HPP

#include "ItemBuffer.hpp"

namespace stc {

    struct CameraData {
        float4 position;
        float4x4 projection;
        float4x4 view;
    };

    static constexpr Binding CAMERA_BINDING = {
        .type = BINDING_TYPE_UNIFORM_BUFFER,
        .slot = 0,
        .shader_stages = SHADER_STAGE_VERTEX | SHADER_STAGE_FRAGMENT,
    };

    struct CameraBuffer : ItemBuffer<CameraData, CAMERA_BINDING> {};

}

#endif //STC_CAMERABUFFER_HPP