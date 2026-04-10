package com.cws.kanvas.editor.overlay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.isTertiaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.cws.kanvas.editor.ui.toDpOffset
import com.cws.kanvas.editor.ui.toOffset

@Composable
fun Overlay(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val overlayController = LocalOverlayController.current

    val cursor = overlayController.cursor
    val hidePointerButton = overlayController.hidePointerButton
    val hoverOverlays = overlayController.hoverOverlays
    val clickOverlays = overlayController.clickOverlays

    var clickableArea by remember { mutableStateOf<Rect?>(null) }

    Box(
        modifier = modifier
            .pointerInput(clickableArea, hidePointerButton) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)

                        overlayController.cursor = with(density) {
                            event.changes.first().position.toDpOffset()
                        }

                        clickableArea?.let { area ->
                            if (event.type == PointerEventType.Press) {
                                val hideButtonClicked = when (hidePointerButton) {
                                    PointerButton.Primary -> event.buttons.isPrimaryPressed
                                    PointerButton.Secondary -> event.buttons.isSecondaryPressed
                                    PointerButton.Tertiary -> event.buttons.isTertiaryPressed
                                    else -> false
                                }

                                if (hideButtonClicked) {
                                    event.changes.forEach { change ->
                                        val clickOutside = !area.contains(change.position)
                                        if (clickOutside && change.pressed != change.previousPressed) {
                                            change.consume()
                                            overlayController.clearClickOverlays()
                                            clickableArea = null
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
    ) {
        content()

        clickOverlays.forEach { (id, state) ->
            Box(
                modifier = Modifier
                    .onGloballyPositioned {
                        clickableArea = Rect(
                            with(density) { state.position.toOffset() },
                            it.size.toSize(),
                        )
                    }
                    .absoluteOffset(state.position.x, state.position.y)
                    .padding(start = 10.dp, top = 10.dp),
            ) {
                state.content()
            }
        }

        hoverOverlays.forEach { (id, state) ->
            Box(
                modifier = Modifier
                    .absoluteOffset(cursor.x, cursor.y)
                    .padding(start = 10.dp, top = 10.dp),
            ) {
                state.content()
            }
        }
    }
}