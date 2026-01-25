package com.cws.kanvas.wgpu.gpu

external interface GPUVertexAttribute {
    var format: GPUVertexFormat
    var offset: GPUSize64
    var shaderLocation: GPUIndex32
}

fun GPUVertexAttribute(
    format: GPUVertexFormat,
    offset: GPUSize64,
    shaderLocation: GPUIndex32
): GPUVertexAttribute = jsObject {
    this.format = format
    this.offset = offset
    this.shaderLocation = shaderLocation
}
