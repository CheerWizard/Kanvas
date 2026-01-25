package com.cws.kanvas.wgpu.gpu

external interface GPUPrimitiveState {
    var cullMode: GPUCullMode?
    var frontFace: GPUFrontFace?
    var stripIndexFormat: GPUIndexFormat?
    var topology: GPUPrimitiveTopology?
    var unclippedDepth: Boolean?
}

fun GPUPrimitiveState(
    cullMode: GPUCullMode? = null,
    frontFace: GPUFrontFace? = null,
    stripIndexFormat: GPUIndexFormat? = null,
    topology: GPUPrimitiveTopology? = null,
    unclippedDepth: Boolean? = null
): GPUPrimitiveState = jsObject {
    this.cullMode = cullMode
    this.frontFace = frontFace
    this.stripIndexFormat = stripIndexFormat
    this.topology = topology
    this.unclippedDepth = unclippedDepth
}
