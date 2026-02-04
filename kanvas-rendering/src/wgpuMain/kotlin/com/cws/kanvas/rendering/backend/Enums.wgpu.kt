package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.*

/* ===================== ATTRIBUTES ===================== */

actual enum class AttributeFormat(
    actual override val value: Int,
    val jsValue: GPUVertexFormat,
) : IntEnum {
    FLOAT(1, GPUVertexFormat.float32),
    FLOAT2(2, GPUVertexFormat.float32x2),
    FLOAT3(3, GPUVertexFormat.float32x3),
    FLOAT4(4, GPUVertexFormat.float32x4),
}

/* ===================== BINDINGS ===================== */

actual enum class BindingType(
    actual override val value: Int,
    val bufferType: GPUBufferBindingType?,
    val samplerType: GPUSamplerBindingType?,
) : IntEnum {
    UNIFORM_BUFFER(0, GPUBufferBindingType.uniform, null),
    STORAGE_BUFFER(1, GPUBufferBindingType.storage, null),
    TEXTURE(2, null, null),
    SAMPLER(3, null, GPUSamplerBindingType.filtering),
}

/* ===================== BLENDING ===================== */

actual enum class BlendOp(
    actual override val value: Int,
    val jsValue: GPUBlendOperation,
) : IntEnum {
    ADD(0, GPUBlendOperation.add),
    SUBTRACT(1, GPUBlendOperation.subtract),
    REVERSE_SUBTRACT(2, GPUBlendOperation.reverseSubtract),
    MIN(3, GPUBlendOperation.min),
    MAX(4, GPUBlendOperation.max),
}

actual enum class BlendFactor(
    actual override val value: Int,
    val jsValue: GPUBlendFactor,
) : IntEnum {
    ZERO(0, GPUBlendFactor.zero),
    ONE(1, GPUBlendFactor.one),
    SRC_COLOR(2, GPUBlendFactor.src),
    ONE_MINUS_SRC_COLOR(3, GPUBlendFactor.oneMinusSrc),
    DST_COLOR(4, GPUBlendFactor.dst),
    ONE_MINUS_DST_COLOR(5, GPUBlendFactor.oneMinusDst),
    SRC_ALPHA(6, GPUBlendFactor.srcAlpha),
    ONE_MINUS_SRC_ALPHA(7, GPUBlendFactor.oneMinusSrcAlpha),
    DST_ALPHA(8, GPUBlendFactor.dstAlpha),
    ONE_MINUS_DST_ALPHA(9, GPUBlendFactor.oneMinusDstAlpha),
}

/* ===================== BUFFERS ===================== */

actual enum class BufferUsage(
    actual override val value: Int,
    val jsValue: Int,
) : IntEnum {
    COPY_SRC(1 shl 0, GPUBufferUsage.COPY_SRC),
    COPY_DST(1 shl 1, GPUBufferUsage.COPY_DST),
    UNIFORM_TEXEL(1 shl 2, 0),
    STORAGE_TEXEL(1 shl 3, 0),
    UNIFORM_BUFFER(1 shl 4, GPUBufferUsage.UNIFORM),
    STORAGE_BUFFER(1 shl 5, GPUBufferUsage.STORAGE),
    INDEX_BUFFER(1 shl 6, GPUBufferUsage.INDEX),
    VERTEX_BUFFER(1 shl 7, GPUBufferUsage.VERTEX),
    INDIRECT_BUFFER(1 shl 8, GPUBufferUsage.INDIRECT),
}

actual enum class TextureUsage(
    actual override val value: Int,
    val jsValue: Int,
) : IntEnum {
    COPY_SRC(1 shl 0, GPUTextureUsage.COPY_SRC),
    COPY_DST(1 shl 1, GPUTextureUsage.COPY_DST),
    TEXTURE_BINDING(1 shl 2, GPUTextureUsage.TEXTURE_BINDING),
    STORAGE_BINDING(1 shl 3, GPUTextureUsage.STORAGE_BINDING),
    RENDER_ATTACHMENT(1 shl 4, GPUTextureUsage.RENDER_ATTACHMENT),
}

actual enum class MemoryType(
    actual override val value: Int
) : IntEnum {
    HOST(0),
    DEVICE_LOCAL(1),
}

/* ===================== PIPELINE ===================== */

actual enum class PrimitiveTopology(
    actual override val value: Int,
    val jsValue: GPUPrimitiveTopology?,
) : IntEnum {
    POINT_LIST(0, GPUPrimitiveTopology.pointList),
    LINE_LIST(1, GPUPrimitiveTopology.lineList),
    LINE_STRIP(2, GPUPrimitiveTopology.lineStrip),
    TRIANGLE_LIST(3, GPUPrimitiveTopology.triangleList),
    TRIANGLE_STRIP(4, GPUPrimitiveTopology.triangleStrip),
    TRIANGLE_FAN(5, null),

    LINE_LIST_WITH_ADJACENCY(6, null),
    LINE_STRIP_WITH_ADJACENCY(7, null),
    TRIANGLE_LIST_WITH_ADJACENCY(8, null),
    TRIANGLE_STRIP_WITH_ADJACENCY(9, null),
    PATCH_LIST(10, null),

    MAX_ENUM(11, null),
}

