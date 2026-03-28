package com.cws.kanvas.assetc.docking.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.cws.kanvas.assetc.docking.LocalDockController
import com.cws.kanvas.assetc.ui.LocalTheme
import com.cws.kanvas.assetc.docking.logic.DockSpaceState
import com.cws.kanvas.assetc.docking.logic.DockWindowState
import com.cws.kanvas.assetc.ui.applyIf
import com.cws.kanvas.assetc.ui.backgroundOverlay
import com.cws.kanvas.assetc.ui.bloomBorders
import com.cws.kanvas.assetc.ui.styling.surface
import kanvas.kanvas_assetc_app.generated.resources.KanvasLogo
import kanvas.kanvas_assetc_app.generated.resources.Res
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.painterResource
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetAdapter
import java.awt.dnd.DropTargetDragEvent
import java.awt.dnd.DropTargetDropEvent
import java.awt.dnd.DropTargetEvent
import java.io.File

data class WindowCoords(
    val awtX: Int,
    val awtY: Int,
    val coords: LayoutCoordinates?,
) {

    fun toScreenPos(position: Offset): Offset {
        val windowPos = coords?.localToWindow(position) ?: return position
        return Offset(awtX + windowPos.x, awtY + windowPos.y)
    }

}

val LocalWindowCoords = staticCompositionLocalOf<WindowCoords?> { null }

@OptIn(FlowPreview::class)
@Composable
fun DockWindow(
    modifier: Modifier = Modifier,
    state: DockWindowState,
    enableDragAndDrop: Boolean = false,
    onClose: () -> Unit = state.onClose,
    onDragAndDrop: (files: List<File>) -> Unit = {},
    titleBar: @Composable (state: DockWindowState) -> Unit,
    content: @Composable () -> Unit,
) {
    val windowController = LocalDockController.current

    val restored by windowController.restored.collectAsState()
    var currentState by remember { mutableStateOf<DockWindowState?>(state) }

    LaunchedEffect(restored, content, onClose) {
        if (restored) {
            val dockWindowState = windowController.getOrAdd(
                DockWindowState(
                    id = state.id,
                    title = state.title,
                    dockSpace = state.dockSpace,
                )
            )
            dockWindowState.content = content
            dockWindowState.onClose = onClose
            currentState = dockWindowState
        }
    }

    currentState?.let { state ->
        DisposableEffect(Unit) {
            onDispose {
                windowController.remove(state)
            }
        }

        if (!state.docked) {
            val windowState = rememberWindowState(
                isMinimized = state.minimized,
                placement = if (state.maximized) WindowPlacement.Maximized else WindowPlacement.Floating,
                position = WindowPosition(state.position.x, state.position.y),
                size = state.size,
            )

            LaunchedEffect(windowState.position) {
                if (windowState.position.x != state.position.x || windowState.position.y != state.position.y) {
                    state.position = DpOffset(windowState.position.x, windowState.position.y)
                }
            }

            LaunchedEffect(windowState.size) {
                if (windowState.size != state.size) {
                    state.size = windowState.size
                }
            }

            LaunchedEffect(Unit) {
                snapshotFlow { windowState.size }
                    .distinctUntilChanged()
                    .debounce(400)
                    .collect { windowController.saveState() }
            }

            LaunchedEffect(state.minimized) {
                windowState.isMinimized = state.minimized
            }

            LaunchedEffect(windowState.isMinimized) {
                state.minimized = windowState.isMinimized
            }

            LaunchedEffect(state.maximized) {
                windowState.placement = if (state.maximized) {
                    WindowPlacement.Maximized
                } else {
                    WindowPlacement.Floating
                }
            }

            Window(
                state = windowState,
                title = state.title,
                onCloseRequest = state.onClose,
                visible = state.visible,
                undecorated = true,
                icon = painterResource(Res.drawable.KanvasLogo),
            ) {
                var layoutCoords by remember { mutableStateOf<LayoutCoordinates?>(null) }
                var windowCoords by remember { mutableStateOf<WindowCoords?>(null) }
                var hoverDragAndDrop by remember { mutableStateOf(false) }

                val awtWindow = window

                if (enableDragAndDrop) {
                    DisposableEffect(awtWindow) {
                        val component = awtWindow.contentPane.components.firstOrNull()
                        val dropTarget = DropTarget(component, object : DropTargetAdapter() {

                            override fun dragEnter(e: DropTargetDragEvent) {
                                hoverDragAndDrop = true
                            }

                            override fun dragExit(e: DropTargetEvent) {
                                hoverDragAndDrop = false
                            }

                            override fun dragOver(e: DropTargetDragEvent) {
                                hoverDragAndDrop = true
                                e.acceptDrag(DnDConstants.ACTION_COPY)
                            }

                            override fun drop(e: DropTargetDropEvent) {
                                hoverDragAndDrop = false
                                try {
                                    e.acceptDrop(DnDConstants.ACTION_COPY)
                                    val transferable = e.transferable
                                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                                        val files = transferable.getTransferData(
                                            DataFlavor.javaFileListFlavor
                                        ) as List<File>
                                        onDragAndDrop(files)
                                        e.dropComplete(true)
                                    } else {
                                        e.dropComplete(false)
                                    }
                                } catch (_: Exception) {
                                    e.dropComplete(false)
                                }
                            }
                        })

                        component?.dropTarget = dropTarget

                        onDispose {
                            component?.dropTarget = null
                        }
                    }
                }

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .surface(LocalTheme.current.elevationLow)
                        .bloomBorders()
                        .onGloballyPositioned {
                            layoutCoords = it
                            windowCoords = WindowCoords(
                                awtX = window.x,
                                awtY = window.y,
                                coords = it,
                            )
                        },
                ) {
                    CompositionLocalProvider(
                        LocalWindowCoords provides windowCoords
                    ) {
                        DockWindowTitleBar(
                            state = state,
                            layoutCoords = layoutCoords,
                            content = titleBar,
                        )
                        DockWindow(
                            modifier = Modifier
                                .applyIf(hoverDragAndDrop) {
                                    backgroundOverlay()
                                },
                            state = state,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DockWindow(
    modifier: Modifier = Modifier,
    state: DockWindowState,
) {
    when (val dockSpace = state.dockSpace) {
        null -> Box(modifier = modifier) {
            state.content()
        }
        is DockSpaceState.Host -> DockHost(state = state, dockSpace = dockSpace)
    }
}
