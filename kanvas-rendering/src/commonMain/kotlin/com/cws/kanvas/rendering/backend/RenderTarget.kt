package com.cws.kanvas.rendering.backend

expect class RenderTarget(context: RenderContext, info: RenderTargetInfo) : Resource<RenderTargetHandle, RenderTargetInfo> {
    override fun onCreate()
    override fun onDestroy()
    override fun setInfo()
    fun resize(width: Int, height: Int)
    constructor(renderContext: RenderContext, handle: RenderTargetHandle)
}
