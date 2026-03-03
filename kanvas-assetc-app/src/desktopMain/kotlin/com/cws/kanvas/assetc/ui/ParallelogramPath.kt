package com.cws.kanvas.assetc.ui

import androidx.compose.ui.graphics.Path

fun ParallelogramPath(
    angle: Float,
    width: Int,
    height: Int
): Path {
    return Path().apply {
        moveTo(angle, 0f)
        lineTo(width.toFloat(), 0f)
        lineTo(width - angle, height.toFloat())
        lineTo(0f, height.toFloat())
        close()
    }
}
