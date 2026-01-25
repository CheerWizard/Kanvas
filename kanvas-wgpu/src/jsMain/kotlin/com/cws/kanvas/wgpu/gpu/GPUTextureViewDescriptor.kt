package com.cws.kanvas.wgpu.gpu

external interface GPUTextureViewDescriptor :
    GPUObjectDescriptorBase {
    var arrayLayerCount: GPUIntegerCoordinate?
    var aspect: GPUTextureAspect?
    var baseArrayLayer: GPUIntegerCoordinate?
    var baseMipLevel: GPUIntegerCoordinate?
    var dimension: GPUTextureViewDimension?
    var format: GPUTextureFormat?
    var mipLevelCount: GPUIntegerCoordinate?
}

fun GPUTextureViewDescriptor(
    format: GPUTextureFormat? = null,
    dimension: GPUTextureViewDimension? = null,
    aspect: GPUTextureAspect? = null,
    baseMipLevel: GPUIntegerCoordinate? = null,
    mipLevelCount: GPUIntegerCoordinate? = null,
    baseArrayLayer: GPUIntegerCoordinate? = null,
    arrayLayerCount: GPUIntegerCoordinate? = null,
    label: String? = null
): GPUTextureViewDescriptor = jsObject {
    this.format = format
    this.dimension = dimension
    this.aspect = aspect
    this.baseMipLevel = baseMipLevel
    this.mipLevelCount = mipLevelCount
    this.baseArrayLayer = baseArrayLayer
    this.arrayLayerCount = arrayLayerCount
    this.label = label
}
