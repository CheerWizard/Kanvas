package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK
import com.cws.std.memory.NativeDataList

actual class CommandBuffer actual constructor(
    context: RenderContext,
    handle: CommandBufferHandle,
) {

    actual var handle: CommandBufferHandle? = handle

    actual fun reset() {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_reset(handle)
        }
    }

    actual fun begin() {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_begin(handle)
        }
    }

    actual fun end() {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_end(handle)
        }
    }

    actual fun beginRenderPass(
        renderTarget: RenderTargetHandle,
        colorAttachmentIndex: Int
    ) {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_beginRenderPass(handle, renderTarget.value, colorAttachmentIndex)
        }
    }

    actual fun endRenderPass() {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_endRenderPass(handle)
        }
    }

    actual fun setPipeline(pipeline: RenderPipeline) {
        val pipeline = pipeline.handle?.value ?: return
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_setPipe(handle, pipeline)
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
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_setViewport(handle, x, y, width, height, minDepth, maxDepth)
        }
    }

    actual fun setScissor(x: Int, y: Int, width: Int, height: Int) {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_setScissor(handle, x, y, width, height)
        }
    }

    actual fun addSecondaryBuffer(secondaryBuffer: CommandBufferHandle) {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_addSecondaryBuffer(handle, secondaryBuffer.value)
        }
    }

    actual fun addSecondaryBuffers(secondaryBuffers: NativeDataList<CommandBufferHandle>) {
        handle?.value?.let { handle ->
            val secondaryBuffersCount = secondaryBuffers.count
            secondaryBuffers.buffer?.buffer?.let { secondaryBuffers ->
                VK.VkCommandBufferResource_addSecondaryBuffers(handle, secondaryBuffers, secondaryBuffersCount)
            }
        }
    }

    actual fun draw(
        vertices: Int,
        vertexOffset: Int,
        instances: Int,
        instanceOffset: Int
    ) {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_draw(handle, vertices, vertexOffset, instances, instanceOffset)
        }
    }

    actual fun drawIndexed(
        vertices: Int,
        vertexOffset: Int,
        indices: Int,
        indexOffset: Int,
        instances: Int,
        instanceOffset: Int
    ) {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_drawIndexed(handle, vertices, vertexOffset, indices, indexOffset, instances, instanceOffset)
        }
    }

    actual fun drawIndexedIndirect(
        indirectBuffer: BufferHandle,
        offset: Int,
        drawCount: Int
    ) {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_drawIndexedIndirect(handle, indirectBuffer.value, offset, drawCount)
        }
    }

    actual fun copyBufferToBuffer(
        srcBuffer: BufferHandle,
        dstBuffer: BufferHandle,
        srcOffset: Int,
        dstOffset: Int,
        size: Int
    ) {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_copyBufferToBuffer(handle,
                srcBuffer.value, dstBuffer.value,
                srcOffset, dstOffset,
                size
            )
        }
    }

    actual fun copyBufferToImage(
        srcBuffer: BufferHandle,
        dstImage: TextureHandle,
        dstMipLevel: Int,
        dstWidth: Int,
        dstHeight: Int,
        dstDepth: Int
    ) {
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_copyBufferToImage(handle,
                srcBuffer.value, dstImage.value,
                dstMipLevel, dstWidth, dstHeight, dstDepth,
            )
        }
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
        handle?.value?.let { handle ->
            VK.VkCommandBufferResource_copyImageToImage(handle,
                srcImage.value, dstImage.value,
                srcX, srcY, srcZ,
                dstX, dstY, dstZ,
                width, height, depth,
            )
        }
    }
}