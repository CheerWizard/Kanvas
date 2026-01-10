package com.cws.kanvas.rendering.backend

expect enum class AttributeFormat {
    FLOAT,
    FLOAT2,
    FLOAT3,
    FLOAT4,
}

enum class AttributeType(val value: Int) {
    ATTRIBUTE_TYPE_PRIMITIVE(1),
    ATTRIBUTE_TYPE_VEC2(2),
    ATTRIBUTE_TYPE_VEC3(3),
    ATTRIBUTE_TYPE_VEC4(4),
    ATTRIBUTE_TYPE_MAT2(8),
    ATTRIBUTE_TYPE_MAT3(12),
    ATTRIBUTE_TYPE_MAT4(16),
};

data class Attribute(
    val location: UInt = 0u,
    val type: AttributeType,
    val format: AttributeFormat,
)
