package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.*
import com.cws.std.memory.CString

actual typealias RenderContextHandle = Long

actual class RenderContext actual constructor(info: RenderContextInfo) : Resource<RenderContextHandle>() {

    private val vkInfo = VkContextInfo(
        applicationName = CString(info.appName),
        engineName = CString(info.engineName),
        width = info.width,
        height = info.height,
        frameCount = info.frameCount,
    )
    private val surface = info.surface

    actual override fun onCreate() {
        vkInfo.pack()?.let {
            handle = VK.VkContext_create(surface, it.buffer)
        }
    }

    actual override fun onDestroy() {
        handle?.let {
            VK.VkContext_destroy(it)
        }
    }

    actual fun wait() {
        handle?.let {
            VK.VkContext_wait(it)
        }
    }

    actual fun resize(width: Int, height: Int) {
        handle?.let {
            VK.VkContext_resize(it, width, height)
        }
    }

    actual fun getRenderTarget(): RenderTarget {
        return RenderTarget(this, VK.VkContext_getRenderTarget(handle!!))
    }

    actual fun beginFrame(frame: Int) {
        handle?.let {
            VK.VkContext_beginFrame(it, frame)
        }
    }

    actual fun endFrame(frame: Int) {
        handle?.let {
            VK.VkContext_endFrame(it, frame)
        }
    }

}