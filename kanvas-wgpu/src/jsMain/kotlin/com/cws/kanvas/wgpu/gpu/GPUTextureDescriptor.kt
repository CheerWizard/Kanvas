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
