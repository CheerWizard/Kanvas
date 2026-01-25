package com.cws.kanvas.wgpu.gpu

external interface GPUOrigin2DDict {
    var x: GPUIntegerCoordinate?
    var y: GPUIntegerCoordinate?
}

fun GPUOrigin2DDict(
    x: GPUIntegerCoordinate? = null,
    y: GPUIntegerCoordinate? = null
): GPUOrigin2DDict = jsObject {
    this.x = x
    this.y = y
}
