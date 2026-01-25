@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPUPipelineLayoutDescriptor : GPUObjectDescriptorBase {
    var bindGroupLayouts: JsReadonlyArray<GPUBindGroupLayout>
}

fun GPUPipelineLayoutDescriptor(
    bindGroupLayouts: JsReadonlyArray<GPUBindGroupLayout>,
    label: String? = null
): GPUPipelineLayoutDescriptor = jsObject {
    this.bindGroupLayouts = bindGroupLayouts
    this.label = label
}
