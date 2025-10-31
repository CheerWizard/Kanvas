package com.cws.kanvas.config

import kotlinx.serialization.Serializable

@Serializable
data class WindowConfig(
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    val title: String
)