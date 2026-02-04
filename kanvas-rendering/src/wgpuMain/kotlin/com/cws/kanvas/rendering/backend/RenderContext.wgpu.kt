@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.GPU
import com.cws.kanvas.wgpu.gpu.GPUAdapter
import com.cws.kanvas.wgpu.gpu.GPUCanvasConfiguration
import com.cws.kanvas.wgpu.gpu.GPUCanvasContext
import com.cws.kanvas.wgpu.gpu.GPUDevice
import com.cws.kanvas.wgpu.gpu.GPUError
import com.cws.kanvas.wgpu.gpu.GPUErrorFilter
import com.cws.kanvas.wgpu.gpu.GPURenderPassColorAttachment
import com.cws.kanvas.wgpu.gpu.GPURenderPassDescriptor
import com.cws.kanvas.wgpu.gpu.GPUTextureFormat
import com.cws.kanvas.wgpu.gpu.GPUTextureUsage
import com.cws.kanvas.wgpu.gpu.bgra8unorm
import com.cws.kanvas.wgpu.gpu.internal
import com.cws.kanvas.wgpu.gpu.jsArrayOf
import com.cws.kanvas.wgpu.gpu.onUncapturedError
import com.cws.kanvas.wgpu.gpu.outOfMemory
import com.cws.kanvas.wgpu.gpu.requestAdapter
import com.cws.kanvas.wgpu.gpu.requestDevice
import com.cws.kanvas.wgpu.gpu.validation
import com.cws.kanvas.wgpu.navigator.NavigatorGPU
import com.cws.print.Print
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLCanvasElement

actual class RenderContext actual constructor(
    info: ContextInfo,
    private val surface: Any?,
) : Resource<RenderContextHandle, ContextInfo>(info) {

    companion object {
        private const val TAG = "RenderContext"
    }

    private val canvas = window.document.getElementById("canvas") as HTMLCanvasElement
    private var gpu: GPU? = null
    private var adapter: GPUAdapter? = null
    // TODO current browsers only support single command buffer model,
    //  so everything must be recorded and submit through this single CommandBuffer
    private var commandBuffer: CommandBuffer? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    inline fun call(call: (GPUDevice) -> Unit) {
        handle?.value?.device?.let { device ->
            device.pushErrorScope(GPUErrorFilter.outOfMemory)
            device.pushErrorScope(GPUErrorFilter.internal)
            device.pushErrorScope(GPUErrorFilter.validation)
            call(device)
            device.popErrorScopeAsync().then(::onError)
            device.popErrorScopeAsync().then(::onError)
            device.popErrorScopeAsync().then(::onError)
        }
    }

    fun onError(error: GPUError?) {
        error ?: return
        when (jsTypeOf(error)) {
            "GPUValidationError" -> Print.e(TAG, "Validation error: ${error.message}")
            "GPUOutOfMemoryError" -> Print.e(TAG, "OOM error: ${error.message}")
            "GPUInternalError" -> Print.e(TAG, "Internal error: ${error.message}")
            else -> Print.e(TAG, "Unknown GPU error: ${error.message}")
        }
    }

    actual override fun onCreate() {
        scope.launch {
            gpu = window.navigator.unsafeCast<NavigatorGPU>().gpu
            if (gpu == null) {
                error("Failed to find supported GPU for browser!")
            }

            adapter = gpu?.requestAdapter()
            if (adapter == null) {
                error("Failed to find supported GPUAdapter for browser!")
            }

            val device = adapter?.requestDevice()
            if (device == null) {
                error("Failed to find supported GPUDevice for browser!")
            }

            device.label = "KanvasWGPUDevice"
            device.onUncapturedError { event -> onError(event.error)}

            handle = RenderContextHandle(GPUCanvasContext("canvas"))
            handle?.value?.configure(
                GPUCanvasConfiguration(
                    device = device,
                    format = GPUTextureFormat.bgra8unorm,
                    usage = GPUTextureUsage.RENDER_ATTACHMENT,
                )
            )
        }
    }

    actual override fun onDestroy() {
        handle = null
        gpu = null
        adapter = null
        commandBuffer = null
    }

    actual override fun setInfo() {
        handle?.value?.let { handle ->
            handle.configure(
                GPUCanvasConfiguration(
                    device = handle.device,
                    format = GPUTextureFormat.bgra8unorm,
                    usage = GPUTextureUsage.RENDER_ATTACHMENT,
                )
            )
        }
    }

    actual fun wait() {
        // there is no API for direct waiting on device
    }

    actual fun resize(width: Int, height: Int) {
        canvas.width = width
        canvas.height = height
        setInfo()
    }

    actual fun setSurface(surface: Any?) {
        // browser doesn't have surface
    }

    actual fun getRenderTarget(): RenderTarget {
        val handle = handle?.value ?: error("getRenderTarget failed because context handle is null!")
        val texture = handle.currentTexture
        val view = texture.createView()
        return RenderTarget(
            this,
            RenderTargetHandle(GPURenderPassDescriptor(
                colorAttachments = jsArrayOf(
                    GPURenderPassColorAttachment(view),
                ),
            ))
        )
    }

    actual fun beginFrame(frame: Int) {
        handle?.value?.device?.let { device ->
            commandBuffer = CommandBuffer(this, CommandBufferHandle(device.createCommandEncoder()))
        }
    }

    actual fun endFrame(frame: Int) {
        val device = handle?.value?.device ?: return
        val gpuCommandBuffer = commandBuffer?.handle?.value?.finish() ?: return
        device.queue.submit(jsArrayOf(gpuCommandBuffer))
        commandBuffer = null
    }

    actual fun getPrimaryCommandBuffer(frame: Int): CommandBuffer? = commandBuffer

    // TODO secondary command buffers design is not supported or limited for browsers right now.
    actual fun getSecondaryCommandBuffer(): CommandBuffer? = null

}