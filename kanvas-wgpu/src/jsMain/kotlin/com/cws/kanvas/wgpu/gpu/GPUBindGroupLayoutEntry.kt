package com.cws.kanvas.wgpu.gpu

external interface GPUBindGroupLayoutEntry {
    var binding: GPUIndex32
    var buffer: GPUBufferBindingLayout?
    var externalTexture: GPUExternalTextureBindingLayout?
    var sampler: GPUSamplerBindingLayout?
    var storageTexture: GPUStorageTextureBindingLayout?
    var texture: GPUTextureBindingLayout?
    var visibility: GPUShaderStageFlags
}

fun GPUBindGroupLayoutEntry(
    binding: GPUIndex32,
    buffer: GPUBufferBindingLayout? = null,
    externalTexture: GPUExternalTextureBindingLayout? = null,
    sampler: GPUSamplerBindingLayout? = null,
    storageTexture: GPUStorageTextureBindingLayout? = null,
    texture: GPUTextureBindingLayout? = null,
    visibility: GPUShaderStageFlags,
) : GPUBindGroupLayoutEntry = js("{}").unsafeCast<GPUBindGroupLayoutEntry>().apply {
    this.binding = binding
    this.buffer = buffer
    this.externalTexture = externalTexture
    this.sampler = sampler
    this.storageTexture = storageTexture
    this.texture = texture
    this.visibility = visibility
}
