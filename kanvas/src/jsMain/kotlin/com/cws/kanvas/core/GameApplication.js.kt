package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioPlayer
import com.cws.kanvas.audio.AudioRecorder
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.event.EventListener
import com.cws.kanvas.sensor.InputSensorManager
import com.cws.printer.Printer

abstract class GameApplication : EventListener {

    init {
        init()
    }

    protected lateinit var gameLoop: GameLoop
    private lateinit var window: Window

    private fun init() {
        Printer.init()
        onCreate()
        initGameLoop()
        GameView(gameLoop)
        gameLoop.onWindowCreated = { window ->
            this.window = window
            window.addEventListener(this)
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

    protected abstract fun provideGame(): Game
    protected abstract fun provideGameConfig(): GameConfig

    private fun initGameLoop() {
        if (!::gameLoop.isInitialized) {
            gameLoop = GameLoop(
                config = provideGameConfig(),
                engine = initEngine(),
                game = provideGame(),
            )
        }
    }

    private fun initEngine(): Engine {
        return Engine(
            inputSensorManager = InputSensorManager(),
            audioPlayer = AudioPlayer(),
            audioRecorder = AudioRecorder(),
        )
    }

}