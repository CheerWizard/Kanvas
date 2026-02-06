package com.cws.kanvas.vk

import java.nio.ByteBuffer

typealias VkHandle = Long
typealias VkHandles = LongArray

object VK {

    // --------------------------------------------------
    // Callbacks
    // --------------------------------------------------

    external fun LogBridge_callback(callback: (level: Int, tag: String, msg: String, exceptionMsg: String) -> Unit)
    external fun ResultBridge_callback(callback: (result: Int) -> Unit)
    external fun removeCallbacks()

    // --------------------------------------------------
    // VkContext
    // --------------------------------------------------

    external fun VkContext_create(surface: Any?, info: ByteBuffer): VkHandle
    external fun VkContext_destroy(context: VkHandle)
    external fun VkContext_setInfo(
        context: VkHandle,
        info: ByteBuffer
    )
    external fun VkContext_wait(context: VkHandle)
    external fun VkContext_resize(
        context: VkHandle,
        width: Int,
        height: Int
    )
    external fun VkContext_setSurface(
        context: VkHandle,
        surface: Any?,
    )
    external fun VkContext_getRenderTarget(
        context: VkHandle
    ): VkHandle
    external fun VkContext_getPrimaryCommandBuffer(
        context: VkHandle,
        frame: Int
    ): VkHandle
    external fun VkContext_getSecondaryCommandBuffer(
        context: VkHandle
    ): VkHandle
    external fun VkContext_beginFrame(
        context: VkHandle,
        frame: Int
    )
    external fun VkContext_endFrame(
        context: VkHandle,
        frame: Int
    )

    // --------------------------------------------------
    // VkShader
    // --------------------------------------------------

    external fun VkShader_create(
        context: VkHandle,
        info: ByteBuffer
    ): VkHandle

    external fun VkShader_destroy(
        shader: VkHandle
    )

    external fun VkShader_setInfo(
        shader: VkHandle,
        info: ByteBuffer
    )

    // --------------------------------------------------
    // VkBindingLayout
    // --------------------------------------------------

    external fun VkBindingLayout_create(
        context: VkHandle,
        info: ByteBuffer
    ): VkHandle

    external fun VkBindingLayout_destroy(
        layout: VkHandle
    )

    external fun VkBindingLayout_setInfo(
        layout: VkHandle,
        info: ByteBuffer
    )

    // --------------------------------------------------
    // VkRenderTarget
    // --------------------------------------------------

    external fun VkRenderTarget_create(
        context: VkHandle,
        info: ByteBuffer
    ): VkHandle

    external fun VkRenderTarget_destroy(
        target: VkHandle
    )

    external fun VkRenderTarget_setInfo(
        target: VkHandle,
        info: ByteBuffer
    )

    external fun VkRenderTarget_resize(
        target: VkHandle,
        width: Int,
        height: Int
    )

    // --------------------------------------------------
    // VkBufferResource
    // --------------------------------------------------

    external fun VkBufferResource_create(
        context: VkHandle,
        info: ByteBuffer
    ): VkHandle

    external fun VkBufferResource_destroy(
        buffer: VkHandle
    )

    external fun VkBufferResource_setInfo(
        buffer: VkHandle,
        info: ByteBuffer
    )

    external fun VkBufferResource_map(
        buffer: VkHandle,
        frame: Int
    ): ByteBuffer?

    external fun VkBufferResource_unmap(
        buffer: VkHandle
    )

    // --------------------------------------------------
    // VkSamplerResource
    // --------------------------------------------------

    external fun VkSamplerResource_create(
        context: VkHandle,
        info: ByteBuffer
    ): VkHandle

    external fun VkSamplerResource_destroy(
        sampler: VkHandle
    )

    external fun VkSamplerResource_setInfo(
        sampler: VkHandle,
        info: ByteBuffer
    )

    // --------------------------------------------------
    // VkTextureResource
    // --------------------------------------------------

    external fun VkTextureResource_create(
        context: VkHandle,
        info: ByteBuffer
    ): VkHandle

    external fun VkTextureResource_destroy(
        texture: VkHandle
    )

    external fun VkTextureResource_setInfo(
        texture: VkHandle,
        info: ByteBuffer
    )

