package com.cws.kanvas.editor.ui.components.buttons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.cws.kanvas.editor.ui.components.AppIcon
import com.cws.kanvas.editor.ui.rememberInteractionState
import com.cws.kanvas.editor.ui.styling.ColorLightness
import com.cws.kanvas.editor.ui.styling.lightness

data class IconButtonStyle(
    val backgroundColor: Color,
    val iconColor: Color,
    val lightness: ColorLightness,
)

@Composable
internal fun IconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    enabled: Boolean,
    style: IconButtonStyle,
    onClick: () -> Unit,
) {
    val interactionState = rememberInteractionState(enabled = enabled)

    val enabled = interactionState.enabled
    val hovered by interactionState.hovered
    val pressed by interactionState.pressed

    val color = when {
        !enabled -> style.iconColor.lightness(style.lightness.disabled)
        hovered -> style.iconColor.lightness(style.lightness.hovered)
        pressed -> style.iconColor.lightness(style.lightness.pressed)
        else -> style.iconColor
    }

    BaseButton(
        modifier = modifier,
        interactionState = interactionState,
        color = style.backgroundColor,
        lightness = style.lightness,
        onClick = onClick,
    ) {
        AppIcon(
            imageVector = imageVector,
            color = color,
            interactionState = interactionState,
        )
    }
}
