package com.cws.kanvas.rendering.backend

expect class RenderContext(info: ContextInfo, surface: Any?) : Resource<RenderContextHandle, ContextInfo> {
    override fun onCreate()
    override fun onDestroy()
    override fun setInfo()
    fun wait()
    fun resize(width: Int, height: Int)
    fun setSurface(surface: Any?)
    fun getRenderTarget(): RenderTarget
    fun beginFrame(frame: Int)
    fun endFrame(frame: Int)
    fun getPrimaryCommandBuffer(frame: Int): CommandBuffer?
    fun getSecondaryCommandBuffer(): CommandBuffer?
}