    external fun VkTextureResource_map(
        texture: VkHandle,
        frame: Int
    ): ByteBuffer?

    external fun VkTextureResource_unmap(
        texture: VkHandle
    )

    // --------------------------------------------------
    // VkPipe
    // --------------------------------------------------

    external fun VkPipe_create(
        context: VkHandle,
        info: ByteBuffer
    ): VkHandle

    external fun VkPipe_destroy(
        pipe: VkHandle
    )

    external fun VkPipe_setInfo(
        pipe: VkHandle,
        info: ByteBuffer
    )

    // --------------------------------------------------
    // VkPipe
    // --------------------------------------------------

    external fun VkComputePipe_create(
        context: VkHandle,
        info: ByteBuffer
    ): VkHandle

    external fun VkComputePipe_destroy(
        pipe: VkHandle
    )

    external fun VkComputePipe_setInfo(
        pipe: VkHandle,
        info: ByteBuffer
    )

    // --------------------------------------------------
    // VkCommandBufferResource
    // --------------------------------------------------

    external fun VkCommandBufferResource_reset(
        cmd: VkHandle
    )

    external fun VkCommandBufferResource_begin(
        cmd: VkHandle
    )

    external fun VkCommandBufferResource_end(
        cmd: VkHandle
    )

    external fun VkCommandBufferResource_beginRenderPass(
        cmd: VkHandle,
        renderTarget: VkHandle,
        colorAttachmentIndex: Int
    )

    external fun VkCommandBufferResource_endRenderPass(
        cmd: VkHandle
    )

    external fun VkCommandBufferResource_setPipe(
        cmd: VkHandle,
        pipe: VkHandle
    )

    external fun VkCommandBufferResource_setViewport(
        cmd: VkHandle,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        minDepth: Float,
        maxDepth: Float
    )

    external fun VkCommandBufferResource_setScissor(
        cmd: VkHandle,
        x: Int,
        y: Int,
        w: Int,
        h: Int
    )

    external fun VkCommandBufferResource_addSecondaryBuffer(
        cmd: VkHandle,
        secondaryBuffer: VkHandle
    )

    external fun VkCommandBufferResource_addSecondaryBuffers(
        cmd: VkHandle,
        secondaryBuffers: ByteBuffer,
        secondaryBuffersCount: Long,
    )

    external fun VkCommandBufferResource_draw(
        cmd: VkHandle,
        vertices: Int,
        vertexOffset: Int,
        instances: Int,
        instanceOffset: Int
    )

    external fun VkCommandBufferResource_drawIndexed(
        cmd: VkHandle,
        vertices: Int,
        vertexOffset: Int,
        indices: Int,
        indexOffset: Int,
        instances: Int,
        instanceOffset: Int
    )

    external fun VkCommandBufferResource_drawIndexedIndirect(
        cmd: VkHandle,
        indirectBuffer: VkHandle,
        offset: Int,
        drawCount: Int
    )

    external fun VkCommandBufferResource_copyBufferToBuffer(
        cmd: VkHandle,
        src: VkHandle,
        dst: VkHandle,
        srcOffset: Int,
        dstOffset: Int,
        size: Int
    )

    external fun VkCommandBufferResource_copyBufferToImage(
        cmd: VkHandle,
        src: VkHandle,
        dst: VkHandle,
        dstMipLevel: Int,
        dstWidth: Int,
        dstHeight: Int,
        dstDepth: Int
    )

    external fun VkCommandBufferResource_copyImageToImage(
        cmd: VkHandle,
        src: VkHandle,
        dst: VkHandle,
        srcX: Int,
        srcY: Int,
        srcZ: Int,
        dstX: Int,
        dstY: Int,
        dstZ: Int,
        width: Int,
        height: Int,
        depth: Int
    )

    external fun VkCommandBufferResource_dispatch(
        cmd: VkHandle,
        groupsX: Int,
        groupsY: Int,
        groupsZ: Int,
    )

    external fun VkCommandBufferResource_pipelineBarrier(
        cmd: VkHandle,
        srcStages: Int,
        dstStages: Int,
        srcAccessFlags: Int,
        dstAccessFlags: Int,
    )
}
