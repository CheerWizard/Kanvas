package com.cws.kanvas.editor.ui.styling

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

data class ColorLightness(
    val disabled: Float,
    val hovered: Float,
    val pressed: Float,
    val hoverSelected: Float,
)

val DefaultColorLightness = ColorLightness(
    disabled = -0.35f,
    pressed = -0.10f,
    hovered = 0.05f,
    hoverSelected = 0.03f
)

fun Color.lightness(lightness: Float): Color {
    // convert RGB to HSL
    val max = maxOf(red, green, blue)
    val min = minOf(red, green, blue)
    val delta = max - min

    // apply lightness factor
    val l = ((max + min) / 2f + lightness).coerceIn(0f, 1f)

    if (delta == 0f) {
        return hslToRgb(0f, 0f, l)
    }

    val s = delta / (1f - abs(2f * l - 1f))

    var h = when (max) {
        red -> 60f * (((green - blue) / delta) % 6f)
        green -> 60f * (((blue - red) / delta) + 2f)
        else -> 60f * (((red - green) / delta) + 4f)
    }

    h = if (h < 0f) h + 360f else h

    return hslToRgb(h, s, l)
}

fun hslToRgb(h: Float, s: Float, l: Float): Color {
    val c = (1 - abs(2 * l - 1)) * s
    val x = c * (1 - abs((h / 60f) % 2 - 1))
    val m = l - c / 2

    val (r1, g1, b1) = when {
        h < 60 -> Color(c, x, 0f)
        h < 120 -> Color(x, c, 0f)
        h < 180 -> Color(0f, c, x)
        h < 240 -> Color(0f, x, c)
        h < 300 -> Color(x, 0f, c)
        else -> Color(c, 0f, x)
    }

    return Color(r1 + m, g1 + m, b1 + m)
}
