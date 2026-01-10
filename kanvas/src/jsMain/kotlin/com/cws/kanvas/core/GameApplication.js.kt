package com.cws.kanvas.core

import com.cws.kanvas.event.EventListener
import com.cws.print.Print

abstract class GameApplication : EventListener {

    init {
        init()
    }

    protected lateinit var gameLoop: GameLoop
    private lateinit var window: Window

    private fun init() {
        Print.install(Unit) {
            onCreate()
            initGameLoop()
            GameView(gameLoop)
            gameLoop.onWindowCreated = { window ->
                this.window = window
                window.addEventListener(this)
            }
        }
    }

    override fun onWindowClosed() {
        if (::window.isInitialized) {
            window.removeEventListener(this)
        }
        onDestroy()
    }

    protected open fun onCreate() {
        // no-op
    }

    protected open fun onDestroy() {
        // no-op
    }

    private fun initGameLoop() {
        if (!::gameLoop.isInitialized) {
            gameLoop = GameLoop(Unit)
        }
    }

}