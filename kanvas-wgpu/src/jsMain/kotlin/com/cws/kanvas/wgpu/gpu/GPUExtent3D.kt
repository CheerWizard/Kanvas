package com.cws.kanvas.wgpu.gpu

external interface GPUExtent3D {
    var depthOrArrayLayers: GPUIntegerCoordinate?
    var height: GPUIntegerCoordinate?
    var width: GPUIntegerCoordinate
}

fun GPUExtent3D(
    width: GPUIntegerCoordinate,
    height: GPUIntegerCoordinate? = null,
    depthOrArrayLayers: GPUIntegerCoordinate? = null
): GPUExtent3D = jsObject {
    this.width = width
    this.height = height
    this.depthOrArrayLayers = depthOrArrayLayers
}