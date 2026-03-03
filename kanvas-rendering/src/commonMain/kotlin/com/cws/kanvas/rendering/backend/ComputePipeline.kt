package com.cws.kanvas.rendering.backend

expect class ComputePipeline(context: RenderContext, info: ComputePipelineInfo) : Resource<ComputePipelineHandle, ComputePipelineInfo> {
    override fun onCreate()
    override fun onDestroy()
    override fun setInfo()
}