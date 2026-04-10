package com.cws.kanvas.editor.overlay

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.onClick
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerButton
import com.cws.kanvas.editor.ui.rememberInteractionState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.clickOverlay(
    id: String,
    showPointerButton: PointerButton = PointerButton.Secondary,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
): Modifier {
    val overlayController = LocalOverlayController.current

    val clickOverlayState = remember {
        ClickOverlayState(
            id = id,
            content = content,
        )
    }

    return this
        .onClick(
            enabled = enabled,
            matcher = PointerMatcher.mouse(showPointerButton),
            onClick = {
                overlayController.addClickOverlay(clickOverlayState)
            }
        )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.hoverClickOverlay(
    id: String,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
): Modifier {
    val overlayController = LocalOverlayController.current
    val interactionState = rememberInteractionState(enabled = enabled)
    val hovered by interactionState.hovered

    LaunchedEffect(hovered) {
        if (hovered) {
            overlayController.addClickOverlay(
                ClickOverlayState(
                    id = id,
                    content = content,
                )
            )
        }
    }

    return this
        .hoverable(
            enabled = enabled,
            interactionSource = interactionState.interactionSource,
        )
}