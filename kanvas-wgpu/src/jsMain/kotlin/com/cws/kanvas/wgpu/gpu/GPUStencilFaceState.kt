package com.cws.kanvas.wgpu.gpu

external interface GPUStencilFaceState {
    var compare: GPUCompareFunction?
    var depthFailOp: GPUStencilOperation?
    var failOp: GPUStencilOperation?
    var passOp: GPUStencilOperation?
}

fun GPUStencilFaceState(
    compare: GPUCompareFunction? = null,
    failOp: GPUStencilOperation? = null,
    depthFailOp: GPUStencilOperation? = null,
    passOp: GPUStencilOperation? = null
): GPUStencilFaceState = jsObject {
    this.compare = compare
    this.failOp = failOp
    this.depthFailOp = depthFailOp
    this.passOp = passOp
}
