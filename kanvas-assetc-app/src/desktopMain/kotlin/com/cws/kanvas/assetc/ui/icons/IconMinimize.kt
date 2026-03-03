package com.cws.kanvas.assetc.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconMinimize: ImageVector
    get() {
        if (_IconMinimize != null) {
            return _IconMinimize!!
        }
        _IconMinimize = ImageVector.Builder(
            name = "IconMinimize",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 52f,
            viewportHeight = 52f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(50f, 48.5f)
                curveToRelative(0f, 0.8f, -0.7f, 1.5f, -1.5f, 1.5f)
                horizontalLineToRelative(-45f)
                curveTo(2.7f, 50f, 2f, 49.3f, 2f, 48.5f)
                verticalLineToRelative(-3f)
                curveTo(2f, 44.7f, 2.7f, 44f, 3.5f, 44f)
                horizontalLineToRelative(45f)
                curveToRelative(0.8f, 0f, 1.5f, 0.7f, 1.5f, 1.5f)
                verticalLineTo(48.5f)
                close()
            }
        }.build()

        return _IconMinimize!!
    }

@Suppress("ObjectPropertyName")
private var _IconMinimize: ImageVector? = null
