package com.cws.kanvas.assetc.docking.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
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
import com.cws.kanvas.assetc.core.LocalDockController
import com.cws.kanvas.assetc.core.LocalTheme
import com.cws.kanvas.assetc.docking.logic.DockSpaceState
import com.cws.kanvas.assetc.docking.logic.DockWindowState
import kanvas.kanvas_assetc_app.generated.resources.Res
import kanvas.kanvas_assetc_app.generated.resources.ic_kanvas
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.painterResource

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
    id: String,
    title: String,
    dockSpace: DockSpaceState? = null,
    onClose: () -> Unit = {},
    titleBar: @Composable RowScope.(windowState: DockWindowState) -> Unit = {},
    content: @Composable () -> Unit,
) {
    val dockController = LocalDockController.current

    val restored by dockController.restored.collectAsState()
    var state by remember { mutableStateOf<DockWindowState?>(null) }

    LaunchedEffect(restored, content, onClose) {
        if (restored) {
            val dockWindowState = dockController.getOrAdd(
                DockWindowState(
                    id = id,
                    title = title,
                    dockSpace = dockSpace,
                )
            )
            dockWindowState.content = content
            dockWindowState.onClose = onClose
            state = dockWindowState
        }
    }

    state?.let { state ->
        DisposableEffect(Unit) {
            onDispose {
                dockController.remove(state)
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
                    .collect { dockController.saveState() }
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
                onCloseRequest = onClose,
                visible = state.visible,
                undecorated = true,
                icon = painterResource(Res.drawable.ic_kanvas),
            ) {
                var layoutCoords by remember { mutableStateOf<LayoutCoordinates?>(null) }
                var windowCoords by remember { mutableStateOf<WindowCoords?>(null) }

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            color = LocalTheme.current.background,
                        )
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
