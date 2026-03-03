package com.cws.kanvas.assetc.docking.math

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

fun hitRect(
    topLeft: Offset,
    size: Size,
    target: Offset,
): Boolean {
    val topRight = Offset(topLeft.x + size.width, topLeft.y)
    val bottomLeft = Offset(topLeft.x, topLeft.y + size.height)
    val bottomRight = Offset(topLeft.x + size.width, topLeft.y + size.height)
    return  target.x >= topLeft.x && target.y >= topLeft.y &&
            target.x >= bottomLeft.x && target.y <= bottomLeft.y &&
            target.x <= topRight.x && target.y >= topRight.y &&
            target.x <= bottomRight.x && target.y <= bottomRight.y
}
