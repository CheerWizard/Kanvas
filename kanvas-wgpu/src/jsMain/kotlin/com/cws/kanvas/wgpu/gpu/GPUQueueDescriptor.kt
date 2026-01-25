package com.cws.kanvas.wgpu.gpu

external interface GPUQueueDescriptor : GPUObjectDescriptorBase

fun GPUQueueDescriptor(
    label: String? = null
): GPUQueueDescriptor = jsObject {
    this.label = label
}