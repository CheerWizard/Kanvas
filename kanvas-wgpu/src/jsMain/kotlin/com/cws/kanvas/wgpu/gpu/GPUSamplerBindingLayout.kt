package com.cws.kanvas.wgpu.gpu

external interface GPUSamplerBindingLayout {
    var type: GPUSamplerBindingType?
}

fun GPUSamplerBindingLayout(
    type: GPUSamplerBindingType? = null
): GPUSamplerBindingLayout = jsObject {
    this.type = type
}
