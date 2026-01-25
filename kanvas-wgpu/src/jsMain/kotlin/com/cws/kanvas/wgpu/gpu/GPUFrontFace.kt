@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUFrontFace {
    companion object
}

inline val GPUFrontFace.Companion.ccw: GPUFrontFace
    get() = unsafeCast("ccw")

inline val GPUFrontFace.Companion.cw: GPUFrontFace
    get() = unsafeCast("cw")
