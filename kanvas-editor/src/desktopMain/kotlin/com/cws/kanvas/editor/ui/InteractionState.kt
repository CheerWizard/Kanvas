package com.cws.kanvas.editor.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
    val focused: State<Boolean>,
    val dragged: State<Boolean>,
)

@Composable
fun rememberInteractionState(
    enabled: Boolean = true,
): InteractionState {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered = interactionSource.collectIsHoveredAsState()
    val pressed = interactionSource.collectIsPressedAsState()
    val focused = interactionSource.collectIsFocusedAsState()
    val dragged = interactionSource.collectIsDraggedAsState()
    val interactionState by remember(enabled) {
        mutableStateOf(
            InteractionState(
                interactionSource = interactionSource,
                enabled = enabled,
                hovered = hovered,
                pressed = pressed,
                focused = focused,
                dragged = dragged,
            )
        )
    }
    return interactionState
}
