package com.cws.kanvas.rendering.frontend

data class Viewport(
    val x: Float = 0f,
    val y: Float = 0f,
    val w: UInt = 0u,
    val h: UInt = 0u,
    val minDepth: Float = 0f,
    val maxDepth: Float = 1f,
)