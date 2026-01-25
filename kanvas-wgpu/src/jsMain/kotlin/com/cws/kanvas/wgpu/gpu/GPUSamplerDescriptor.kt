package com.cws.kanvas.wgpu.gpu

external interface GPUSamplerDescriptor :
    GPUObjectDescriptorBase {
    var addressModeU: GPUAddressMode?
    var addressModeV: GPUAddressMode?
    var addressModeW: GPUAddressMode?
    var compare: GPUCompareFunction?
    var lodMaxClamp: Float?
    var lodMinClamp: Float?
    var magFilter: GPUFilterMode?
    var maxAnisotropy: Short?
    var minFilter: GPUFilterMode?
    var mipmapFilter: GPUMipmapFilterMode?
}

fun GPUSamplerDescriptor(
    addressModeU: GPUAddressMode? = null,
    addressModeV: GPUAddressMode? = null,
    addressModeW: GPUAddressMode? = null,
    magFilter: GPUFilterMode? = null,
    minFilter: GPUFilterMode? = null,
    mipmapFilter: GPUMipmapFilterMode? = null,
    compare: GPUCompareFunction? = null,
    lodMinClamp: Float? = null,
    lodMaxClamp: Float? = null,
    maxAnisotropy: Short? = null,
    label: String? = null
): GPUSamplerDescriptor = jsObject {
    this.addressModeU = addressModeU
    this.addressModeV = addressModeV
    this.addressModeW = addressModeW
    this.magFilter = magFilter
    this.minFilter = minFilter
    this.mipmapFilter = mipmapFilter
    this.compare = compare
    this.lodMinClamp = lodMinClamp
    this.lodMaxClamp = lodMaxClamp
    this.maxAnisotropy = maxAnisotropy
    this.label = label
}