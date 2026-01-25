package com.cws.kanvas.wgpu.gpu

external interface GPUExtent3DDict {
    var depthOrArrayLayers: GPUIntegerCoordinate?
    var height: GPUIntegerCoordinate?
    var width: GPUIntegerCoordinate
}

fun GPUExtent3DDict(
    width: GPUIntegerCoordinate,
    height: GPUIntegerCoordinate? = null,
    depthOrArrayLayers: GPUIntegerCoordinate? = null
): GPUExtent3DDict = jsObject {
    this.width = width
    this.height = height
    this.depthOrArrayLayers = depthOrArrayLayers
}