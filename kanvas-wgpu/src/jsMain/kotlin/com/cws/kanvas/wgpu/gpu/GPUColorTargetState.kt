package com.cws.kanvas.wgpu.gpu

external interface GPUColorTargetState {
    var format: GPUTextureFormat
    var blend: GPUBlendState?
    var writeMask: GPUColorWriteFlags?
}

fun GPUColorTargetState(
    format: GPUTextureFormat,
    blend: GPUBlendState? = null,
    writeMask: GPUColorWriteFlags? = null
): GPUColorTargetState =
    jsObject {
        this.format = format
        this.blend = blend
        this.writeMask = writeMask
    }
