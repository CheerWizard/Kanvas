package com.cws.kanvas.core

import androidx.compose.runtime.Composable
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.event.EventListener

abstract class Game : EventListener {
    lateinit var engine: Engine
    lateinit var config: GameConfig

    abstract fun onCreate()
    abstract fun onDestroy()
    abstract fun onUpdate(dtMillis: Float)
    abstract fun onRender(dtMillis: Float)
    @Composable
    abstract fun onRenderUI()
}