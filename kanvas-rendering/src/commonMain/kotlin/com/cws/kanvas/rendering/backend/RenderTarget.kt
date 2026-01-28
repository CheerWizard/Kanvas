package com.cws.kanvas.rendering.backend

import com.cws.kanvas.math.Vec4

data class ColorAttachment(
    val format: TextureFormat,
    val samples: Int = 1,
    val clearColor: Vec4 = Vec4(0f, 0f, 0f, 1f),
    val enableBlending: Boolean = false,
    val blend: Blend = Blend(),
)

data class DepthAttachment(
    val enabled: Boolean = false,
    val format: TextureFormat = TextureFormat.FORMAT_DEPTH24,
    val samples: Int = 1,
    val depthClearValue: Float = 1.0f,
    val depthCompareOp: CompareOp = CompareOp.LESS,
    val stencilClearValue: Int = 1,
    val depthReadOnly: Boolean = false,
    val depthWriteEnabled: Boolean = false,
    val stencilReadOnly: Boolean = false
)

data class RenderTargetInfo(
    val name: String,
    val x: Int = 0,
    val y: Int = 0,
    val width: Int,
    val height: Int,
    val depth: Int = 1,
    val colorAttachments: List<ColorAttachment>,
    val depthAttachment: DepthAttachment = DepthAttachment(),
)

expect class RenderTargetHandle

expect class RenderTarget(renderContext: RenderContext, config: RenderTargetInfo) : Resource<RenderTargetHandle> {
    constructor(renderContext: RenderContext, handle: RenderTargetHandle)
    override fun onCreate()
    override fun onDestroy()
}
