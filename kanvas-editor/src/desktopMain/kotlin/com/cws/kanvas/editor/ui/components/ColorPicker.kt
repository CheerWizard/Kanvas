package com.cws.kanvas.editor.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.cws.kanvas.editor.audio.AudioEffect
import com.cws.kanvas.editor.audio.LocalAudioController
import com.cws.kanvas.editor.ui.LocalAppTextStyles
import com.cws.kanvas.editor.ui.LocalTheme
import com.cws.kanvas.editor.ui.styling.hslToRgb
import com.cws.kanvas.editor.ui.toHex

val hueGradient = Brush.horizontalGradient(
    listOf(
        Color.Red,
        Color.Yellow,
        Color.Green,
        Color.Cyan,
        Color.Blue,
        Color.Magenta,
        Color.Red
    )
)

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    color: Color,
    onColorChanged: (Color) -> Unit,
) {
    val audioController = LocalAudioController.current

    var hue by remember { mutableFloatStateOf(0f) }
    var saturation by remember { mutableFloatStateOf(0f) }
    var lightness by remember { mutableFloatStateOf(0f) }
    var hueSliderPosition by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        val x = change.position.x.coerceIn(0f, size.width.toFloat())
                        val y = change.position.y.coerceIn(0f, size.height.toFloat())
                        saturation = x / size.width
                        lightness = 1f - y / size.height
                        onColorChanged(hslToRgb(hue, saturation, lightness))
                        audioController.play(AudioEffect.Click)
                    }
                }
        ) {
            // draw colors
            val hueColor = Color.hsl(hue = hue, saturation = 1f, lightness = 0.5f)
            drawRect(Brush.horizontalGradient(listOf(Color.White, hueColor)))
            drawRect(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            // draw cursor
            val cx = saturation * size.width
            val cy = (1f - lightness) * size.height
            drawCircle(
                color = Color.White,
                radius = 6.dp.toPx(),
                center = Offset(cx, cy),
                style = Stroke(2.dp.toPx())
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(hueGradient)
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        val x = change.position.x.coerceIn(0f, size.width.toFloat())
                        val y = change.position.y.coerceIn(0f, size.height.toFloat())
                        hueSliderPosition = Offset(x, y)
                        hue = 360f * (x / size.width)
                        onColorChanged(hslToRgb(hue, saturation, lightness))
                        audioController.play(AudioEffect.Click)
                    }
                }
                .drawBehind {
                    // draw cursor
                    drawCircle(
                        color = Color.White,
                        radius = 4.dp.toPx(),
                        center = hueSliderPosition,
                        style = Stroke(2.dp.toPx())
                    )
                }
        )

        AppText(
            text = "Color",
            style = LocalAppTextStyles.current.labelSmall,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .shadow(elevation = LocalTheme.current.elevationLow)
                    .background(color)
                    .border(width = 1.dp, color = LocalTheme.current.border),
            )
            AppText(
                text = color.toHex(),
                style = LocalAppTextStyles.current.labelSmall,
                selectable = true,
            )
        }
    }
}