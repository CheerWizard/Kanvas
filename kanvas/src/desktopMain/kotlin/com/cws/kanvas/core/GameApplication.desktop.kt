package com.cws.kanvas.core

import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.application
import com.cws.kanvas.audio.AudioOutputStream
import com.cws.kanvas.audio.AudioInputStream
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.sensor.InputSensorManager
import com.cws.printer.Printer

abstract class GameApplication {

    init {
        init()
    }

    protected lateinit var gameLoop: GameLoop

    private fun init() {
        Printer.init()
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

    protected open fun onCreate() {
        // no-op
    }

    protected open fun ApplicationScope.onDestroy() {
        exitApplication()
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
            audioPlayer = AudioOutputStream(),
            audioRecorder = AudioInputStream(),
        )
    }

}