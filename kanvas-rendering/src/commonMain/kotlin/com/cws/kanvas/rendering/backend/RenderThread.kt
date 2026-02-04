package com.cws.kanvas.rendering.backend

import com.cws.std.async.ConcurrentQueue
import com.cws.std.async.Thread

data class Frame(
    val string: String,
)

class RenderThread(
    contextInfo: ContextInfo,
    surface: Any?,
) {

    private val context = RenderContext(contextInfo, surface)
    private val thread = Thread(
        start = false,
        name = "RenderThread",
        priority = 1,
        task = ::run,
    )
    private var isRunning = false
    private val frameQueue = ConcurrentQueue<Frame>(contextInfo.frameCount)
    private var currentFrameIndex = 0

    fun pushFrame(frame: Frame) {
        frameQueue.push(frame)
    }

    fun resize(width: Int, height: Int) {
        context.resize(width, height)
    }

    fun setSurface(surface: Any?) {
        context.setSurface(surface)
    }

    fun start() {
        isRunning = true
        thread.start()
    }

    fun stop() {
        isRunning = false
        thread.join()
    }

    private fun run() {
        isRunning = true
        context.create()
        while (isRunning) {
            val frame = frameQueue.pop()
            if (frame != null) {
                renderFrame(frame)
            }
        }
        context.destroy()
    }

    private fun renderFrame(frame: Frame) {
        context.beginFrame(currentFrameIndex)
        // TODO render per frame, consider how to apply command buffers in API agnostic way

        //    void RenderApi::render(Scope<CommandBuffer> &commandBuffer, u32 frame) {
//        u32 width = context->surface->render_target->info.width;
//        u32 height = context->surface->render_target->info.height;
//
//        commandBuffer->beginRenderPass({
//        .renderTarget = context->surface->render_target,
//        .colorAttachmentIndex = 0,
//    });
//
//        // commandBuffer->setPipeline(pipeline->handle);
//        commandBuffer->setVertexBuffer(meshBuffer->vertexBuffer);
//        commandBuffer->setIndexBuffer(meshBuffer->indexBuffer);
//        commandBuffer->setViewport({ width, height });
//        commandBuffer->setScissor(0, 0, width, height);
//        commandBuffer->drawIndexedIndirect({
//        .indirectBuffer = indirectIndexBuffer,
//        .offset = frame * sizeof(IndirectIndexData) * indirectIndexBuffer->count,
//        .drawCount = indirectIndexBuffer->count
//    });
//
//        commandBuffer->endRenderPass();
//    }

        context.endFrame(currentFrameIndex)
        currentFrameIndex = (currentFrameIndex + 1) % frameQueue.size()
    }

}
