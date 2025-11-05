package com.cws.kanvas.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.cws.kanvas.audio.AudioPlayer
import com.cws.kanvas.audio.AudioRecorder
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.sensor.InputSensorManager

abstract class GameActivity : ComponentActivity() {

    protected lateinit var gameLoop: GameLoop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGameLoop()
        enableEdgeToEdge()
        setContent {
            GameView(
                modifier = Modifier.fillMaxSize(),
                gameLoop = gameLoop,
            )
        }
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
            inputSensorManager = InputSensorManager(applicationContext),
            audioPlayer = AudioPlayer(),
            audioRecorder = AudioRecorder(),
        )
    }

}