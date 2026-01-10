package com.cws.kanvas.core

import androidx.compose.runtime.Composable
import com.cws.kanvas.event.EventListener

abstract class GameModule : EventListener {
    lateinit var engine: Engine
    lateinit var window: Window

    abstract fun onCreate()
    abstract fun onDestroy()
    abstract fun onUpdate(dtMillis: Float)
    abstract fun onRender(dtMillis: Float)
    @Composable
    abstract fun onRenderUI()
}