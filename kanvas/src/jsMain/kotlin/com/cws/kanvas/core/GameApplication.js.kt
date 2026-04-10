package com.cws.kanvas.core

import com.cws.kanvas.event.EventListener
import com.cws.print.JsPrintContext
import com.cws.print.Print

abstract class GameApplication : EventListener {

    init {
        init()
    }

    protected lateinit var gameLoop: GameLoop
    private lateinit var window: Window

    private fun init() {
        Print.install(JsPrintContext()) {
            GameView(gameLoop)
            gameLoop.onWindowCreated = { window ->
                this.window = window
                window.addEventListener(this)
            }
        }
    }

}