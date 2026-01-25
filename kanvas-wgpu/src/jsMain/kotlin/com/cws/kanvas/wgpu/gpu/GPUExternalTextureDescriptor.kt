package com.cws.kanvas.wgpu.gpu

import org.w3c.dom.CanvasImageSource

external interface GPUExternalTextureDescriptor :
    GPUObjectDescriptorBase {
    var colorSpace: PredefinedColorSpace?
    var source: CanvasImageSource /* HTMLVideoElement | VideoFrame */
}

fun GPUExternalTextureDescriptor(
    source: CanvasImageSource,
    colorSpace: PredefinedColorSpace? = null,
    label: String? = null
): GPUExternalTextureDescriptor = jsObject {
    this.source = source
    this.colorSpace = colorSpace
    this.label = label
}