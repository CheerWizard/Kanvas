package com.cws.kanvas.wgpu.gpu

external interface GPURenderPassColorAttachment {
    var clearValue: GPUColor?
    var depthSlice: GPUIntegerCoordinate?
    var loadOp: GPULoadOp
    var resolveTarget: GPUTextureView?
    var storeOp: GPUStoreOp
    var view: GPUTextureView
}

fun GPURenderPassColorAttachment(
    view: GPUTextureView,
    loadOp: GPULoadOp,
    storeOp: GPUStoreOp,
    resolveTarget: GPUTextureView? = null,
    clearValue: GPUColor? = null,
    depthSlice: GPUIntegerCoordinate? = null
): GPURenderPassColorAttachment = jsObject {
    this.view = view
    this.loadOp = loadOp
    this.storeOp = storeOp
    this.resolveTarget = resolveTarget
    this.clearValue = clearValue
    this.depthSlice = depthSlice
}