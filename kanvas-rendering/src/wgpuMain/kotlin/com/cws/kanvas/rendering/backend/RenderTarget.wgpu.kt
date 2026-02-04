@file:OptIn(ExperimentalJsCollectionsApi::class, ExperimentalJsExport::class)

package com.cws.kanvas.rendering.backend

import com.cws.kanvas.math.Vec4
import com.cws.kanvas.math.w
import com.cws.kanvas.math.x
import com.cws.kanvas.math.y
import com.cws.kanvas.math.z
import com.cws.kanvas.wgpu.gpu.GPUColor
import com.cws.kanvas.wgpu.gpu.GPURenderPassColorAttachment
import com.cws.kanvas.wgpu.gpu.GPURenderPassDepthStencilAttachment
import com.cws.kanvas.wgpu.gpu.GPURenderPassDescriptor
import com.cws.std.memory.NativeBuffer

fun Vec4.toGPUColor() = GPUColor(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())

actual class RenderTarget actual constructor(
    private val context: RenderContext,
    info: RenderTargetInfo
) : Resource<RenderTargetHandle, RenderTargetInfo>(info) {

    actual override fun onCreate() {
        val depthView = info.depthAttachment.texture?.handle?.view
        handle = RenderTargetHandle(GPURenderPassDescriptor(
            label = info.name.value,
            colorAttachments = info.colorAttachments.list
                .filter { colorAttachment -> colorAttachment.texture?.handle?.view != null }
                .map { colorAttachment ->
                    GPURenderPassColorAttachment(
                        view = colorAttachment.texture!!.handle!!.view!!,
                        clearValue = colorAttachment.clearColor.toGPUColor(),
                    )
                }.asJsReadonlyArrayView(),
            depthStencilAttachment = if (depthView != null) {
                GPURenderPassDepthStencilAttachment(
                    view = depthView,
                    depthClearValue = info.depth.toFloat(),
                    depthReadOnly = info.depthAttachment.depthReadOnly,
                    stencilClearValue = info.stencilAttachment.stencilClearValue,
                    stencilReadOnly = info.stencilAttachment.stencilReadOnly,
                    // TODO expose storeOp and loadOp for depth and stencil
                )
            } else null,
        ))
    }

    actual override fun onDestroy() {
        // no-op
    }

    actual override fun setInfo() {
        onCreate()
    }

    actual fun resize(width: Int, height: Int) {
        info.width = width
        info.height = height

        info.colorAttachments.list.forEach { colorAttachment ->
            colorAttachment.texture?.let { texture ->
                texture.info.width = width
                texture.info.height = height
                texture.setInfo()
            }
        }

        info.depthAttachment.texture?.let { texture ->
            texture.info.width = width
            texture.info.height = height
            texture.setInfo()
        }

        info.stencilAttachment.texture?.let { texture ->
            texture.info.width = width
            texture.info.height = height
            texture.setInfo()
        }

        onDestroy()
        onCreate()
    }

    actual constructor(
        renderContext: RenderContext,
        handle: RenderTargetHandle
    ) : this(renderContext, RenderTargetInfo())

}