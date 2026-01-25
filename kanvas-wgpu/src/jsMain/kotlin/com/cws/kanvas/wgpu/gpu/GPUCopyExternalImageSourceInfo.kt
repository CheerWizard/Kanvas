package com.cws.kanvas.wgpu.gpu

external interface GPUCopyExternalImageSourceInfo {
    var source: GPUCopyExternalImageSource
    var flipY: Boolean?
    var origin: GPUOrigin2D?
}

fun GPUCopyExternalImageSourceInfo(
    source: GPUCopyExternalImageSource,
    flipY: Boolean? = null,
    origin: GPUOrigin2D? = null
): GPUCopyExternalImageSourceInfo =
    jsObject {
        this.source = source
        this.flipY = flipY
        this.origin = origin
    }
