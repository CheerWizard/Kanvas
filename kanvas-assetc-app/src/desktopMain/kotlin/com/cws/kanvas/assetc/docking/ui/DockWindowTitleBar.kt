package com.cws.kanvas.assetc.docking.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import com.cws.kanvas.assetc.core.LocalDockController
import com.cws.kanvas.assetc.core.LocalTextStyles
import com.cws.kanvas.assetc.core.LocalTheme
import com.cws.kanvas.assetc.docking.logic.DockWindowState
import com.cws.kanvas.assetc.ui.AppText
import com.cws.kanvas.assetc.ui.IconButton
import com.cws.kanvas.assetc.ui.icons.IconMaximize
import com.cws.kanvas.assetc.ui.icons.IconMinimize
import com.cws.kanvas.assetc.ui.icons.IconX

@Composable
fun FrameWindowScope.DockWindowTitleBar(
    state: DockWindowState,
    layoutCoords: LayoutCoordinates?,
    content: @Composable RowScope.(windowState: DockWindowState) -> Unit = {},
) {
    val dockController = LocalDockController.current
    val awtWindow = window

    WindowDraggableArea(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content(state)
            Spacer(Modifier.weight(1f))
            AppText(
                modifier = Modifier.weight(0.5f),
                text = state.title,
                textAlign = TextAlign.Center,
                style = LocalTextStyles.current.title,
            )
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier.weight(0.33f),
                    imageVector = IconMinimize,
                    style = LocalTheme.current.windowButtonStyle,
                    enabled = true,
                    onClick = {
                        dockController.minimizeWindow(state)
                    },
                )
                Spacer(Modifier.width(1.dp).background(LocalTheme.current.windowButtonStyle.borderColor))
                IconButton(
                    modifier = Modifier.weight(0.33f),
                    imageVector = IconMaximize,
                    style = LocalTheme.current.windowButtonStyle,
                    enabled = true,
                    onClick = {
                        dockController.maximizeWindow(state)
                    },
                )
                Spacer(Modifier.width(1.dp).background(LocalTheme.current.windowButtonStyle.borderColor))
                IconButton(
                    modifier = Modifier.weight(0.33f),
                    imageVector = IconX,
                    style = LocalTheme.current.windowCloseButtonStyle,
                    enabled = state.canClose,
                    onClick = {
                        dockController.closeWindow(state)
                    },
                )
            }
        }
    }
}
