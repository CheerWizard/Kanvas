package com.cws.kanvas.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier

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

    private fun initGameLoop() {
        if (!::gameLoop.isInitialized) {
            gameLoop = GameLoop(applicationContext)
        }
    }

}