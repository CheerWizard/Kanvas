package com.cws.kanvas.wgpu.gpu

external interface GPUColor {
    var r: Double
    var g: Double
    var b: Double
    var a: Double
}

fun GPUColor(
    r: Double,
    g: Double,
    b: Double,
    a: Double = 1.0
): GPUColor =
    jsObject{
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }
