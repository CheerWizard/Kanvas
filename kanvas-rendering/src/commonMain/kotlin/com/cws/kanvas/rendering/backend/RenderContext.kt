package com.cws.kanvas.rendering.backend

data class RenderContextInfo(
    val width: Int,
    val height: Int,
    val frameCount: Int = 1,
    val surface: Any? = null,
    val appName: String = "",
    val engineName: String = "",
    val enableValidation: Boolean = false,
)

expect class RenderContextHandle

expect class RenderContext(info: RenderContextInfo) : Resource<RenderContextHandle> {
    override fun onCreate()
    override fun onDestroy()
}
