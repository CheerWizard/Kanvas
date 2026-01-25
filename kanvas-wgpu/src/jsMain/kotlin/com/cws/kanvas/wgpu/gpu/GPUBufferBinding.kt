package com.cws.kanvas.wgpu.gpu

external interface GPUBufferBinding : GPUBindingResource {
    var buffer: GPUBuffer
    var offset: GPUSize64?
    var size: GPUSize64?
}

fun GPUBufferBinding(
    buffer: GPUBuffer,
    offset: GPUSize64? = null,
    size: GPUSize64? = null
): GPUBufferBinding =
    jsObject {
        this.buffer = buffer
        this.offset = offset
        this.size = size
    }