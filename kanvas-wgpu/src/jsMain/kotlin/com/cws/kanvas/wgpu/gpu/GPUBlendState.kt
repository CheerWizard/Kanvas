package com.cws.kanvas.wgpu.gpu

external interface GPUBlendState {
    var alpha: GPUBlendComponent
    var color: GPUBlendComponent
}

fun GPUBlendState(
    alpha: GPUBlendComponent,
    color: GPUBlendComponent,
): GPUBlendState = jsObject {
    this.alpha = alpha
    this.color = color
}