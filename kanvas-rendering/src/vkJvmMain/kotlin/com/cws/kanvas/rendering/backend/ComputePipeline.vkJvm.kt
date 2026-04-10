package com.cws.kanvas.shaderc.backend

import com.cws.kanvas.rendering.backend.Resource
import com.cws.kanvas.vk.VK

actual class ComputePipeline actual constructor(
    private val context: RenderContext,
    info: ComputePipelineInfo
) : Resource<ComputePipelineHandle, ComputePipelineInfo>(info) {

    actual override fun onCreate() {
        context.handle?.value?.let { ctx ->
            info.pack()?.buffer?.let { info ->
                handle = ComputePipelineHandle(VK.VkComputePipe_create(ctx, info))
            }
        }
    }

    actual override fun onDestroy() {
        handle?.value?.let { handle ->
            VK.VkComputePipe_destroy(handle)
        }
    }

    actual override fun setInfo() {
        handle?.value?.let { handle ->
            info.pack()?.buffer?.let { info ->
                VK.VkComputePipe_setInfo(handle, info)
            }
        }
    }

}