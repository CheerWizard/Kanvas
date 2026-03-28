package com.cws.kanvas.assetc.docking.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cws.kanvas.assetc.docking.LocalDockController
import com.cws.kanvas.assetc.docking.logic.DockContainerState
import com.cws.kanvas.assetc.ui.LocalAppTextStyles
import com.cws.kanvas.assetc.ui.LocalTheme
import com.cws.kanvas.assetc.ui.applyIf
import com.cws.kanvas.assetc.ui.backgroundOverlay
import com.cws.kanvas.assetc.ui.bloomBorders
import com.cws.kanvas.assetc.ui.components.AppText
import com.cws.kanvas.assetc.ui.icons.IconX
import com.cws.kanvas.assetc.ui.styling.TabTextActive
import com.cws.kanvas.assetc.ui.styling.White
import com.cws.kanvas.assetc.ui.styling.surface

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
    val elevation = if (hovered) LocalTheme.current.elevationHigh else LocalTheme.current.elevationLow

    Column(
        modifier = modifier
            .surface(elevation),
    ) {
        if (container.windows.isNotEmpty()) {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                itemsIndexed(container.windows) { i, windowId ->
                    dockController[windowId]?.let { window ->
                        DockContainerTab(
                            modifier = Modifier
                                .width(160.dp),
                            title = window.title,
                            selected = windowId == currentWindowId,
                            canClose = window.canClose,
                            canUndock = window.canUndock,
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
                        .backgroundOverlay()
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
    canUndock: Boolean,
    onClose: () -> Unit,
    onSelected: () -> Unit,
    onDragStart: () -> Unit,
    onDragEnd: () -> Unit,
    onDrag: (change: PointerInputChange) -> Unit,
) {
    val density = LocalDensity.current

    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()

//    var background = if (selected) TabActive else TabInactive
//
//    background = when {
//        hovered -> background.lightness(DefaultColorLightness.hovered)
//        pressed -> background.lightness(DefaultColorLightness.pressed)
//        else -> background
//    }

    val elevation = when {
        selected -> LocalTheme.current.elevationHigh
        pressed -> LocalTheme.current.elevationNone
        else -> LocalTheme.current.elevationLow
    }

    val textStyle = LocalAppTextStyles.current.bodyMedium.copy(primaryColor = TabTextActive)
    val iconSize = with(density) { (textStyle.style.fontSize * 1.4f).toDp() }

    Row(
        modifier = modifier
            .pointerInput(canUndock) {
                detectDragGestures(
                    onDragStart = {
                        if (canUndock) onDragStart()
                    },
                    onDragEnd = {
                        if (canUndock) onDragEnd()
                    },
                    onDrag = { change, _ ->
                        if (canUndock) onDrag(change)
                    },
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
            .surface(elevation)
            .applyIf(selected || pressed || hovered) {
                bloomBorders()
            }
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppText(
            text = title,
            style = textStyle,
            hovered = hovered,
            pressed = pressed,
        )
        Spacer(Modifier.weight(1f))
        if (canClose) {
            Image(
                modifier = Modifier
                    .size(iconSize)
                    .clickable(onClick = onClose),
                imageVector = IconX,
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                colorFilter = ColorFilter.tint(White)
            )
        }
    }
}
