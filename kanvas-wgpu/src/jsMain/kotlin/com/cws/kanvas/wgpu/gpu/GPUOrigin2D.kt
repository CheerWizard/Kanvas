package com.cws.kanvas.wgpu.gpu

external interface GPUOrigin2D {
    var x: GPUIntegerCoordinate?
    var y: GPUIntegerCoordinate?
}

fun GPUOrigin2D(
    x: GPUIntegerCoordinate? = null,
    y: GPUIntegerCoordinate? = null
): GPUOrigin2D = jsObject {
    this.x = x
    this.y = y
}
