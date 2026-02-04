package com.cws.kanvas.wgpu.gpu

external interface GPUOrigin3D {
    var x: GPUIntegerCoordinate?
    var y: GPUIntegerCoordinate?
    var z: GPUIntegerCoordinate?
}

fun GPUOrigin3D(
    x: GPUIntegerCoordinate? = null,
    y: GPUIntegerCoordinate? = null,
    z: GPUIntegerCoordinate? = null
): GPUOrigin3D = jsObject {
    this.x = x
    this.y = y
    this.z = z
}
