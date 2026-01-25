package com.cws.kanvas.wgpu.gpu

external interface GPUOrigin3DDict {
    var x: GPUIntegerCoordinate?
    var y: GPUIntegerCoordinate?
    var z: GPUIntegerCoordinate?
}

fun GPUOrigin3DDict(
    x: GPUIntegerCoordinate? = null,
    y: GPUIntegerCoordinate? = null,
    z: GPUIntegerCoordinate? = null
): GPUOrigin3DDict = jsObject {
    this.x = x
    this.y = y
    this.z = z
}
