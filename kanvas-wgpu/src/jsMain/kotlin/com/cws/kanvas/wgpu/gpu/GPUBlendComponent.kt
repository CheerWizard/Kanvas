package com.cws.kanvas.wgpu.gpu

external interface GPUBlendComponent {
    var dstFactor: GPUBlendFactor?
    var operation: GPUBlendOperation?
    var srcFactor: GPUBlendFactor?
}

fun GPUBlendComponent(
    dstFactor: GPUBlendFactor?,
    operation: GPUBlendOperation?,
    srcFactor: GPUBlendFactor?,
): GPUBlendComponent = jsObject {
    this.dstFactor = dstFactor
    this.operation = operation
    this.srcFactor = srcFactor
}