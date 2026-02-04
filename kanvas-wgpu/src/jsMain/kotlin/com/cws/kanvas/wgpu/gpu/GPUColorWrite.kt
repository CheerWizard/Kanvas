package com.cws.kanvas.wgpu.gpu

object GPUColorWrite {
    val RED = 1 shl 0
    val GREEN = 1 shl 1
    val BLUE = 1 shl 2
    val ALPHA = 1 shl 3
    val ALL = RED or GREEN or BLUE or ALPHA
}