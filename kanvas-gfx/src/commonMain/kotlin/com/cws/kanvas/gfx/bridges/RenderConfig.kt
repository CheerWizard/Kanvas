package com.cws.kanvas.gfx.bridges

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class RenderConfig(
    @Transient
    val nativeWindow: Any? = null,
    val width: Int,
    val height: Int,
    val canvasID: String = "",
)
