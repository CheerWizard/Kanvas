package com.cws.kanvas.gfx.bridges

data class Image(
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int,
    val format: ImageFormat,
    val pixelFormat: PixelFormat,
)

enum class ImageFormat {
    BGRA,
    RGBA
}

enum class PixelFormat {
    BGRA,
    RGBA
}