@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPUBindGroupLayoutDescriptor :
    GPUObjectDescriptorBase {
    var entries: JsReadonlyArray<GPUBindGroupLayoutEntry>
}

fun GPUBindGroupLayoutDescriptor(
    entries: JsReadonlyArray<GPUBindGroupLayoutEntry>
): GPUBindGroupLayoutDescriptor =
    jsObject {
        this.entries = entries
    }