package com.cws.kanvas.wgpu.gpu

external interface GPUDepthStencilState {
    var format: GPUTextureFormat
    var depthBias: GPUDepthBias?
    var depthBiasClamp: Float?
    var depthBiasSlopeScale: Float?
    var depthCompare: GPUCompareFunction?
    var depthWriteEnabled: Boolean?
    var stencilFront: GPUStencilFaceState?
    var stencilBack: GPUStencilFaceState?
    var stencilReadMask: GPUStencilValue?
    var stencilWriteMask: GPUStencilValue?
}

fun GPUDepthStencilState(
    format: GPUTextureFormat,
    depthBias: GPUDepthBias? = null,
    depthBiasClamp: Float? = null,
    depthBiasSlopeScale: Float? = null,
    depthCompare: GPUCompareFunction? = null,
    depthWriteEnabled: Boolean? = null,
    stencilFront: GPUStencilFaceState? = null,
    stencilBack: GPUStencilFaceState? = null,
    stencilReadMask: GPUStencilValue? = null,
    stencilWriteMask: GPUStencilValue? = null
): GPUDepthStencilState =
    jsObject {
        this.format = format
        this.depthBias = depthBias
        this.depthBiasClamp = depthBiasClamp
        this.depthBiasSlopeScale = depthBiasSlopeScale
        this.depthCompare = depthCompare
        this.depthWriteEnabled = depthWriteEnabled
        this.stencilFront = stencilFront
        this.stencilBack = stencilBack
        this.stencilReadMask = stencilReadMask
        this.stencilWriteMask = stencilWriteMask
    }
