package com.cws.kanvas.wgpu.gpu

external interface GPUMultisampleState {
    var alphaToCoverageEnabled: Boolean?
    var count: GPUSize32?
    var mask: GPUSampleMask?
}

fun GPUMultisampleState(
    alphaToCoverageEnabled: Boolean? = null,
    count: GPUSize32? = null,
    mask: GPUSampleMask? = null
): GPUMultisampleState = jsObject {
    this.alphaToCoverageEnabled = alphaToCoverageEnabled
    this.count = count
    this.mask = mask
}
