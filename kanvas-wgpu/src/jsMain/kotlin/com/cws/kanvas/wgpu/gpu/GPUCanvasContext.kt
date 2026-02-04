package com.cws.kanvas.wgpu.gpu

import kotlinx.browser.document
import org.w3c.dom.HTMLCanvasElement

/**
 * Binding for the HTML canvas WebGPU context.
 * This corresponds to calling:
 *   canvas.getContext("webgpu")
 */
external interface GPUCanvasContext {
    val device: GPUDevice
    val format: String
    val currentTexture: GPUTexture

    fun configure(config: GPUCanvasConfiguration)
    fun unconfigure()
}

fun GPUCanvasContext(canvasId: String): GPUCanvasContext {
    val canvas = document.getElementById(canvasId) as HTMLCanvasElement
    @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
    val context = canvas.getContext("webgpu") as GPUCanvasContext?
    if (context == null) {
        error("Failed to find WebGPU on this browser for HTML canvas element - $canvasId!")
    }
    return context
}