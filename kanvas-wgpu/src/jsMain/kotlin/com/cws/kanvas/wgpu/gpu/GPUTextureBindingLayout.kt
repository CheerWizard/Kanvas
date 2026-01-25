package com.cws.kanvas.wgpu.gpu

external interface GPUTextureBindingLayout {
    var multisampled: Boolean?
    var sampleType: GPUTextureSampleType?
    var viewDimension: GPUTextureViewDimension?
}

fun GPUTextureBindingLayout(
    multisampled: Boolean? = null,
    sampleType: GPUTextureSampleType? = null,
    viewDimension: GPUTextureViewDimension? = null
): GPUTextureBindingLayout = jsObject {
    this.multisampled = multisampled
    this.sampleType = sampleType
    this.viewDimension = viewDimension
}
