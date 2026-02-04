package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK

actual class Shader actual constructor(
    private val context: RenderContext,
    info: ShaderInfo,
) : Resource<ShaderHandle, ShaderInfo>(info) {

    actual override fun onCreate() {
        context.handle?.value?.let { ctx ->
            info.pack()?.buffer?.let { info ->
                handle = ShaderHandle(VK.VkShader_create(ctx, info))
            }
        }
    }

    actual override fun onDestroy() {
        handle?.value?.let { handle ->
            VK.VkShader_destroy(handle)
        }
    }

    actual override fun setInfo() {
        handle?.value?.let { handle ->
            info.pack()?.buffer?.let { info ->
                VK.VkShader_setInfo(handle, info)
            }
        }
    }

}