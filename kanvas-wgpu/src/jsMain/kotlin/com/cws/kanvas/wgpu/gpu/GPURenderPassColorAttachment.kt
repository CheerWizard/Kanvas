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
    loadOp: GPULoadOp = GPULoadOp.clear,
    storeOp: GPUStoreOp = GPUStoreOp.store,
    resolveTarget: GPUTextureView? = null,
    clearValue: GPUColor = GPUColor(0.0, 0.0, 0.0, 1.0),
    depthSlice: GPUIntegerCoordinate? = null
): GPURenderPassColorAttachment = jsObject {
    this.view = view
    this.loadOp = loadOp
    this.storeOp = storeOp
    this.resolveTarget = resolveTarget
    this.clearValue = clearValue
    this.depthSlice = depthSlice
}