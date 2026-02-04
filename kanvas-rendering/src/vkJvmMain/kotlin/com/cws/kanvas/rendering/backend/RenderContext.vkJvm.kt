package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.*
import com.cws.print.LogLevel
import com.cws.print.Print
import com.cws.print.toLogLevel
import com.cws.std.memory.NativeString

actual class RenderContext actual constructor(
    info: ContextInfo,
    private val surface: Any?,
) : Resource<RenderContextHandle, ContextInfo>(info) {

    init {
        VK.LogBridge_callback { level, tag, msg, exceptionMsg ->
            Print.log(level, tag, msg, exceptionMsg)
        }
        VK.ResultBridge_callback { result ->
            Print.log(LogLevel.DEBUG, "RenderContext", "VkResult received - $result")
        }
    }

    actual override fun onCreate() {
        info.pack()?.buffer?.let { info ->
            handle = RenderContextHandle(VK.VkContext_create(surface, info))
        }
    }

    actual override fun onDestroy() {
        handle?.let {
            VK.VkContext_destroy(it.value)
        }
    }

    actual override fun setInfo() {
        handle?.let { handle ->
            info.pack()?.buffer?.let { info ->
                VK.VkContext_setInfo(handle.value, info)
            }
        }
    }

    actual fun wait() {
        handle?.let {
            VK.VkContext_wait(it.value)
        }
    }

    actual fun resize(width: Int, height: Int) {
        handle?.let {
            VK.VkContext_resize(it.value, width, height)
        }
    }

    actual fun setSurface(surface: Any?) {
        handle?.let {
            VK.VkContext_setSurface(it.value, surface)
        }
    }

    actual fun getRenderTarget(): RenderTarget {
        return RenderTarget(this, RenderTargetHandle(VK.VkContext_getRenderTarget(handle?.value ?: 0)))
    }

    actual fun beginFrame(frame: Int) {
        handle?.let {
            VK.VkContext_beginFrame(it.value, frame)
        }
    }

    actual fun endFrame(frame: Int) {
        handle?.let {
            VK.VkContext_endFrame(it.value, frame)
        }
    }

    actual fun getPrimaryCommandBuffer(frame: Int): CommandBuffer? {
        val handle = handle?.value ?: return null
        return CommandBuffer(
            this,
            CommandBufferHandle(VK.VkContext_getPrimaryCommandBuffer(handle, frame)),
        )
    }

    actual fun getSecondaryCommandBuffer(): CommandBuffer? {
        val handle = handle?.value ?: return null
        return CommandBuffer(
            this,
            CommandBufferHandle(VK.VkContext_getSecondaryCommandBuffer(handle)),
        )
    }

}