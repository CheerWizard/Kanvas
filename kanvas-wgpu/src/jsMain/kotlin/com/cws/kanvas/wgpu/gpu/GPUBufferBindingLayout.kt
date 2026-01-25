package com.cws.kanvas.wgpu.gpu

external interface GPUBufferBindingLayout {
    var hasDynamicOffset: Boolean?
    var minBindingSize: GPUSize64?
    var type: GPUBufferBindingType?
}

fun GPUBufferBindingLayout(
    type: GPUBufferBindingType? = null,
    hasDynamicOffset: Boolean? = null,
    minBindingSize: GPUSize64? = null
): GPUBufferBindingLayout =
    jsObject {
        this.type = type
        this.hasDynamicOffset = hasDynamicOffset
        this.minBindingSize = minBindingSize
    }
