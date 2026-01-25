package com.cws.kanvas.wgpu.gpu

external interface GPUStorageTextureBindingLayout {
    var access: GPUStorageTextureAccess?
    var format: GPUTextureFormat
    var viewDimension: GPUTextureViewDimension?
}

fun GPUStorageTextureBindingLayout(
    format: GPUTextureFormat,
    access: GPUStorageTextureAccess? = null,
    viewDimension: GPUTextureViewDimension? = null
): GPUStorageTextureBindingLayout = jsObject {
    this.format = format
    this.access = access
    this.viewDimension = viewDimension
}
