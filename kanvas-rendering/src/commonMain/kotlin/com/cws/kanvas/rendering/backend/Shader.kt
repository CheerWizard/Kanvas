package com.cws.kanvas.rendering.backend

expect enum class ShaderStage {
    VERTEX,
    FRAGMENT,
    COMPUTE,
    MESH,
    RAY_TRACING,
}

data class ShaderConfig(
    val name: String,
    val stage: ShaderStage,
    var entryPoint: String = "main",
    var source: String,
)

expect class ShaderHandle

expect class Shader(context: RenderContext, config: ShaderConfig) : Resource<ShaderHandle, ShaderConfig> {
    override fun onCreate()
    override fun onRelease()

    fun compile()
}
