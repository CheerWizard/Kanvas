package com.cws.kanvas.wgpu.gpu

external interface GPURenderPipelineDescriptor :
    GPUPipelineDescriptorBase {
    var depthStencil: GPUDepthStencilState?
    var fragment: GPUFragmentState?
    var multisample: GPUMultisampleState?
    var primitive: GPUPrimitiveState?
    var vertex: GPUVertexState
}

fun GPURenderPipelineDescriptor(
    vertex: GPUVertexState,
    fragment: GPUFragmentState? = null,
    primitive: GPUPrimitiveState? = null,
    depthStencil: GPUDepthStencilState? = null,
    multisample: GPUMultisampleState? = null,
    layout: dynamic = "auto",
    label: String? = null
): GPURenderPipelineDescriptor = jsObject {
    this.vertex = vertex
    this.fragment = fragment
    this.primitive = primitive
    this.depthStencil = depthStencil
    this.multisample = multisample
    this.layout = layout
    this.label = label
}
