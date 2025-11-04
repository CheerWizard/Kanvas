//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_VERTEX_HPP
#define STC_VERTEX_HPP

#include "backend/Attribute.hpp"

namespace stc {

    struct Vertex {
        vec3<float> position = { 0, 0, 0 };
        vec2<float> uv = { 0, 0 };
        vec3<float> normal = { 0, 0, 0 };

        inline static std::vector<Attribute> attributes = {
            { .location = 0, .type = ATTRIBUTE_TYPE_VEC3, .format = ATTRIBUTE_FORMAT_FLOAT3 },
            { .location = 1, .type = ATTRIBUTE_TYPE_VEC2, .format = ATTRIBUTE_FORMAT_FLOAT2 },
            { .location = 2, .type = ATTRIBUTE_TYPE_VEC3, .format = ATTRIBUTE_FORMAT_FLOAT3 },
        };
    };

}

#endif //STC_VERTEX_HPP