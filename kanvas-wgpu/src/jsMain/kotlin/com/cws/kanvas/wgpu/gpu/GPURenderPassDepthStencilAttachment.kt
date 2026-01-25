package com.cws.kanvas.wgpu.gpu

external interface GPURenderPassDepthStencilAttachment {
    var depthClearValue: Float?
    var depthLoadOp: GPULoadOp?
    var depthReadOnly: Boolean?
    var depthStoreOp: GPUStoreOp?
    var stencilClearValue: GPUStencilValue?
    var stencilLoadOp: GPULoadOp?
    var stencilReadOnly: Boolean?
    var stencilStoreOp: GPUStoreOp?
    var view: GPUTextureView
}

fun GPURenderPassDepthStencilAttachment(
    view: GPUTextureView,
    depthClearValue: Float? = null,
    depthLoadOp: GPULoadOp? = null,
    depthStoreOp: GPUStoreOp? = null,
    depthReadOnly: Boolean? = null,
    stencilClearValue: GPUStencilValue? = null,
    stencilLoadOp: GPULoadOp? = null,
    stencilStoreOp: GPUStoreOp? = null,
    stencilReadOnly: Boolean? = null
): GPURenderPassDepthStencilAttachment = jsObject {
    this.view = view
    this.depthClearValue = depthClearValue
    this.depthLoadOp = depthLoadOp
    this.depthStoreOp = depthStoreOp
    this.depthReadOnly = depthReadOnly
    this.stencilClearValue = stencilClearValue
    this.stencilLoadOp = stencilLoadOp
    this.stencilStoreOp = stencilStoreOp
    this.stencilReadOnly = stencilReadOnly
}