package com.cws.kanvas.wgpu.gpu

external interface GPUTexelCopyTextureInfo {
    var aspect: GPUTextureAspect?
    var mipLevel: GPUIntegerCoordinate?
    var origin: GPUOrigin3D?
    var texture: GPUTexture
}

fun GPUTexelCopyTextureInfo(
    texture: GPUTexture,
    mipLevel: GPUIntegerCoordinate? = null,
    origin: GPUOrigin3D? = null,
    aspect: GPUTextureAspect? = null
): GPUTexelCopyTextureInfo = jsObject {
    this.texture = texture
    this.mipLevel = mipLevel
    this.origin = origin
    this.aspect = aspect
}
