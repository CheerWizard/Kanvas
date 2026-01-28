package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK
import com.cws.kanvas.vk.VkShaderInfo
import com.cws.std.memory.CString

actual typealias ShaderHandle = Long

actual class Shader actual constructor(
    private val context: RenderContext,
    actual val info: ShaderInfo
) : Resource<ShaderHandle>() {

    private val vkInfo = VkShaderInfo(
        name = CString(info.name),
        entryPoint = CString(info.entryPoint),
        spirvCode = info.sourceSpirv,
        spirvCodeSize = info.sourceSpirvSize,
        bindingLayouts = LongArray(info.bindingLayouts.size) { i ->
            info.bindingLayouts[i].handle ?: 0
        },
        bindingLayoutsCount = info.bindingLayouts.size.toLong(),
    )

    actual override fun onCreate() {
        context.handle?.let { ctx ->
            vkInfo.pack()?.buffer?.let { info ->
                handle = VK.VkShader_create(ctx, info)
            }
        }
    }

    actual override fun onDestroy() {
        handle?.let {
            VK.VkShader_destroy(it)
        }
    }

    actual fun update() {
        handle?.let { handle ->
            vkInfo.release()
            vkInfo.name = CString(info.name)
            vkInfo.entryPoint = CString(info.entryPoint)
            vkInfo.spirvCode = info.sourceSpirv
            vkInfo.spirvCodeSize = info.sourceSpirvSize
            repeat(info.bindingLayouts.size) { i ->
                vkInfo.bindingLayouts[i] = info.bindingLayouts[i].handle ?: 0
            }
            vkInfo.bindingLayoutsCount = info.bindingLayouts.size.toLong()
            vkInfo.pack()?.let { info ->
                VK.VkShader_update(handle, info.buffer)
            }
        }
    }

}