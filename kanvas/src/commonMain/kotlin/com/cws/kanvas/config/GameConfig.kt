package com.cws.kanvas.config

import kotlinx.serialization.Serializable

@Serializable
data class GameConfig(
    val window: WindowConfig,
)
