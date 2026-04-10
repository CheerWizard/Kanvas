package com.cws.kanvas.editor.docking.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import com.cws.kanvas.editor.docking.LocalDockController
import com.cws.kanvas.editor.ui.LocalAppTextStyles
import com.cws.kanvas.editor.ui.LocalTheme
import com.cws.kanvas.editor.docking.logic.DockWindowState
import com.cws.kanvas.editor.ui.styling.VerticalDivider
import com.cws.kanvas.editor.ui.components.AppText
import com.cws.kanvas.editor.ui.components.buttons.IconButton
import com.cws.kanvas.editor.ui.icons.IconMaximize
import com.cws.kanvas.editor.ui.icons.IconMinimize
import com.cws.kanvas.editor.ui.icons.IconWindowMode
import com.cws.kanvas.editor.ui.icons.IconX

@Composable
fun FrameWindowScope.DockWindowTitleBar(
    state: DockWindowState,
    layoutCoords: LayoutCoordinates?,
    content: @Composable (windowState: DockWindowState) -> Unit,
) {
    val dockController = LocalDockController.current
    val awtWindow = window

    WindowDraggableArea(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        dockController.toggleWindowMode(state)
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        dockController.dragWindowStart(state)
                    },
                    onDragEnd = {
                        dockController.dragWindowEnd(state)
                    },
                    onDrag = { change, _ ->
                        val localPos = change.position
                        val windowPos = layoutCoords?.localToWindow(localPos) ?: return@detectDragGestures
                        val screenPos = Offset(awtWindow.x + windowPos.x, awtWindow.y + windowPos.y)
                        dockController.dragWindow(state, screenPos)
                    }
                )
            },
    ) {
        content(state)
    }
}

@Composable
fun WindowTitleBarContent(
    modifier: Modifier = Modifier,
    state: DockWindowState,
    showWindowButtons: Boolean,
) {
    Row(
        modifier = modifier.height(32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.weight(1f))
        AppText(
            modifier = Modifier.weight(0.5f),
            text = state.title,
            textAlign = TextAlign.Center,
            style = LocalAppTextStyles.current.titleLarge,
        )
        Spacer(Modifier.weight(1f))
        if (showWindowButtons) {
            WindowButtons(state = state)
        }
    }
}

@Composable
fun WindowButtons(
    state: DockWindowState
) {
    val dockController = LocalDockController.current

    Row(
        modifier = Modifier
            .width(100.dp)
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier
                .weight(0.33f),
            imageVector = IconMinimize,
            style = LocalTheme.current.windowButtonStyle,
            enabled = true,
            onClick = {
                dockController.minimizeWindow(state)
            },
        )
        VerticalDivider(colorStops = LocalTheme.current.dividerColors)
        IconButton(
            modifier = Modifier
                .weight(0.33f),
            imageVector = if (state.maximized) IconWindowMode else IconMaximize,
            style = LocalTheme.current.windowButtonStyle,
            enabled = true,
            onClick = {
                dockController.toggleWindowMode(state)
            },
        )
        VerticalDivider(colorStops = LocalTheme.current.dividerColors)
        IconButton(
            modifier = Modifier
                .weight(0.33f),
            imageVector = IconX,
            style = LocalTheme.current.windowCloseButtonStyle,
            enabled = state.canClose,
            onClick = {
                dockController.closeWindow(state)
            },
        )
    }
}
