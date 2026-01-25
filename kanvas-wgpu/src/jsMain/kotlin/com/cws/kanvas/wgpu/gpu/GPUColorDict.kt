package com.cws.kanvas.wgpu.gpu

external interface GPUColorDict {
    var r: Double
    var g: Double
    var b: Double
    var a: Double
}

fun GPUColorDict(
    r: Double,
    g: Double,
    b: Double,
    a: Double = 1.0
): GPUColorDict =
    jsObject{
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }
