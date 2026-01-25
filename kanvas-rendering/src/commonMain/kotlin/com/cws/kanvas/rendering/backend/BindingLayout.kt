package com.cws.kanvas.rendering.backend

expect enum class BindingType {
    UNIFORM_BUFFER,
    STORAGE_BUFFER,
    TEXTURE,
    SAMPLER,
}

interface BindingResource

data class Binding(
    var type: BindingType,
    var shaderStages: Int,
    var set: Int,
    var binding: Int,
    var count: Int = 1,
    var resource: BindingResource? = null
)
