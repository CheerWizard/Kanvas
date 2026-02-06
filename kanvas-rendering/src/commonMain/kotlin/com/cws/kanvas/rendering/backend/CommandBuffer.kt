package com.cws.kanvas.rendering.backend

import com.cws.std.memory.NativeDataList

expect class CommandBuffer(
    context: RenderContext,
    handle: CommandBufferHandle,
) {
    var handle: CommandBufferHandle?

    fun reset()

    fun begin()
    fun end()

    fun beginRenderPass(renderTarget: RenderTargetHandle, colorAttachmentIndex: Int)
    fun endRenderPass()

    fun beginComputePass()
    fun endComputePass()

    fun setPipeline(pipeline: RenderPipeline)
    fun setViewport(
        x: Float, y: Float,
        width: Float, height: Float,
        minDepth: Float, maxDepth: Float,
    )
    fun setScissor(
        x: Int, y: Int,
        width: Int, height: Int,
    )

    fun addSecondaryBuffer(secondaryBuffer: CommandBufferHandle)
    fun addSecondaryBuffers(secondaryBuffers: NativeDataList<CommandBufferHandle>)

    fun draw(vertices: Int, vertexOffset: Int, instances: Int, instanceOffset: Int)

    fun drawIndexed(vertices: Int, vertexOffset: Int,
                    indices: Int, indexOffset: Int,
                    instances: Int, instanceOffset: Int)

    fun drawIndexedIndirect(indirectBuffer: BufferHandle, offset: Int, drawCount: Int)

    fun copyBufferToBuffer(srcBuffer: BufferHandle, dstBuffer: BufferHandle,
                           srcOffset: Int, dstOffset: Int, size: Int)

    fun copyBufferToImage(srcBuffer: BufferHandle, dstImage: TextureHandle,
                          dstMipLevel: Int, dstWidth: Int, dstHeight: Int, dstDepth: Int)

    fun copyImageToBuffer(srcImage: TextureHandle, dstBuffer: BufferHandle,
                          srcX: Int, srcY: Int, srcZ: Int, dstOffset: Int,
                          width: Int, height: Int, depth: Int)

    fun copyImageToImage(srcImage: TextureHandle, dstImage: TextureHandle,
                         srcX: Int, srcY: Int, srcZ: Int,
                         dstX: Int, dstY: Int, dstZ: Int,
                         width: Int, height: Int, depth: Int)

    fun dispatch(groupsX: Int, groupsY: Int, groupsZ: Int)

    fun pipelineBarrier(srcStages: Int, dstStages: Int, srcAccessFlags: Int, dstAccessFlags: Int)
}