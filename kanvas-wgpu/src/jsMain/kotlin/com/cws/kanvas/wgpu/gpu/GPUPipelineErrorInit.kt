package com.cws.kanvas.wgpu.gpu

external interface GPUPipelineErrorInit {
    var reason: GPUPipelineErrorReason
}

fun GPUPipelineErrorInit(
    reason: GPUPipelineErrorReason
): GPUPipelineErrorInit = jsObject {
    this.reason = reason
}
