package com.cws.kanvas.wgpu.gpu

external interface GPUTexelCopyBufferLayout {
    var bytesPerRow: GPUSize32?
    var offset: GPUSize64?
    var rowsPerImage: GPUSize32?
}

fun GPUTexelCopyBufferLayout(
    offset: GPUSize64? = null,
    bytesPerRow: GPUSize32? = null,
    rowsPerImage: GPUSize32? = null
): GPUTexelCopyBufferLayout = jsObject {
    this.offset = offset
    this.bytesPerRow = bytesPerRow
    this.rowsPerImage = rowsPerImage
}
