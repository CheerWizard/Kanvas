package com.cws.kanvas.assetc.docking.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.cws.kanvas.assetc.core.LocalDockController
import com.cws.kanvas.assetc.core.LocalTextStyles
import com.cws.kanvas.assetc.core.LocalTheme
import com.cws.kanvas.assetc.docking.logic.DockContainerState
import com.cws.kanvas.assetc.ui.AppText
import com.cws.kanvas.assetc.ui.Blue
import com.cws.kanvas.assetc.ui.White
import com.cws.kanvas.assetc.ui.icons.IconX

@Composable
internal fun DockContainer(
    modifier: Modifier = Modifier,
    hovered: Boolean,
    container: DockContainerState,
) {
    val dockController = LocalDockController.current
    val windowCoords = LocalWindowCoords.current
    val currentWindowId = container.currentWindow
    val currentWindow = dockController[currentWindowId]
    val content = currentWindow?.content

    Column(
        modifier = modifier
            .background(
                color = LocalTheme.current.background,
            ),
    ) {
        if (container.windows.isNotEmpty()) {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                itemsIndexed(container.windows) { i, windowId ->
                    dockController[windowId]?.let { window ->
                        DockContainerTab(
                            modifier = Modifier
                                .width(100.dp),
                            title = window.title,
                            selected = windowId == currentWindowId,
                            canClose = window.canClose,
                            onClose = {
                                dockController.closeTab(container, windowId)
                            },
                            onSelected = {
                                dockController.selectTab(container, windowId)
                            },
                            onDragStart = {
                                dockController.dragTabStart(container)
                            },
                            onDragEnd = {
                                dockController.dragTabEnd(container)
                            },
                            onDrag = { change ->
                                val screenPos = windowCoords?.toScreenPos(change.position) ?: return@DockContainerTab
                                dockController.dragTab(container, windowCoords, screenPos)
                            }
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            content?.invoke()
            if (hovered) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LocalTheme.current.dockOverlay)
                        .border(width = 1.dp, color = LocalTheme.current.accentHover)
                )
            }
        }
    }
}

@Composable
internal fun DockContainerTab(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    canClose: Boolean,
    onClose: () -> Unit,
    onSelected: () -> Unit,
    onDragStart: () -> Unit,
    onDragEnd: () -> Unit,
    onDrag: (change: PointerInputChange) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val color = when {
        hovered -> LocalTheme.current.accentHover
        selected -> LocalTheme.current.accentPressed
        else -> LocalTheme.current.accent
    }

    Row(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { onDragStart() },
                    onDragEnd = onDragEnd,
                    onDrag = { change, _ -> onDrag(change) },
                )
            }
            .clickable(
                onClick = onSelected,
                interactionSource = interactionSource,
                indication = null,
            )
            .hoverable(
                interactionSource = interactionSource,
            )
            .background(color)
            .padding(vertical = 2.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppText(
            text = title,
            style = LocalTextStyles.current.body,
        )
        Spacer(Modifier.weight(1f))
        if (canClose) {
            Image(
                modifier = Modifier
                    .size(16.dp)
                    .clickable(onClick = onClose),
                imageVector = IconX,
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                colorFilter = ColorFilter.tint(White)
            )
        }
    }
}

@Preview
@Composable
fun Preview_DockContainerTab() {

}
