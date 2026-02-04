package com.cws.kanvas.rendering.backend

import kotlin.enums.enumEntries

interface IntEnum {
    val value: Int

    companion object {
        inline fun <reified T> from(value: Int): T where T : Enum<T>, T : IntEnum {
            return enumEntries<T>().firstOrNull { it.value == value }
                ?: error("Unknown ${T::class.simpleName} value: $value")
        }
    }
}

interface StringEnum {
    val string: String

    companion object {
        inline fun <reified T> from(string: String): T where T : Enum<T>, T : StringEnum {
            return enumEntries<T>().firstOrNull { it.string == string }
                ?: error("Unknown ${T::class.simpleName} string: $string")
        }
    }
}

expect enum class AttributeFormat : IntEnum {
    FLOAT,
    FLOAT2,
    FLOAT3,
    FLOAT4;

    override val value: Int
}

enum class AttributeType(override val value: Int): IntEnum {
    ATTRIBUTE_TYPE_PRIMITIVE(1),
    ATTRIBUTE_TYPE_VEC2(2),
    ATTRIBUTE_TYPE_VEC3(3),
    ATTRIBUTE_TYPE_VEC4(4),
    ATTRIBUTE_TYPE_MAT2(8),
    ATTRIBUTE_TYPE_MAT3(12),
    ATTRIBUTE_TYPE_MAT4(16);
}

expect enum class BindingType : IntEnum {
    UNIFORM_BUFFER,
    STORAGE_BUFFER,
    TEXTURE,
    SAMPLER;

    override val value: Int
}

expect enum class BlendOp : IntEnum {
    ADD,
    SUBTRACT,
    REVERSE_SUBTRACT,
    MIN,
    MAX;

    override val value: Int
}

expect enum class BlendFactor : IntEnum {
    ZERO,
    ONE,
    SRC_COLOR,
    ONE_MINUS_SRC_COLOR,
    DST_COLOR,
    ONE_MINUS_DST_COLOR,
    SRC_ALPHA,
    ONE_MINUS_SRC_ALPHA,
    DST_ALPHA,
    ONE_MINUS_DST_ALPHA;

    override val value: Int
}

expect enum class BufferUsage : IntEnum {
    COPY_SRC,
    COPY_DST,
    UNIFORM_TEXEL,
    STORAGE_TEXEL,
    UNIFORM_BUFFER,
    STORAGE_BUFFER,
    INDEX_BUFFER,
    VERTEX_BUFFER,
    INDIRECT_BUFFER;

    override val value: Int
}

expect enum class TextureUsage : IntEnum {
    COPY_SRC,
    COPY_DST,
    TEXTURE_BINDING,
    STORAGE_BINDING,
    RENDER_ATTACHMENT;

    override val value: Int
}

expect enum class MemoryType : IntEnum {
    HOST,
    DEVICE_LOCAL;

    override val value: Int
}

expect enum class PrimitiveTopology : IntEnum {
    POINT_LIST,
    LINE_LIST,
    LINE_STRIP,
    TRIANGLE_LIST,
    TRIANGLE_STRIP,
    TRIANGLE_FAN,
    LINE_LIST_WITH_ADJACENCY,
    LINE_STRIP_WITH_ADJACENCY,
    TRIANGLE_LIST_WITH_ADJACENCY,
    TRIANGLE_STRIP_WITH_ADJACENCY,
    PATCH_LIST,
    MAX_ENUM;

    override val value: Int
}

expect enum class PolygonMode : IntEnum {
    FILL,
    LINE,
    POINT;

    override val value: Int
}

expect enum class CullMode : IntEnum {
    NONE,
    FRONT,
    BACK,
    FRONT_AND_BACK;

    override val value: Int
}

expect enum class FrontFace : IntEnum {
    COUNTER_CLOCKWISE,
    CLOCKWISE;

    override val value: Int
}

expect enum class ShaderStage : IntEnum {
    VERTEX,
    FRAGMENT,
    COMPUTE,
    MESH,
    RAY_TRACING;

    override val value: Int
}

expect enum class TextureType : IntEnum {
    TEXTURE2D,
    TEXTURE3D,
    TEXTURE_CUBE,
    TEXTURE_ARRAY;

    override val value: Int
}

expect enum class TextureFormat : IntEnum {
    FORMAT_R8,
    FORMAT_RG8,
    FORMAT_RGB8,
    FORMAT_RGBA8,

    FORMAT_R16,
    FORMAT_RG16,
    FORMAT_RGB16,
    FORMAT_RGBA16,

    FORMAT_R32,
    FORMAT_RG32,
    FORMAT_RGB32,
    FORMAT_RGBA32,

    FORMAT_DEPTH16,
    FORMAT_DEPTH24,
    FORMAT_DEPTH32;

    override val value: Int
}

expect enum class SamplerFilter : IntEnum {
    LINEAR,
    NEAREST;

    override val value: Int
}

expect enum class SamplerMode : IntEnum {
    REPEAT,
    MIRRORED_REPEAT,
    CLAMP_TO_EDGE,
    CLAMP_TO_BORDER;

    override val value: Int
}

expect enum class SamplerMipMapMode : IntEnum {
    LINEAR,
    NEAREST;

    override val value: Int
}

expect enum class CompareOp : IntEnum {
    ALWAYS,
    NEVER,
    LESS,
    LESS_EQUAL,
    GREATER,
    GREATER_EQUAL,
    EQUAL,
    NOT_EQUAL;

    override val value: Int
}

expect enum class BorderColor : IntEnum {
    FLOAT_TRANSPARENT_BLACK,
    INT_TRANSPARENT_BLACK,
    FLOAT_OPAQUE_BLACK,
    INT_OPAQUE_BLACK,
    FLOAT_OPAQUE_WHITE,
    INT_OPAQUE_WHITE;

    override val value: Int
}