package com.cws.kanvas.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.cws.kanvas.editor.ui.effects.bloom
import com.cws.kanvas.editor.ui.styling.DefaultColorLightness
import com.cws.kanvas.editor.ui.styling.lightness

inline fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier {
    return if (condition) {
        this.then(modifier(Modifier))
    } else {
        this
    }
}

@Composable
fun Modifier.backgroundOverlay(): Modifier {
    return this
        .background(LocalTheme.current.dockOverlay)
        .border(width = 1.dp, color = LocalTheme.current.accent.lightness(DefaultColorLightness.hovered))
}

@Composable
fun Modifier.bloomBorders(
    width: Dp = 2.dp,
    bloomRadius: Dp = 32.dp,
): Modifier {
    val density = LocalDensity.current
    var radius by remember { mutableStateOf(DpOffset.Zero) }
    val widthPx = with(density) { width.toPx() }

    return this
        .onGloballyPositioned {
            with(density) {
                radius = DpOffset(
                    it.size.width.toDp() / 2 + bloomRadius + width,
                    it.size.height.toDp() / 2 + bloomRadius + width
                )
            }
        }
        .bloom(
            radius = radius,
            drawStyle = Stroke(width = widthPx),
        )
}
