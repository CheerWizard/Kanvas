@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPULoadOp {
    companion object
}

inline val GPULoadOp.Companion.clear: GPULoadOp
    get() = unsafeCast("clear")

inline val GPULoadOp.Companion.load: GPULoadOp
    get() = unsafeCast("load")
