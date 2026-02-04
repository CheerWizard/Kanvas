@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.GPUCompilationMessageType
import com.cws.kanvas.wgpu.gpu.GPUShaderModuleDescriptor
import com.cws.kanvas.wgpu.gpu.error
import com.cws.kanvas.wgpu.gpu.info
import com.cws.kanvas.wgpu.gpu.warning
import com.cws.print.Print
import kotlin.js.collections.toList

actual class Shader actual constructor(
    private val context: RenderContext,
    info: ShaderInfo
) : Resource<ShaderHandle, ShaderInfo>(info) {

    actual override fun onCreate() {
        context.call { device ->
            val shader = device.createShaderModule(GPUShaderModuleDescriptor(
                label = info.name.value,
                code = info.textCode,
            ))

            shader.getCompilationInfoAsync().then { compilationInfo ->
                compilationInfo.messages.toList().forEach { message ->
                    val shaderName = info.name.value
                    val formatted = StringBuilder("$shaderName compilation info")

                    if (message.lineNum != 0.0) {
                        formatted.append("Line ${message.lineNum}:${message.linePos} - ")
                        formatted.appendLine(info.textCode.substring(message.offset.toInt(), message.length.toInt()))
                    }
                    formatted.append(message.message)

                    when (message.type) {
                        GPUCompilationMessageType.info -> Print.i(shaderName, formatted.toString())
                        GPUCompilationMessageType.warning -> Print.w(shaderName, formatted.toString())
                        GPUCompilationMessageType.error -> Print.e(shaderName, formatted.toString())
                    }
                }
            }
            handle = ShaderHandle(shader)
        }
    }

    actual override fun onDestroy() {
        // no-op
    }

    actual override fun setInfo() {
        onCreate()
    }

}