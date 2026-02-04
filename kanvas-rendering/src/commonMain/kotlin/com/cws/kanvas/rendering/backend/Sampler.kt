package com.cws.kanvas.rendering.backend

expect class Sampler(context: RenderContext, info: SamplerInfo) : Resource<SamplerHandle, SamplerInfo> {
    override fun onCreate()
    override fun onDestroy()
    override fun setInfo()
}
