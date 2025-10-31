package com.cws.kanvas.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.top
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Canvas
import org.jetbrains.compose.web.renderComposable

const val KANVAS_ROOT = "kanvas"
const val KANVAS_RUNTIME = "kanvas-runtime"
const val KANVAS_UI = "kanvas-ui"

@OptIn(ExperimentalComposeUiApi::class)
fun GameView(
    gameLoop: GameLoop,
) {
    // TODO handle GameConfig
    // GameLoop canvas
    renderComposable(KANVAS_ROOT) {
        Canvas(attrs = {
            id(KANVAS_RUNTIME)
            style {
                position(Position.Absolute)
                top(0.px)
                left(0.px)
                width(100.percent)
                height(100.percent)
                property("z-index", "0")
            }
            ref {
                gameLoop.startLoop()
                onDispose { gameLoop.stopLoop() }
            }
        })
    }
    // UI canvas
    CanvasBasedWindow(title = "UI", canvasElementId = KANVAS_UI) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            gameLoop.uiContent.invoke(this)
        }
    }
}