package com.cws.kanvas.rendering.frontend

import com.cws.kanvas.math.Vec2
import com.cws.kanvas.math.Vec3
import com.cws.kanvas.rendering.backend.Attribute
import com.cws.kanvas.rendering.backend.AttributeFormat
import com.cws.kanvas.rendering.backend.AttributeType
import com.cws.std.memory.NativeData

val VertexAttributes = arrayOf(
    Attribute(
        location = 0u,
        type = AttributeType.ATTRIBUTE_TYPE_VEC3,
        format = AttributeFormat.FLOAT3,
    ),
    Attribute(
        location = 1u,
        type = AttributeType.ATTRIBUTE_TYPE_VEC2,
        format = AttributeFormat.FLOAT2,
    ),
    Attribute(
        location = 2u,
        type = AttributeType.ATTRIBUTE_TYPE_VEC3,
        format = AttributeFormat.FLOAT3,
    ),
)

@NativeData
data class _Vertex(
    val position: Vec3,
    val uv: Vec2,
    val normal: Vec3,
)