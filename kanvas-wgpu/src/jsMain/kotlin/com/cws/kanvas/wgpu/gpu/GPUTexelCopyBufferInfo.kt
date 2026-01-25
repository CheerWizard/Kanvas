package com.cws.kanvas.wgpu.gpu

external interface GPUTexelCopyBufferInfo :
    GPUTexelCopyBufferLayout {
    var buffer: GPUBuffer
}

fun GPUTexelCopyBufferInfo(
    buffer: GPUBuffer,
    offset: GPUSize64? = null,
    bytesPerRow: GPUSize32? = null,
    rowsPerImage: GPUSize32? = null
): GPUTexelCopyBufferInfo = jsObject {
    this.buffer = buffer
    this.offset = offset
    this.bytesPerRow = bytesPerRow
    this.rowsPerImage = rowsPerImage
}
