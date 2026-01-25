package com.cws.kanvas.wgpu.gpu

external interface GPUShaderModuleDescriptor :
    GPUObjectDescriptorBase {
    var code: String
}

fun GPUShaderModuleDescriptor(
    code: String,
    label: String? = null
): GPUShaderModuleDescriptor = jsObject {
    this.code = code
    this.label = label
}
