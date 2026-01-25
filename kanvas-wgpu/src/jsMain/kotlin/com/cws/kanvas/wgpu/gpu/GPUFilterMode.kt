@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUFilterMode {
    companion object
}

inline val GPUFilterMode.Companion.linear: GPUFilterMode
    get() = unsafeCast("linear")

inline val GPUFilterMode.Companion.nearest: GPUFilterMode
    get() = unsafeCast("nearest")
