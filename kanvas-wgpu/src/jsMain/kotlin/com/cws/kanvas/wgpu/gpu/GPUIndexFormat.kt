@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUIndexFormat {
    companion object
}

inline val GPUIndexFormat.Companion.uint16: GPUIndexFormat
    get() = unsafeCast("uint16")

inline val GPUIndexFormat.Companion.uint32: GPUIndexFormat
    get() = unsafeCast("uint32")
