package com.cws.kanvas.wgpu.gpu

external interface GPUCopyExternalImageDestInfo : GPUTexelCopyTextureInfo {
    var colorSpace: PredefinedColorSpace?
    var premultipliedAlpha: Boolean?
}

fun GPUCopyExternalImageDestInfo(
    texture: GPUTexture,
    mipLevel: Int = 0,
    origin: GPUOrigin3D? = null,
    aspect: GPUTextureAspect = GPUTextureAspect.all,
    colorSpace: PredefinedColorSpace? = null,
    premultipliedAlpha: Boolean? = null
): GPUCopyExternalImageDestInfo =
    jsObject {
        this.texture = texture
        this.mipLevel = mipLevel
        this.origin = origin
        this.aspect = aspect
        this.colorSpace = colorSpace
        this.premultipliedAlpha = premultipliedAlpha
    }
