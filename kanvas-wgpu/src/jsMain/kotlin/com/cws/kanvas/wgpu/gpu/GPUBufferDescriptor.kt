package com.cws.kanvas.wgpu.gpu

external interface GPUBufferDescriptor : GPUObjectDescriptorBase {
    var mappedAtCreation: Boolean?
    var size: GPUSize64
    var usage: GPUBufferUsageFlags
}

fun GPUBufferDescriptor(
    size: GPUSize64,
    usage: GPUBufferUsageFlags,
    mappedAtCreation: Boolean? = null
): GPUBufferDescriptor =
    jsObject {
        this.size = size
        this.usage = usage
        this.mappedAtCreation = mappedAtCreation
    }
