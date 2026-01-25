@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUCanvasAlphaMode {
    companion object
}

inline val GPUCanvasAlphaMode.Companion.opaque: GPUCanvasAlphaMode
    get() = "opaque".unsafeCast<GPUCanvasAlphaMode>()

inline val GPUCanvasAlphaMode.Companion.premultiplied: GPUCanvasAlphaMode
    get() = "premultiplied".unsafeCast<GPUCanvasAlphaMode>()
