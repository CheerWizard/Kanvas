package com.cws.kanvas.editor.ui.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cws.kanvas.editor.ui.InteractionState
import com.cws.kanvas.editor.ui.LocalTheme
import com.cws.kanvas.editor.ui.applyIf
import com.cws.kanvas.editor.ui.bloomBorders
import com.cws.kanvas.editor.ui.styling.ButtonPrimary
import com.cws.kanvas.editor.ui.styling.ColorLightness
import com.cws.kanvas.editor.ui.styling.ItemGradient
import com.cws.kanvas.editor.ui.styling.lightness
import com.cws.kanvas.editor.ui.styling.surfaceButton

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    interactionState: InteractionState,
    color: Color,
    lightness: ColorLightness,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val enabled = interactionState.enabled
    val hovered by interactionState.hovered
    val pressed by interactionState.pressed

    val background = when {
        !enabled -> ItemGradient.lightness(lightness.disabled)
        hovered -> ItemGradient.lightness(lightness.hovered)
        pressed -> ItemGradient.lightness(lightness.pressed)
        else -> ItemGradient
    }

    Box(
        modifier = modifier
            .surfaceButton(
                background = background,
            )
            .applyIf(hovered || pressed) {
                bloomBorders()
            }
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionState.interactionSource,
                indication = ripple(bounded = false, color = LocalTheme.current.bloomGlow),
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}