package com.cws.kanvas.wgpu.gpu

external interface GPULimits {
    var maxBindGroups: GPUSize32?
    var maxDynamicUniformBuffersPerPipelineLayout: GPUSize32?
    var maxDynamicStorageBuffersPerPipelineLayout: GPUSize32?
    var maxSampledTexturesPerShaderStage: GPUSize32?
    var maxSamplersPerShaderStage: GPUSize32?
    var maxStorageBuffersPerShaderStage: GPUSize32?
    var maxStorageTexturesPerShaderStage: GPUSize32?
    var maxUniformBuffersPerShaderStage: GPUSize32?
    var maxUniformBufferBindingSize: GPUSize64?
    var maxStorageBufferBindingSize: GPUSize64?
    var maxVertexBuffers: GPUSize32?
    var maxBufferSize: GPUSize64?
    var maxVertexAttributes: GPUSize32?
    var maxVertexBufferArrayStride: GPUSize64?
    var maxInterStageShaderComponents: GPUSize32?
    var maxComputeWorkgroupStorageSize: GPUSize32?
    var maxComputeInvocationsPerWorkgroup: GPUSize32?
    var maxComputeWorkgroupSizeX: GPUSize32?
    var maxComputeWorkgroupSizeY: GPUSize32?
    var maxComputeWorkgroupSizeZ: GPUSize32?
    var maxComputeWorkgroupsPerDimension: GPUSize32?
    var maxTextureDimension1D: GPUSize32?
    var maxTextureDimension2D: GPUSize32?
    var maxTextureDimension3D: GPUSize32?
    var maxTextureArrayLayers: GPUSize32?
    var maxColorAttachments: GPUSize32?
    var maxPushConstantSize: GPUSize32?
}

fun GPULimits(
    maxBindGroups: GPUSize32? = null,
    maxDynamicUniformBuffersPerPipelineLayout: GPUSize32? = null,
    maxDynamicStorageBuffersPerPipelineLayout: GPUSize32? = null,
    maxSampledTexturesPerShaderStage: GPUSize32? = null,
    maxSamplersPerShaderStage: GPUSize32? = null,
    maxStorageBuffersPerShaderStage: GPUSize32? = null,
    maxStorageTexturesPerShaderStage: GPUSize32? = null,
    maxUniformBuffersPerShaderStage: GPUSize32? = null,
    maxUniformBufferBindingSize: GPUSize64? = null,
    maxStorageBufferBindingSize: GPUSize64? = null,
    maxVertexBuffers: GPUSize32? = null,
    maxBufferSize: GPUSize64? = null,
    maxVertexAttributes: GPUSize32? = null,
    maxVertexBufferArrayStride: GPUSize64? = null,
    maxInterStageShaderComponents: GPUSize32? = null,
    maxComputeWorkgroupStorageSize: GPUSize32? = null,
    maxComputeInvocationsPerWorkgroup: GPUSize32? = null,
    maxComputeWorkgroupSizeX: GPUSize32? = null,
    maxComputeWorkgroupSizeY: GPUSize32? = null,
    maxComputeWorkgroupSizeZ: GPUSize32? = null,
    maxComputeWorkgroupsPerDimension: GPUSize32? = null,
    maxTextureDimension1D: GPUSize32? = null,
    maxTextureDimension2D: GPUSize32? = null,
    maxTextureDimension3D: GPUSize32? = null,
    maxTextureArrayLayers: GPUSize32? = null,
    maxColorAttachments: GPUSize32? = null,
    maxPushConstantSize: GPUSize32? = null
): GPULimits = jsObject {
    this.maxBindGroups = maxBindGroups
    this.maxDynamicUniformBuffersPerPipelineLayout = maxDynamicUniformBuffersPerPipelineLayout
    this.maxDynamicStorageBuffersPerPipelineLayout = maxDynamicStorageBuffersPerPipelineLayout
    this.maxSampledTexturesPerShaderStage = maxSampledTexturesPerShaderStage
    this.maxSamplersPerShaderStage = maxSamplersPerShaderStage
    this.maxStorageBuffersPerShaderStage = maxStorageBuffersPerShaderStage
    this.maxStorageTexturesPerShaderStage = maxStorageTexturesPerShaderStage
    this.maxUniformBuffersPerShaderStage = maxUniformBuffersPerShaderStage
    this.maxUniformBufferBindingSize = maxUniformBufferBindingSize
    this.maxStorageBufferBindingSize = maxStorageBufferBindingSize
    this.maxVertexBuffers = maxVertexBuffers
    this.maxBufferSize = maxBufferSize
    this.maxVertexAttributes = maxVertexAttributes
    this.maxVertexBufferArrayStride = maxVertexBufferArrayStride
    this.maxInterStageShaderComponents = maxInterStageShaderComponents
    this.maxComputeWorkgroupStorageSize = maxComputeWorkgroupStorageSize
    this.maxComputeInvocationsPerWorkgroup = maxComputeInvocationsPerWorkgroup
    this.maxComputeWorkgroupSizeX = maxComputeWorkgroupSizeX
    this.maxComputeWorkgroupSizeY = maxComputeWorkgroupSizeY
    this.maxComputeWorkgroupSizeZ = maxComputeWorkgroupSizeZ
    this.maxComputeWorkgroupsPerDimension = maxComputeWorkgroupsPerDimension
    this.maxTextureDimension1D = maxTextureDimension1D
    this.maxTextureDimension2D = maxTextureDimension2D
    this.maxTextureDimension3D = maxTextureDimension3D
    this.maxTextureArrayLayers = maxTextureArrayLayers
    this.maxColorAttachments = maxColorAttachments
    this.maxPushConstantSize = maxPushConstantSize
}