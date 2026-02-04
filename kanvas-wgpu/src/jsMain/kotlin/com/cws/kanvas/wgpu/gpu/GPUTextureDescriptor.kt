@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPUTextureDescriptor :
    GPUObjectDescriptorBase {
    var dimension: GPUTextureDimension?
    var format: GPUTextureFormat
    var mipLevelCount: GPUIntegerCoordinate?
    var sampleCount: GPUSize32?
    var size: GPUExtent3D
    var usage: GPUTextureUsageFlags
    var viewFormats: JsReadonlyArray<GPUTextureFormat>?
}

fun GPUTextureDescriptor(
    label: String,
    size: GPUExtent3D,
    format: GPUTextureFormat,
    usage: GPUTextureUsageFlags,
    dimension: GPUTextureDimension? = null,
    mipLevelCount: GPUIntegerCoordinate? = null,
    sampleCount: GPUSize32? = null,
    viewFormats: JsReadonlyArray<GPUTextureFormat>? = null
): GPUTextureDescriptor = jsObject {
    this.label = label
    this.size = size
    this.format = format
    this.usage = usage
    this.dimension = dimension
    this.mipLevelCount = mipLevelCount
    this.sampleCount = sampleCount
    this.viewFormats = viewFormats
}