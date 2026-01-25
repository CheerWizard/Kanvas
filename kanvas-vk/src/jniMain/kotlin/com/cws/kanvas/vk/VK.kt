package com.cws.kanvas.vk

import java.nio.ByteBuffer

typealias VkHandle = Long
typealias VkHandles = LongArray

object VK {

    // --------------------------------------------------
    // Callbacks
    // --------------------------------------------------

    external fun LogBridge_callback(callback: (String) -> Unit)
    external fun ResultBridge_callback(callback: (Int) -> Unit)

    // --------------------------------------------------
    // VkContext
    // --------------------------------------------------

    external fun VkContext_create(info: ByteBuffer): VkHandle
    external fun VkContext_destroy(context: VkHandle)
    external fun VkContext_wait(context: VkHandle)

    external fun VkContext_resize(
        context: VkHandle,
        width: Int,
        height: Int
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
    // VkDeviceQueue
    // --------------------------------------------------

    external fun VkDeviceQueue_reset(
        queue: VkHandle
    )

    // --------------------------------------------------
    // VkFenceResource
    // --------------------------------------------------

    external fun VkFenceResource_create(
        context: VkHandle,
        signaled: Int
    ): VkHandle

    external fun VkFenceResource_destroy(
        fence: VkHandle
    )

    external fun VkFenceResource_wait(
        fence: VkHandle,
        timeout: Long
    )

    external fun VkFenceResource_reset(
        fence: VkHandle
    )

    // --------------------------------------------------
    // VkSemaphoreResource
    // --------------------------------------------------

    external fun VkSemaphoreResource_create(
        context: VkHandle
    ): VkHandle

    external fun VkSemaphoreResource_destroy(
        semaphore: VkHandle
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

    external fun VkShader_update(
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

    external fun VkBindingLayout_update(
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

    external fun VkBufferResource_map(
        buffer: VkHandle
    ): Long

    external fun VkBufferResource_unmap(
        buffer: VkHandle
    )

    external fun VkBufferResource_updateBinding(
        buffer: VkHandle,
        frame: Int
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

    external fun VkSamplerResource_updateBinding(
        sampler: VkHandle,
        frame: Int
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

    external fun VkTextureResource_map(
        texture: VkHandle
    ): Long

    external fun VkTextureResource_unmap(
        texture: VkHandle
    )

    external fun VkTextureResource_updateBinding(
        texture: VkHandle,
        frame: Int
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

    external fun VkPipe_update(
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
        secondaryBuffers: VkHandles
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
        offset: Long,
        drawCount: Int
    )

    external fun VkCommandBufferResource_copyBufferToBuffer(
        cmd: VkHandle,
        src: VkHandle,
        dst: VkHandle,
        srcOffset: Long,
        dstOffset: Long,
        size: Long
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
}
