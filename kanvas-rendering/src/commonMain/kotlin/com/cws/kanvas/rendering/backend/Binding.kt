package com.cws.kanvas.rendering.backend

expect enum class BindingType {
    UNIFORM_BUFFER,
    STORAGE_BUFFER,
    TEXTURE,
    SAMPLER,
}

data class Binding(
    var type: BindingType,
    var shaderStages: Int,
    var set: Int,
    var binding: Int,
    var count: Int = 1,
)
