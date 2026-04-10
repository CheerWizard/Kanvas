package com.cws.kanvas.editor.ui.effects

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.cws.kanvas.editor.ui.LocalTheme
import com.cws.kanvas.editor.ui.styling.Transparent

@Composable
fun Modifier.bloom(
    radius: Dp = 16.dp,
    center: Offset = Offset.Unspecified,
    drawStyle: DrawStyle = Fill,
    glowColor: Color = LocalTheme.current.bloomGlow,
    coreColor: Color = LocalTheme.current.bloomCore,
    glowAlpha: Float = LocalTheme.current.bloomGlowAlpha,
    coreAlpha: Float = LocalTheme.current.bloomCoreAlpha,
    haloAlpha: Float = LocalTheme.current.bloomHaloAlpha,
): Modifier {
    return this
        .drawBehind {
            val r = radius.toPx()

            // outer halo
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = haloAlpha),
                        Transparent
                    ),
                    radius = r * 2,
                    center = center,
                ),
                style = drawStyle,
            )

            // inner glow
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = glowAlpha),
                        Transparent
                    ),
                    radius = r,
                    center = center,
                ),
                style = drawStyle,
            )

            // bright core
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        coreColor.copy(alpha = coreAlpha),
                        Transparent
                    ),
                    radius = r * 0.5f,
                    center = center,
                ),
                style = drawStyle,
            )
        }
}

@Composable
fun Modifier.bloom(
    radius: DpOffset = DpOffset(16.dp, 16.dp),
    center: Offset = Offset.Unspecified,
    drawStyle: DrawStyle = Fill,
    glowColor: Color = LocalTheme.current.bloomGlow,
    coreColor: Color = LocalTheme.current.bloomCore,
    glowAlpha: Float = LocalTheme.current.bloomGlowAlpha,
    coreAlpha: Float = LocalTheme.current.bloomCoreAlpha,
    haloAlpha: Float = LocalTheme.current.bloomHaloAlpha,
): Modifier {
    return this
        .drawBehind {
            val rx = radius.x.toPx()
            val ry = radius.y.toPx()

            // outer halo
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = haloAlpha),
                        Transparent
                    ),
                    radius = rx * 2,
                    center = center,
                ),
                style = drawStyle,
            )
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = haloAlpha),
                        Transparent
                    ),
                    radius = ry * 2,
                    center = center,
                ),
                style = drawStyle,
            )

            // inner glow
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = glowAlpha),
                        Transparent
                    ),
                    radius = rx,
                    center = center,
                ),
                style = drawStyle,
            )
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = glowAlpha),
                        Transparent
                    ),
                    radius = ry,
                    center = center,
                ),
                style = drawStyle,
            )

            // bright core
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        coreColor.copy(alpha = coreAlpha),
                        Transparent
                    ),
                    radius = rx * 0.5f,
                    center = center,
                ),
                style = drawStyle,
            )
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        coreColor.copy(alpha = coreAlpha),
                        Transparent
                    ),
                    radius = ry * 0.5f,
                    center = center,
                ),
                style = drawStyle,
            )
        }
}