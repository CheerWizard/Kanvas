//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_VERTEX_HPP
#define STC_VERTEX_HPP

namespace stc {

    enum class AttributeType {
        PRIMITIVE = 1,
        VEC2 = 2, VEC3 = 3, VEC4 = 4,
        MAT2 = 8, MAT3 = 12, MAT4 = 16,
    };

    struct VertexAttribute {
        uint32_t location = 0;
        AttributeType type = AttributeType::PRIMITIVE;
    };

    struct Vertex {
        vec3<float> position = { 0, 0, 0 };
        vec2<float> uv = { 0, 0 };
        vec3<float> normal = { 0, 0, 0 };

        inline static std::vector<VertexAttribute> attributes = {
            { 0, AttributeType::VEC3 },
            { 1, AttributeType::VEC2 },
            { 2, AttributeType::VEC3 },
        };
    };

}

#endif //STC_VERTEX_HPP