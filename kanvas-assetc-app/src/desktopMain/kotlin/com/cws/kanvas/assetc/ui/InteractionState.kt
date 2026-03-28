package com.cws.kanvas.assetc.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class InteractionState(
    val interactionSource: MutableInteractionSource,
    val enabled: Boolean,
    val hovered: State<Boolean>,
    val pressed: State<Boolean>,
)

@Composable
fun rememberInteractionState(
    enabled: Boolean = true,
): InteractionState {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered = interactionSource.collectIsHoveredAsState()
    val pressed = interactionSource.collectIsPressedAsState()
    val interactionState by remember(enabled) {
        mutableStateOf(
            InteractionState(
                interactionSource = interactionSource,
                enabled = enabled,
                hovered = hovered,
                pressed = pressed,
            )
        )
    }
    return interactionState
}
