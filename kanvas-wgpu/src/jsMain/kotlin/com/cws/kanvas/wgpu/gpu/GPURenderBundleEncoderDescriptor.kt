@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPURenderBundleEncoderDescriptor :
    GPURenderPassLayout {
    var depthReadOnly: Boolean?
    var stencilReadOnly: Boolean?
}

fun GPURenderBundleEncoderDescriptor(
    depthReadOnly: Boolean? = null,
    stencilReadOnly: Boolean? = null,
    colorFormats: JsReadonlyArray<GPUTextureFormat>? = null,
    depthStencilFormat: GPUTextureFormat? = null,
    label: String? = null
): GPURenderBundleEncoderDescriptor = jsObject {
    this.depthReadOnly = depthReadOnly
    this.stencilReadOnly = stencilReadOnly
    this.colorFormats = colorFormats
    this.depthStencilFormat = depthStencilFormat
    this.label = label
}