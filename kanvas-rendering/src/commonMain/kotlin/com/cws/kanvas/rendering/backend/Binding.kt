package com.cws.kanvas.rendering.backend

expect enum class BindingType {
    UNIFORM_BUFFER,
    STORAGE_BUFFER,
    TEXTURE,
    SAMPLER,
}

data class Binding(
    val type: BindingType,
    val slot: UInt,
    val shaderStages: UInt,
)