actual enum class PolygonMode(
    actual override val value: Int
) : IntEnum {
    FILL(0),
    LINE(1),
    POINT(2),
}

actual enum class CullMode(
    actual override val value: Int,
    val jsValue: GPUCullMode?,
) : IntEnum {
    NONE(0, GPUCullMode.none),
    FRONT(1, GPUCullMode.front),
    BACK(2, GPUCullMode.back),
    FRONT_AND_BACK(3, null), // not supported
}

actual enum class FrontFace(
    actual override val value: Int,
    val jsValue: GPUFrontFace,
) : IntEnum {
    COUNTER_CLOCKWISE(0, GPUFrontFace.ccw),
    CLOCKWISE(1, GPUFrontFace.cw),
}

actual enum class ShaderStage(
    actual override val value: Int,
    val jsValue: Int?,
) : IntEnum {
    VERTEX(1 shl 0, GPUShaderStage.VERTEX),
    FRAGMENT(1 shl 1, GPUShaderStage.FRAGMENT),
    COMPUTE(1 shl 2, GPUShaderStage.COMPUTE),
    MESH(1 shl 3, null), // not supported
    RAY_TRACING(1 shl 4, null), // not supported
}

/* ===================== TEXTURES ===================== */

actual enum class TextureType(
    actual override val value: Int,
    val textureDimension: GPUTextureDimension,
    val viewDimension: GPUTextureViewDimension,
) : IntEnum {
    TEXTURE2D(0, GPUTextureDimension._2d, GPUTextureViewDimension._2d),
    TEXTURE3D(0, GPUTextureDimension._3d, GPUTextureViewDimension._2d),
    TEXTURE_CUBE(0, GPUTextureDimension._2d, GPUTextureViewDimension.cube),
    TEXTURE_ARRAY(0, GPUTextureDimension._2d, GPUTextureViewDimension._2dArray),
}

actual enum class TextureFormat(
    actual override val value: Int,
    val jsValue: GPUTextureFormat,
) : IntEnum {
    FORMAT_R8(1, GPUTextureFormat.r8unorm),
    FORMAT_RG8(2, GPUTextureFormat.rg8unorm),
    FORMAT_RGB8(3, GPUTextureFormat.rgba8unorm), // not supported
    FORMAT_RGBA8(4, GPUTextureFormat.rgba8unorm),

    FORMAT_R16(5, GPUTextureFormat.r16float),
    FORMAT_RG16(6, GPUTextureFormat.rg16float),
    FORMAT_RGB16(7, GPUTextureFormat.rgba16float), // not supported
    FORMAT_RGBA16(8, GPUTextureFormat.rgba16float),

    FORMAT_R32(9, GPUTextureFormat.r32float),
    FORMAT_RG32(10, GPUTextureFormat.rg32float),
    FORMAT_RGB32(11, GPUTextureFormat.rgba32float), // not supported
    FORMAT_RGBA32(12, GPUTextureFormat.rgba32float),

    FORMAT_DEPTH16(13, GPUTextureFormat.depth16unorm),
    FORMAT_DEPTH24(14, GPUTextureFormat.depth24plus),
    FORMAT_DEPTH32(15, GPUTextureFormat.depth32float),
}

/* ===================== SAMPLERS ===================== */

actual enum class SamplerFilter(
    actual override val value: Int,
    val jsValue: GPUFilterMode,
) : IntEnum {
    LINEAR(0, GPUFilterMode.linear),
    NEAREST(1, GPUFilterMode.nearest),
}

actual enum class SamplerMode(
    actual override val value: Int,
    val jsValue: GPUAddressMode,
) : IntEnum {
    REPEAT(0, GPUAddressMode.repeat),
    MIRRORED_REPEAT(1, GPUAddressMode.mirrorRepeat),
    CLAMP_TO_EDGE(2, GPUAddressMode.clampToEdge),
    CLAMP_TO_BORDER(3, GPUAddressMode.clampToEdge),
}

actual enum class SamplerMipMapMode(
    actual override val value: Int,
    val jsValue: GPUMipmapFilterMode,
) : IntEnum {
    LINEAR(0, GPUMipmapFilterMode.linear),
    NEAREST(1, GPUMipmapFilterMode.nearest),
}

/* ===================== DEPTH / STENCIL ===================== */

actual enum class CompareOp(
    actual override val value: Int,
    val jsValue: GPUCompareFunction,
) : IntEnum {
    ALWAYS(0, GPUCompareFunction.always),
    NEVER(1, GPUCompareFunction.never),
    LESS(2, GPUCompareFunction.less),
    LESS_EQUAL(3, GPUCompareFunction.lessEqual),
    GREATER(4, GPUCompareFunction.greater),
    GREATER_EQUAL(5, GPUCompareFunction.greaterEqual),
    EQUAL(6, GPUCompareFunction.equal),
    NOT_EQUAL(7, GPUCompareFunction.notEqual),
}

actual enum class BorderColor(
    actual override val value: Int
) : IntEnum {
    FLOAT_TRANSPARENT_BLACK(0),
    INT_TRANSPARENT_BLACK(1),
    FLOAT_OPAQUE_BLACK(2),
    INT_OPAQUE_BLACK(3),
    FLOAT_OPAQUE_WHITE(4),
    INT_OPAQUE_WHITE(5),
}