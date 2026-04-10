package com.cws.kanvas.editor.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconCube: ImageVector
    get() {
        if (_IconCube != null) {
            return _IconCube!!
        }
        _IconCube = ImageVector.Builder(
            name = "IconCube",
            defaultWidth = 256.dp,
            defaultHeight = 256.dp,
            viewportWidth = 256f,
            viewportHeight = 256f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(72.2f, 30.9f)
                curveToRelative(-29.4f, 17f, -54.1f, 31.6f, -55f, 32.5f)
                curveToRelative(-1.6f, 1.5f, -1.7f, 7.2f, -1.7f, 64.6f)
                curveToRelative(0f, 61.6f, 0f, 63f, 2f, 64.9f)
                curveToRelative(3.3f, 3.1f, 108.3f, 63.1f, 110.5f, 63.1f)
                curveToRelative(2.2f, -0f, 107.2f, -60f, 110.5f, -63.1f)
                curveToRelative(2f, -1.9f, 2f, -3.3f, 2f, -64.9f)
                curveToRelative(0f, -61.6f, 0f, -63f, -2f, -64.9f)
                curveToRelative(-3.6f, -3.4f, -108.4f, -63.1f, -110.7f, -63f)
                curveToRelative(-1.3f, -0f, -26.3f, 13.9f, -55.6f, 30.8f)
                close()
                moveTo(172.8f, 42f)
                curveToRelative(24.3f, 14f, 44.2f, 25.7f, 44.2f, 26f)
                curveToRelative(0f, 1f, -87.3f, 50.5f, -89f, 50.5f)
                curveToRelative(-1.7f, -0f, -89f, -49.5f, -89f, -50.5f)
                curveToRelative(0f, -0.7f, 88.2f, -51.9f, 89.1f, -51.6f)
                curveToRelative(0.3f, -0f, 20.5f, 11.6f, 44.7f, 25.6f)
                close()
                moveTo(76.1f, 106.9f)
                curveToRelative(23.3f, 13.3f, 42.7f, 24.5f, 43.2f, 25f)
                curveToRelative(0.4f, 0.4f, 0.6f, 23.7f, 0.5f, 51.9f)
                lineToRelative(-0.3f, 51.1f)
                lineToRelative(-44f, -25.3f)
                lineToRelative(-44f, -25.4f)
                lineToRelative(-0.3f, -51.2f)
                curveToRelative(-0.2f, -42f, 0f, -51.1f, 1.1f, -50.7f)
                curveToRelative(0.7f, 0.3f, 20.4f, 11.4f, 43.8f, 24.6f)
                close()
                moveTo(224.8f, 133.1f)
                lineToRelative(-0.3f, 51.1f)
                lineToRelative(-44f, 25.4f)
                lineToRelative(-44f, 25.3f)
                lineToRelative(-0.3f, -51.1f)
                curveToRelative(-0.1f, -28.2f, 0.1f, -51.5f, 0.5f, -51.9f)
                curveToRelative(1.2f, -1.1f, 86.7f, -49.8f, 87.6f, -49.9f)
                curveToRelative(0.4f, -0f, 0.6f, 23f, 0.5f, 51.1f)
                close()
            }
        }.build()

        return _IconCube!!
    }

@Suppress("ObjectPropertyName")
private var _IconCube: ImageVector? = null
