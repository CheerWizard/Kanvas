@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUMipmapFilterMode {
    companion object
}

inline val GPUMipmapFilterMode.Companion.linear: GPUMipmapFilterMode
    get() = unsafeCast("linear")

inline val GPUMipmapFilterMode.Companion.nearest: GPUMipmapFilterMode
    get() = unsafeCast("nearest")
