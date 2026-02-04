package com.cws.kanvas.rendering.backend

/* ================= AttributeFormat ================= */

actual enum class AttributeFormat(
    actual override val value: Int
) : IntEnum {
    FLOAT(1),
    FLOAT2(2),
    FLOAT3(3),
    FLOAT4(4);
}

/* ================= BindingType ================= */

actual enum class BindingType(
    actual override val value: Int
) : IntEnum {
    UNIFORM_BUFFER(6),
    STORAGE_BUFFER(7),
    TEXTURE(1),
    SAMPLER(0);
}

/* ================= BlendOp ================= */

actual enum class BlendOp(
    actual override val value: Int
) : IntEnum {
    ADD(0),
    SUBTRACT(1),
    REVERSE_SUBTRACT(2),
    MIN(3),
    MAX(4);
}

/* ================= BlendFactor ================= */

actual enum class BlendFactor(
    actual override val value: Int
) : IntEnum {
    ZERO(0),
    ONE(1),
    SRC_COLOR(2),
    ONE_MINUS_SRC_COLOR(3),
    DST_COLOR(4),
    ONE_MINUS_DST_COLOR(5),
    SRC_ALPHA(6),
    ONE_MINUS_SRC_ALPHA(7),
    DST_ALPHA(8),
    ONE_MINUS_DST_ALPHA(9);
}

/* ================= BufferUsage ================= */

actual enum class BufferUsage(
    actual override val value: Int
) : IntEnum {
    COPY_SRC(0x00000001),
    COPY_DST(0x00000002),
    UNIFORM_TEXEL(0x00000004),
    STORAGE_TEXEL(0x00000008),
    UNIFORM_BUFFER(0x00000010),
    STORAGE_BUFFER(0x00000020),
    INDEX_BUFFER(0x00000040),
    VERTEX_BUFFER(0x00000080),
    INDIRECT_BUFFER(0x00000100);
}

/* ================= TextureUsage ================= */

actual enum class TextureUsage(
    actual override val value: Int
) : IntEnum {
    COPY_SRC(0x00000001),
    COPY_DST(0x00000002),
    TEXTURE_BINDING(0x00000004),
    STORAGE_BINDING(0x00000008),
    RENDER_ATTACHMENT(0x00000010),
    DEPTH_STENCIL_ATTACHMENT(0x00000020);
}

/* ================= MemoryType ================= */

actual enum class MemoryType(
    actual override val value: Int
) : IntEnum {
    DEVICE_LOCAL(0x00000001),
    HOST(0x00000002);
}

/* ================= PrimitiveTopology ================= */

actual enum class PrimitiveTopology(
    actual override val value: Int
) : IntEnum {
    POINT_LIST(0),
    LINE_LIST(1),
    LINE_STRIP(2),
    TRIANGLE_LIST(3),
    TRIANGLE_STRIP(4),
    TRIANGLE_FAN(5),
    LINE_LIST_WITH_ADJACENCY(6),
    LINE_STRIP_WITH_ADJACENCY(7),
    TRIANGLE_LIST_WITH_ADJACENCY(8),
    TRIANGLE_STRIP_WITH_ADJACENCY(9),
    PATCH_LIST(10),
    MAX_ENUM(0x7fffffff.toInt());
}

/* ================= PolygonMode ================= */

actual enum class PolygonMode(
    actual override val value: Int
) : IntEnum {
    FILL(0),
    LINE(1),
    POINT(2);
}

/* ================= CullModeFlagBits ================= */

actual enum class CullMode(
    actual override val value: Int
) : IntEnum {
    NONE(0),
    FRONT(0x1),
    BACK(0x2),
    FRONT_AND_BACK(0x3);
}

/* ================= FrontFace ================= */

actual enum class FrontFace(
    actual override val value: Int
) : IntEnum {
    COUNTER_CLOCKWISE(0),
    CLOCKWISE(1);
}

/* ================= ShaderStage ================= */

actual enum class ShaderStage(
    actual override val value: Int
) : IntEnum {
    VERTEX(0x00000001),
    FRAGMENT(0x00000010),
    COMPUTE(0x00000020),
    MESH(0x00000040),
    RAY_TRACING(0x00000100);
}

/* ================= TextureType ================= */

actual enum class TextureType(
    actual override val value: Int
) : IntEnum {
    TEXTURE2D(1),
    TEXTURE3D(2),
    TEXTURE_CUBE(3),
    TEXTURE_ARRAY(4);
}

/* ================= TextureFormat ================= */

actual enum class TextureFormat(
    actual override val value: Int
) : IntEnum {
    FORMAT_R8(9),
    FORMAT_RG8(16),
    FORMAT_RGB8(23),
    FORMAT_RGBA8(37),

    FORMAT_R16(70),
    FORMAT_RG16(77),
    FORMAT_RGB16(84),
    FORMAT_RGBA16(97),

    FORMAT_R32(100),
    FORMAT_RG32(103),
    FORMAT_RGB32(106),
    FORMAT_RGBA32(109),

    FORMAT_DEPTH16(124),
    FORMAT_DEPTH24(129),
    FORMAT_DEPTH32(126);
}

/* ================= SamplerFilter ================= */

actual enum class SamplerFilter(
    actual override val value: Int
) : IntEnum {
    NEAREST(0),
    LINEAR(1);
}

/* ================= SamplerMode ================= */

actual enum class SamplerMode(
    actual override val value: Int
) : IntEnum {
    REPEAT(0),
    MIRRORED_REPEAT(1),
    CLAMP_TO_EDGE(2),
    CLAMP_TO_BORDER(3);
}

/* ================= SamplerMipMapMode ================= */

actual enum class SamplerMipMapMode(
    actual override val value: Int
) : IntEnum {
    NEAREST(0),
    LINEAR(1);
}

/* ================= CompareOp ================= */

actual enum class CompareOp(
    actual override val value: Int
) : IntEnum {
    NEVER(0),
    LESS(1),
    EQUAL(2),
    LESS_EQUAL(3),
    GREATER(4),
    NOT_EQUAL(5),
    GREATER_EQUAL(6),
    ALWAYS(7);
}

/* ================= BorderColor ================= */

actual enum class BorderColor(
    actual override val value: Int
) : IntEnum {
    FLOAT_TRANSPARENT_BLACK(0),
    INT_TRANSPARENT_BLACK(1),
    FLOAT_OPAQUE_BLACK(2),
    INT_OPAQUE_BLACK(3),
    FLOAT_OPAQUE_WHITE(4),
    INT_OPAQUE_WHITE(5);
}
