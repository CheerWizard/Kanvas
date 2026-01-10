package com.cws.kanvas.core

import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.application
import com.cws.print.Print

abstract class GameApplication {

    init {
        init()
    }

    protected lateinit var gameLoop: GameLoop

    private fun init() {
        Print.install(Unit) {
            application(exitProcessOnExit = true) {
                onCreate()
                initGameLoop()
                GameView(
                    gameLoop = gameLoop,
                    onWindowClose = {
                        onDestroy()
                    }
                )
            }
        }
    }

    protected open fun onCreate() {
        // no-op
    }

    protected open fun ApplicationScope.onDestroy() {
        exitApplication()
    }

    private fun initGameLoop() {
        if (!::gameLoop.isInitialized) {
            gameLoop = GameLoop(Unit)
        }
    }

}