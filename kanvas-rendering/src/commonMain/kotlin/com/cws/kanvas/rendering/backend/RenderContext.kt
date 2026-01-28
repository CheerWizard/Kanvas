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

    fun wait()
    fun resize(width: Int, height: Int)
    fun getRenderTarget(): RenderTarget
    fun beginFrame(frame: Int)
    fun endFrame(frame: Int)
}
