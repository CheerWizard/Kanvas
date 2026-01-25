package com.cws.kanvas.wgpu.gpu

external interface GPUPipelineDescriptorBase :
    GPUObjectDescriptorBase {
    var layout: dynamic /* GPUPipelineLayout | GPUAutoLayoutMode */
}

fun GPUPipelineDescriptorBase(
    layout: dynamic,
    label: String? = null
): GPUPipelineDescriptorBase = jsObject {
    this.layout = layout
    this.label = label
}
