//
// Created by cheerwizard on 03.11.25.
//

#ifndef STC_ATTRIBUTETYPE_HPP
#define STC_ATTRIBUTETYPE_HPP

#include "Handle.hpp"

namespace stc {

#ifdef VK

    enum AttributeFormat {
        ATTRIBUTE_FORMAT_FLOAT = VK_FORMAT_R32_SFLOAT,
        ATTRIBUTE_FORMAT_FLOAT2 = VK_FORMAT_R32G32_SFLOAT,
        ATTRIBUTE_FORMAT_FLOAT3 = VK_FORMAT_R32G32B32_SFLOAT,
        ATTRIBUTE_FORMAT_FLOAT4 = VK_FORMAT_R32G32B32A32_SFLOAT,
    };

#elif METAL

#elif WEBGPU

    enum AttributeFormat {
        ATTRIBUTE_FORMAT_FLOAT = WGPUVertexFormat_Float32,
        ATTRIBUTE_FORMAT_FLOAT2 = WGPUVertexFormat_Float32x2,
        ATTRIBUTE_FORMAT_FLOAT3 = WGPUVertexFormat_Float32x3,
        ATTRIBUTE_FORMAT_FLOAT4 = WGPUVertexFormat_Float32x4,
    };

#endif

    enum AttributeType {
        ATTRIBUTE_TYPE_PRIMITIVE = 1,
        ATTRIBUTE_TYPE_VEC2 = 2,
        ATTRIBUTE_TYPE_VEC3 = 3,
        ATTRIBUTE_TYPE_VEC4 = 4,
        ATTRIBUTE_TYPE_MAT2 = 8,
        ATTRIBUTE_TYPE_MAT3 = 12,
        ATTRIBUTE_TYPE_MAT4 = 16,
    };

    struct Attribute {
        u32 location = 0;
        AttributeType type;
        AttributeFormat format;
    };

}

#endif //STC_ATTRIBUTETYPE_HPP