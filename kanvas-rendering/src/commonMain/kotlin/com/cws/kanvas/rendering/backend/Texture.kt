package com.cws.kanvas.rendering.backend

expect enum class TextureType {
    TEXTURE2D,
    TEXTURE3D,
    TEXTURE_CUBE,
    TEXTURE_ARRAY,
}

expect enum class TextureFormat {
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
    FORMAT_DEPTH32,

    FORMAT_STENCIL,
}

expect enum class SamplerFilter {
    LINEAR,
}

expect enum class SamplerMode {
    REPEAT,
}

expect enum class SamplerMipMapMode {
    LINEAR,
}

expect enum class CompareOp {
    NONE,
    ALWAYS,
    NEVER,
    LESS,
    LESS_EQUAL,
    GREATER,
    GREATER_EQUAL,
    EQUAL,
    NOT_EQUAL,
}

data class SamplerInfo(
    val magFilter: SamplerFilter = SamplerFilter.LINEAR,
    val minFilter: SamplerFilter = SamplerFilter.LINEAR,

    val addressModeU: SamplerMode = SamplerMode.REPEAT,
    val addressModeV: SamplerMode = SamplerMode.REPEAT,
    val addressModeW: SamplerMode = SamplerMode.REPEAT,

    val enableAnisotropy: Boolean = true,
    val maxAnisotropy: Float = 1.0f,

    val unnormalizedCoordinates: Boolean = false,

    val enableCompare: Boolean = false,
    val compareOp: CompareOp = CompareOp.ALWAYS,

    val mipmapMode: SamplerMipMapMode = SamplerMipMapMode.LINEAR,
    val mipLodBias: Float = 0.0f,
    val minLod: Float = 0.0f,
    val maxLod: Float = 0.0f
)

expect class SamplerHandle

expect class Sampler(context: RenderContext, info: SamplerInfo) : Resource<SamplerHandle, SamplerInfo> {
    override fun onCreate()
    override fun onDestroy()
}

data class TextureInfo(
    val type: TextureType = TextureType.TEXTURE2D,

    val width: Int = 0,
    val height: Int = 0,
    val depth: Int = 1,

    val format: TextureFormat = TextureFormat.FORMAT_RGBA8,

    val mips: Int = 1,
    val baseMip: Int = 1
)

expect class TextureHandle

expect class Texture(context: RenderContext, info: TextureInfo) : Resource<TextureHandle, TextureInfo> {
    override fun onCreate()
    override fun onDestroy()
}

expect class TextureViewHandle

expect class TextureView(context: RenderContext) : Resource<TextureViewHandle, Unit> {
    override fun onCreate()
    override fun onDestroy()
}
