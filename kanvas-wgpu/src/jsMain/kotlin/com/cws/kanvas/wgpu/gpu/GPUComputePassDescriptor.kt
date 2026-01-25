package com.cws.kanvas.wgpu.gpu

external interface GPUComputePassDescriptor : GPUObjectDescriptorBase {
    var timestampWrites: GPUComputePassTimestampWrites?
}

fun GPUComputePassDescriptor(
    timestampWrites: GPUComputePassTimestampWrites? = null
): GPUComputePassDescriptor =
    jsObject {
        this.timestampWrites = timestampWrites
    }
