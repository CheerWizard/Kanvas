package com.cws.kanvas.rendering.backend

expect enum class ShaderStage {
    VERTEX,
    FRAGMENT,
    COMPUTE,
    MESH,
    RAY_TRACING,
}

data class ShaderInfo(
    val name: String,
    val stage: ShaderStage,
    var entryPoint: String = "main",
    var source: String,
)

expect class ShaderHandle

expect class Shader(context: RenderContext, config: ShaderInfo) : Resource<ShaderHandle, ShaderInfo> {
    override fun onCreate()
    override fun onRelease()

    fun compile()
}
