@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.GPUExtent3D
import com.cws.kanvas.wgpu.gpu.GPUTextureAspect
import com.cws.kanvas.wgpu.gpu.GPUTextureDescriptor
import com.cws.kanvas.wgpu.gpu.GPUTextureFormat
import com.cws.kanvas.wgpu.gpu.GPUTextureViewDescriptor
import com.cws.kanvas.wgpu.gpu.all
import com.cws.kanvas.wgpu.gpu.jsArrayOf
import com.cws.kanvas.wgpu.gpu.rgba8unorm
import com.cws.kanvas.wgpu.gpu.toGPUSize32
import com.cws.std.memory.NativeBuffer

actual class Texture actual constructor(
    private val context: RenderContext,
    info: TextureInfo
) : Resource<TextureHandle, TextureInfo>(info) {

    actual override fun onCreate() {
        context.call { device ->
            val texture = device.createTexture(GPUTextureDescriptor(
                label = "${info.name.value}-Texture",
                size = GPUExtent3D(info.width, info.height, info.depth),
                format = info.format.jsValue,
                usage = info.usages,
                dimension = info.type.textureDimension,
                mipLevelCount = info.mips,
                sampleCount = info.samples.toGPUSize32(),
                viewFormats = jsArrayOf(GPUTextureFormat.rgba8unorm),
            ))

            val view = texture.createView(GPUTextureViewDescriptor(
                label = "${info.name.value}-TextureView",
                format = info.format.jsValue,
                dimension = info.type.viewDimension,
                aspect = GPUTextureAspect.all,
                baseMipLevel = info.baseMip,
                mipLevelCount = info.mips,
                baseArrayLayer = 0,
                arrayLayerCount = 1,
            ))

            handle = TextureHandle(texture, view)
        }
    }

    actual override fun onDestroy() {
        handle?.texture?.destroy()
    }

    actual override fun setInfo() {
        onDestroy()
        onCreate()
    }

    actual fun write(
        frame: Int,
        data: NativeBuffer,
        srcOffset: Int,
        destOffset: Int,
        size: Int
    ) {
        handle?.texture?.let { texture ->
        }
    }

    actual fun read(
        frame: Int,
        data: NativeBuffer,
        srcOffset: Int,
        destOffset: Int,
        size: Int
    ) {
        handle?.texture?.let { texture ->
        }
    }
}