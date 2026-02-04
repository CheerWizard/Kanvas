@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.GPUExtent3D
import com.cws.kanvas.wgpu.gpu.GPUIndexFormat
import com.cws.kanvas.wgpu.gpu.GPUOrigin3D
import com.cws.kanvas.wgpu.gpu.GPURenderPassEncoder
import com.cws.kanvas.wgpu.gpu.GPUTexelCopyBufferInfo
import com.cws.kanvas.wgpu.gpu.GPUTexelCopyTextureInfo
import com.cws.kanvas.wgpu.gpu.toGPUSize64
import com.cws.kanvas.wgpu.gpu.uint16
import com.cws.std.memory.NativeDataList

actual class CommandBuffer actual constructor(
    private val context: RenderContext,
    handle: CommandBufferHandle
) {
    actual var handle: CommandBufferHandle? = handle
    private var renderPass: GPURenderPassEncoder? = null

    actual fun reset() {
        // no-op
    }

    actual fun begin() {
        // no-op
    }

    actual fun end() {
        // no-op
    }

    actual fun beginRenderPass(
        renderTarget: RenderTargetHandle,
        colorAttachmentIndex: Int
    ) {
        val descriptor = renderTarget.value ?: return
        renderPass = handle?.value?.beginRenderPass(descriptor)
    }

    actual fun endRenderPass() {
        renderPass?.end()
    }

    actual fun setPipeline(pipeline: RenderPipeline) {
        renderPass?.let { renderPass ->
            pipeline.handle?.value?.let { pipeline ->
                renderPass.setPipeline(pipeline)
            }

            pipeline.info.vertexBuffer?.handle?.value?.let { vertexBuffer ->
                renderPass.setVertexBuffer(slot = pipeline.info.vertexBuffer!!.slot, buffer = vertexBuffer)
            }

            pipeline.info.indexBuffer?.handle?.value?.let { indexBuffer ->
                renderPass.setIndexBuffer(buffer = indexBuffer, indexFormat = GPUIndexFormat.uint16)
            }

            setViewport(pipeline.info.viewportX, pipeline.info.viewportY,
                pipeline.info.viewportWidth, pipeline.info.viewportHeight,
                pipeline.info.viewportMinDepth, pipeline.info.viewportMaxDepth)

            setScissor(pipeline.info.scissorX, pipeline.info.scissorY,
                pipeline.info.scissorWidth, pipeline.info.scissorHeight)

            val colorAttachment = pipeline.info.renderTarget?.info?.colorAttachments?.list?.firstOrNull()
            if (colorAttachment != null) {
                renderPass.setBlendConstant(colorAttachment.clearColor.toGPUColor())
            }

            renderPass.setStencilReference(pipeline.info.renderTarget?.info?.stencilAttachment?.stencilClearValue)

            pipeline.info.vertexShader?.info?.bindingLayouts?.list?.forEach { bindingLayout ->
                renderPass.setBindGroup(
                    index = bindingLayout.groupIndex,
                    bindGroup = bindingLayout.handle?.group,
                )
            }

            pipeline.info.fragmentShader?.info?.bindingLayouts?.list?.forEach { bindingLayout ->
                renderPass.setBindGroup(
                    index = bindingLayout.groupIndex,
                    bindGroup = bindingLayout.handle?.group,
                )
            }

            pipeline.info.geometryShader?.info?.bindingLayouts?.list?.forEach { bindingLayout ->
                renderPass.setBindGroup(
                    index = bindingLayout.groupIndex,
                    bindGroup = bindingLayout.handle?.group,
                )
            }
        }
    }

    actual fun setViewport(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        minDepth: Float,
        maxDepth: Float
    ) {
        renderPass?.setViewport(x.toInt(), y.toInt(), width.toInt(), height.toInt(), minDepth, maxDepth)
    }

    actual fun setScissor(x: Int, y: Int, width: Int, height: Int) {
        renderPass?.setScissorRect(x, y, width, height)
    }

    actual fun addSecondaryBuffer(secondaryBuffer: CommandBufferHandle) {
        // no-op
    }

    actual fun addSecondaryBuffers(secondaryBuffers: NativeDataList<CommandBufferHandle>) {
        // no-op
    }

    actual fun draw(
        vertices: Int,
        vertexOffset: Int,
        instances: Int,
        instanceOffset: Int
    ) {
        renderPass?.draw(vertices, instances, vertexOffset, instanceOffset)
    }

    actual fun drawIndexed(
        vertices: Int,
        vertexOffset: Int,
        indices: Int,
        indexOffset: Int,
        instances: Int,
        instanceOffset: Int
    ) {
        renderPass?.drawIndexed(indices, instances, indexOffset, vertexOffset, instanceOffset)
    }

    actual fun drawIndexedIndirect(
        indirectBuffer: BufferHandle,
        offset: Int,
        drawCount: Int
    ) {
        val buffer = indirectBuffer.value ?: return
        renderPass?.drawIndexedIndirect(buffer, offset.toGPUSize64())
    }

    actual fun copyBufferToBuffer(
        srcBuffer: BufferHandle,
        dstBuffer: BufferHandle,
        srcOffset: Int,
        dstOffset: Int,
        size: Int
    ) {
        val srcBuffer = srcBuffer.value ?: return
        val dstBuffer = dstBuffer.value ?: return
        handle?.value?.copyBufferToBuffer(srcBuffer, srcOffset.toGPUSize64(), dstBuffer, dstOffset.toGPUSize64(), size.toGPUSize64())
    }

    actual fun copyBufferToImage(
        srcBuffer: BufferHandle,
        dstImage: TextureHandle,
        dstMipLevel: Int,
        dstWidth: Int,
        dstHeight: Int,
        dstDepth: Int
    ) {
        val srcBuffer = srcBuffer.value ?: return
        val dstImage = dstImage.texture ?: return
        handle?.value?.copyBufferToTexture(
            source = GPUTexelCopyBufferInfo(
                buffer = srcBuffer,
            ),
            destination = GPUTexelCopyTextureInfo(
                texture = dstImage,
                mipLevel = dstMipLevel,
            ),
            copySize = GPUExtent3D(
                width = dstWidth,
                height = dstHeight,
                depthOrArrayLayers = dstDepth,
            ),
        )
    }

    actual fun copyImageToBuffer(
        srcImage: TextureHandle,
        dstBuffer: BufferHandle,
        srcX: Int, srcY: Int, srcZ: Int,
        dstOffset: Int,
        width: Int, height: Int, depth: Int,
    ) {
        val srcImage = srcImage.texture ?: return
        val dstBuffer = dstBuffer.value ?: return
        handle?.value?.copyTextureToBuffer(
            source = GPUTexelCopyTextureInfo(
                texture = srcImage,
                origin = GPUOrigin3D(
                    x = srcX,
                    y = srcY,
                    z = srcZ,
                ),
            ),
            destination = GPUTexelCopyBufferInfo(
                buffer = dstBuffer,
                offset = dstOffset.toGPUSize64(),
            ),
            copySize = GPUExtent3D(
                width = width,
                height = height,
                depthOrArrayLayers = depth,
            ),
        )
    }

    actual fun copyImageToImage(
        srcImage: TextureHandle,
        dstImage: TextureHandle,
        srcX: Int,
        srcY: Int,
        srcZ: Int,
        dstX: Int,
        dstY: Int,
        dstZ: Int,
        width: Int,
        height: Int,
        depth: Int
    ) {
        val srcImage = srcImage.texture ?: return
        val dstImage = dstImage.texture ?: return
        handle?.value?.copyTextureToTexture(
            source = GPUTexelCopyTextureInfo(
                texture = srcImage,
                origin = GPUOrigin3D(
                    x = srcX,
                    y = srcY,
                    z = srcZ,
                ),
            ),
            destination = GPUTexelCopyTextureInfo(
                texture = dstImage,
                origin = GPUOrigin3D(
                    x = dstX,
                    y = dstY,
                    z = dstZ,
                ),
            ),
            copySize = GPUExtent3D(
                width = width,
                height = height,
                depthOrArrayLayers = depth,
            ),
        )
    }
}