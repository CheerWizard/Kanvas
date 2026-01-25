package com.cws.kanvas.wgpu.gpu

external interface GPURenderBundleDescriptor : GPUObjectDescriptorBase

fun GPURenderBundleDescriptor(
    label: String? = null
): GPURenderBundleDescriptor = jsObject {
    this.label = label
}
