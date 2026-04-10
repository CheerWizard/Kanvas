package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK

actual class Sampler actual constructor(
    private val context: RenderContext,
    info: SamplerInfo,
) : Resource<SamplerHandle, SamplerInfo>(info) {

    actual override fun onCreate() {
        context.handle?.value?.let { ctx ->
            info.pack()?.buffer?.let { info ->
                handle = SamplerHandle(VK.VkSamplerResource_create(ctx, info))
            }
        }
    }

    actual override fun onDestroy() {
        handle?.value?.let { handle ->
            VK.VkSamplerResource_destroy(handle)
        }
    }

    actual override fun setInfo() {
        handle?.value?.let { handle ->
            info.pack()?.buffer?.let { info ->
                VK.VkSamplerResource_setInfo(handle, info)
            }
        }
    }

}