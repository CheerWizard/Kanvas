package com.cws.kanvas.wgpu.gpu

external interface GPUCanvasConfiguration {
    var device: GPUDevice
    var format: GPUTextureFormat
    var usage: Int? // optional, defaults to GPUTextureUsage.RENDER_ATTACHMENT
    var alphaMode: String? // "opaque" or "premultiplied"
}

fun GPUCanvasConfiguration(
    device: GPUDevice,
    format: GPUTextureFormat,
    usage: Int? = null,
    alphaMode: String? = null
): GPUCanvasConfiguration = jsObject {
    this.device = device
    this.format = format
    this.usage = usage
    this.alphaMode = alphaMode
}
