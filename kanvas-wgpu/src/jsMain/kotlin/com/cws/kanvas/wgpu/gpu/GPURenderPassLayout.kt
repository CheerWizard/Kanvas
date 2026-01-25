@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPURenderPassLayout :
    GPUObjectDescriptorBase {
    var colorFormats: JsReadonlyArray<GPUTextureFormat?>
    var depthStencilFormat: GPUTextureFormat?
    var sampleCount: GPUSize32?
}

fun GPURenderPassLayout(
    colorFormats: JsReadonlyArray<GPUTextureFormat>,
    depthStencilFormat: GPUTextureFormat? = null,
    sampleCount: GPUSize32? = null,
    label: String? = null
): GPURenderPassLayout = jsObject {
    this.colorFormats = colorFormats
    this.depthStencilFormat = depthStencilFormat
    this.sampleCount = sampleCount
    this.label = label
}
