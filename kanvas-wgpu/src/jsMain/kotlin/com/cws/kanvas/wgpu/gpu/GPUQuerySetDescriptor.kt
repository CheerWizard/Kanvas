package com.cws.kanvas.wgpu.gpu

external interface GPUQuerySetDescriptor :
    GPUObjectDescriptorBase {
    var count: GPUSize32
    var type: GPUQueryType
}

fun GPUQuerySetDescriptor(
    count: GPUSize32,
    type: GPUQueryType,
    label: String? = null
): GPUQuerySetDescriptor = jsObject {
    this.count = count
    this.type = type
    this.label = label
}
