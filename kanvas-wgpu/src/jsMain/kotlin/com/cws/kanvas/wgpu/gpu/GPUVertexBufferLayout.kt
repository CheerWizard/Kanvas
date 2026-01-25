@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPUVertexBufferLayout {
    var arrayStride: GPUSize64
    var attributes: JsReadonlyArray<GPUVertexAttribute>
    var stepMode: GPUVertexStepMode?
}

fun GPUVertexBufferLayout(
    arrayStride: GPUSize64,
    attributes: List<GPUVertexAttribute>,
    stepMode: GPUVertexStepMode? = null
): GPUVertexBufferLayout = jsObject {
    this.arrayStride = arrayStride
    this.attributes = attributes.toTypedArray().unsafeCast<JsReadonlyArray<GPUVertexAttribute>>()
    this.stepMode = stepMode
}
