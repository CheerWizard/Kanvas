package com.cws.kanvas.wgpu.gpu

external interface GPUComputePipelineDescriptor : GPUPipelineDescriptorBase {
    var compute: GPUProgrammableStage
}

fun GPUComputePipelineDescriptor(
    compute: GPUProgrammableStage,
    layout: GPUPipelineLayout,
    label: String? = null,
): GPUComputePipelineDescriptor =
    jsObject {
        this.compute = compute
        this.label = label
        this.layout = layout
    }
