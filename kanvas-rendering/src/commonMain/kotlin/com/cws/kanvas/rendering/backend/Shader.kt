package com.cws.kanvas.rendering.backend

expect class Shader(context: RenderContext, info: ShaderInfo) : Resource<ShaderHandle, ShaderInfo> {
    override fun onCreate()
    override fun onDestroy()
    override fun setInfo()
}
