package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK

actual class BindingLayout actual constructor(
    private val context: RenderContext,
    info: BindingInfo,
) : Resource<BindingLayoutHandle, BindingInfo>(info) {

    actual override fun onCreate() {
        context.handle?.let { ctx ->
            info.pack()?.let {
                VK.VkBindingLayout_create(ctx.value, it.buffer)
            }
        }
    }

    actual override fun onDestroy() {
        handle?.let {
            VK.VkBindingLayout_destroy(it.value)
        }
    }

    actual override fun setInfo() {
        handle?.let {
            info.pack()?.buffer?.let { info ->
                VK.VkBindingLayout_setInfo(it.value, info)
            }
        }
    }

}
