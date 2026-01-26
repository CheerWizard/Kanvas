package com.cws.kanvas.rendering.backend

import com.cws.std.memory.NativeBuffer

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
    var sourceText: String = "",
    var sourceSpirv: NativeBuffer? = null,
    var sourceSpirvSize: Long = 0,
)

expect class ShaderHandle

expect class Shader(context: RenderContext, info: ShaderInfo) : Resource<ShaderHandle> {
    override fun onCreate()
    override fun onDestroy()
}
