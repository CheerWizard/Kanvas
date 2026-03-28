package com.cws.kanvas.assetc.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import com.cws.kanvas.assetc.ui.InteractionState
import com.cws.kanvas.assetc.ui.styling.ColorLightness
import com.cws.kanvas.assetc.ui.styling.DefaultColorLightness
import com.cws.kanvas.assetc.ui.styling.lightness

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    color: Color,
    interactionState: InteractionState,
    lightness: ColorLightness = DefaultColorLightness,
) {
    val tint = when {
        !interactionState.enabled -> color.lightness(lightness.disabled)
        interactionState.hovered.value -> color.lightness(lightness.hovered)
        interactionState.pressed.value -> color.lightness(lightness.pressed)
        else -> color
    }

    Image(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        colorFilter = ColorFilter.tint(tint),
    )
}