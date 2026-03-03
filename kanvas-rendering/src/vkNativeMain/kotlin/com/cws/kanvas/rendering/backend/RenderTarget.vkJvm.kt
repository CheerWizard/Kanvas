package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK
import com.cws.std.memory.NativeBuffer

actual class RenderTarget actual constructor(
    private val context: RenderContext,
    info: RenderTargetInfo,
) : Resource<RenderTargetHandle, RenderTargetInfo>(info) {

    actual override fun onCreate() {
        context.handle?.value?.let { ctx ->
            info.pack()?.buffer?.let { info ->
                handle = RenderTargetHandle(VK.VkRenderTarget_create(ctx, info))
            }
        }
    }

    actual override fun onDestroy() {
        handle?.value?.let { handle ->
            VK.VkRenderTarget_destroy(handle)
        }
    }

    actual override fun setInfo() {
        handle?.value?.let { handle ->
            info.pack()?.buffer?.let { info ->
                VK.VkRenderTarget_setInfo(handle, info)
            }
        }
    }

    actual fun resize(width: Int, height: Int) {
        handle?.value?.let { handle ->
            info.width = width
            info.height = height
            VK.VkRenderTarget_resize(handle, width, height)
        }
    }

    actual constructor(
        renderContext: RenderContext,
        handle: RenderTargetHandle
    ) : this(renderContext, RenderTargetInfo(buffer = NativeBuffer(handle.value, RenderTargetInfo.SIZEOF)))

}
