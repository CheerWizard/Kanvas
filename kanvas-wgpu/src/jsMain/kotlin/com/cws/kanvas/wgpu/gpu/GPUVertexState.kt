@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPUVertexState :
    GPUProgrammableStage {
    var buffers: JsReadonlyArray<GPUVertexBufferLayout?>?
}

fun GPUVertexState(
    module: GPUShaderModule,
    entryPoint: String,
    buffers: List<GPUVertexBufferLayout?>? = null
): GPUVertexState = jsObject {
    this.module = module
    this.entryPoint = entryPoint
    this.buffers = buffers?.toTypedArray()?.unsafeCast<JsReadonlyArray<GPUVertexBufferLayout?>>()
}
