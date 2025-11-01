package com.cws.kanvas.config

import com.cws.kanvas.gfx.bridges.RenderConfig
import kotlinx.serialization.Serializable

@Serializable
data class GameConfig(
    val window: WindowConfig,
    val renderConfig: RenderConfig,
)
