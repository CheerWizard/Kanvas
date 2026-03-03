@file:OptIn(ExperimentalForeignApi::class)

package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.*
import com.cws.print.LogLevel
import com.cws.print.Print
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKStringFromUtf8
import vk.LogBridge_init
import vk.ResultBridge_init
import vk.VkContextInfo
import vk.VkContext_create
import kotlin.native.internal.NativePtr

actual typealias Surface = NativePtr

actual class RenderContext actual constructor(
    info: ContextInfo,
    private val surface: Surface?,
) : Resource<RenderContextHandle, ContextInfo>(info) {

    init {
        LogBridge_init(staticCFunction { level, tag, msg, exception ->
            Print.log(
                level,
                tag?.toKStringFromUtf8() ?: "RenderContext",
                msg?.toKStringFromUtf8().orEmpty(),
                exception?.toKStringFromUtf8()
            )
        })
        ResultBridge_init(staticCFunction { result ->
            Print.log(LogLevel.DEBUG, "RenderContext", "VkResult received - $result")
        })
    }

    actual override fun onCreate() {
        info.pack()?.buffer?.let { info ->
            val contextInfo = info.reinterpret<VkContextInfo>()
            handle = RenderContextHandle(VkContext_create())
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