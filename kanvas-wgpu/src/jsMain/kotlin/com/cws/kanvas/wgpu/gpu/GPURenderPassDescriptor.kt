@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPURenderPassDescriptor :
    GPUObjectDescriptorBase {
    var colorAttachments: JsReadonlyArray<GPURenderPassColorAttachment?>
    var depthStencilAttachment: GPURenderPassDepthStencilAttachment?
    var maxDrawCount: GPUSize64?
    var occlusionQuerySet: GPUQuerySet?
    var timestampWrites: GPURenderPassTimestampWrites?
}

fun GPURenderPassDescriptor(
    colorAttachments: JsReadonlyArray<GPURenderPassColorAttachment>,
    depthStencilAttachment: GPURenderPassDepthStencilAttachment? = null,
    maxDrawCount: GPUSize64? = null,
    occlusionQuerySet: GPUQuerySet? = null,
    timestampWrites: GPURenderPassTimestampWrites? = null,
    label: String? = null
): GPURenderPassDescriptor = jsObject {
    this.colorAttachments = colorAttachments
    this.depthStencilAttachment = depthStencilAttachment
    this.maxDrawCount = maxDrawCount
    this.occlusionQuerySet = occlusionQuerySet
    this.timestampWrites = timestampWrites
    this.label = label
}