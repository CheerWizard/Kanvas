@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPUBindGroupDescriptor : GPUObjectDescriptorBase {
    var entries: JsReadonlyArray<GPUBindGroupEntry>
    var layout: GPUBindGroupLayout
}

fun GPUBindGroupDescriptor(
    entries: JsReadonlyArray<GPUBindGroupEntry>,
    layout: GPUBindGroupLayout,
): GPUBindGroupDescriptor =
    jsObject {
        this.entries = entries
        this.layout = layout
    }
