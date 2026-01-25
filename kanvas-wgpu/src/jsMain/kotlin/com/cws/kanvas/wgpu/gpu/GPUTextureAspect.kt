@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUTextureAspect {
    companion object
}

inline val GPUTextureAspect.Companion.all: GPUTextureAspect
    get() = unsafeCast("all")

inline val GPUTextureAspect.Companion.depthOnly: GPUTextureAspect
    get() = unsafeCast("depth-only")

inline val GPUTextureAspect.Companion.stencilOnly: GPUTextureAspect
    get() = unsafeCast("stencil-only")
