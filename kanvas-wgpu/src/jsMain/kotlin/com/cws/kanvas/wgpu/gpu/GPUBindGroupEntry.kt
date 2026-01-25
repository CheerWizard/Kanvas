package com.cws.kanvas.wgpu.gpu

external interface GPUBindGroupEntry {
    var binding: GPUIndex32
    var resource: GPUBindingResource
}

fun GPUBindGroupEntry(
    binding: GPUIndex32,
    resource: GPUBindingResource,
): GPUBindGroupEntry =
    jsObject {
        this.binding = binding
        this.resource = resource
    }
