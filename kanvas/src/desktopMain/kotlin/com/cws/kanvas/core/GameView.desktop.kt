package com.cws.kanvas.core

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameView(
    gameLoop: GameLoop,
    onWindowClose: () -> Unit,
) {
    val windowConfig = gameLoop.config.window

    val composeWindow = rememberWindowState(
        position = WindowPosition(windowConfig.x.dp, windowConfig.y.dp),
        width = windowConfig.width.dp,
        height = windowConfig.height.dp,
    )

    var window by remember { mutableStateOf<Window?>(null) }
    var windowBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(window) {
        window?.onBitmapChanged = { windowBitmap = it }
    }

    DisposableEffect(Unit) {
        gameLoop.onWindowCreated = { window = it }
        gameLoop.startLoop()
        onDispose {
            gameLoop.stopLoop()
        }
    }

    LaunchedEffect(composeWindow) {
        window?.composeState = composeWindow
    }

    Window(
        state = composeWindow,
        onCloseRequest = {
            window?.onWindowClose()
            onWindowClose()
        },
        onPreviewKeyEvent = { window?.onKeyEvent(it) ?: false }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onPointerEvent(PointerEventType.Press) {
                    it.button?.let { btn ->
                        window?.onMousePress(btn)
                    }
                }
                .onPointerEvent(PointerEventType.Release) {
                    it.button?.let { btn ->
                        window?.onMouseRelease(btn)
                    }
                }
                .onPointerEvent(PointerEventType.Move) {
                    val (x, y) = it.changes.first().position
                    window?.onMouseMove(x, y)
                }
                .onPointerEvent(PointerEventType.Scroll) {
                    val (x, y) = it.changes.first().scrollDelta
                    window?.onMouseScroll(x, y)
                }
        ) {
            windowBitmap?.let { bitmap ->
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawImage(bitmap)
                }
            }
            gameLoop.uiContent.invoke(this)
        }
    }
}