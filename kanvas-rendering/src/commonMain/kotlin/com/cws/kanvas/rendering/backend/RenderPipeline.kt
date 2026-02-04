package com.cws.kanvas.rendering.backend

expect class RenderPipeline(context: RenderContext, info: PipelineInfo) : Resource<RenderPipelineHandle, PipelineInfo> {
    override fun onCreate()
    override fun onDestroy()
    override fun setInfo()
}