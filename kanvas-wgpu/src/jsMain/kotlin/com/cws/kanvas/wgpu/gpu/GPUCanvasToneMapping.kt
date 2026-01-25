package com.cws.kanvas.wgpu.gpu

external interface GPUCanvasToneMapping {
    var mode: GPUCanvasToneMappingMode?
}

fun GPUCanvasToneMapping(
    mode: GPUCanvasToneMappingMode? = null
): GPUCanvasToneMapping =
    jsObject {
        this.mode = mode
    }
