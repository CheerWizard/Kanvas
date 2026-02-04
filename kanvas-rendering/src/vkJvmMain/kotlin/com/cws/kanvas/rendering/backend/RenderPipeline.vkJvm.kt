package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK

actual class RenderPipeline actual constructor(
    private val context: RenderContext,
    info: PipelineInfo
) : Resource<RenderPipelineHandle, PipelineInfo>(info) {

    actual override fun onCreate() {
        context.handle?.value?.let { ctx ->
            info.pack()?.buffer?.let { info ->
                handle = RenderPipelineHandle(VK.VkPipe_create(ctx, info))
            }
        }
    }

    actual override fun onDestroy() {
        handle?.value?.let { handle ->
            VK.VkPipe_destroy(handle)
        }
    }

    actual override fun setInfo() {
        handle?.value?.let { handle ->
            info.pack()?.buffer?.let { info ->
                VK.VkPipe_setInfo(handle, info)
            }
        }
    }

}