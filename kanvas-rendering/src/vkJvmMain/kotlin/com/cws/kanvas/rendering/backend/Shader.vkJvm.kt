package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK
import com.cws.kanvas.vk.VkShaderInfo
import com.cws.std.memory.CString

actual typealias ShaderHandle = Long

actual class Shader actual constructor(
    private val context: RenderContext,
    info: ShaderInfo
) : Resource<ShaderHandle>() {

    private val vkInfo = VkShaderInfo(
        name = CString(info.name),
        entryPoint = CString(info.entryPoint),
        spirvCode = info.sourceSpirv,
        spirvCodeSize = info.sourceSpirvSize,
    )

    actual override fun onCreate() {
        context.handle?.let { ctx ->
            vkInfo.pack()?.buffer?.let { info ->
                handle = VK.VkShader_create(ctx, info)
            }
        }
    }

    actual override fun onDestroy() {
        VK.VkShader_destroy(handle!!)
    }

}