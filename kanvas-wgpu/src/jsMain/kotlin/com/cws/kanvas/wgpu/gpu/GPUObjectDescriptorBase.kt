package com.cws.kanvas.wgpu.gpu

external interface GPUObjectDescriptorBase {
    var label: String?
}

fun GPUObjectDescriptorBase(
    label: String? = null
): GPUObjectDescriptorBase = jsObject {
    this.label = label
}