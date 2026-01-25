@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUDeviceLostReason {
    companion object
}

inline val GPUDeviceLostReason.Companion.destroyed: GPUDeviceLostReason
    get() = unsafeCast("destroyed")

inline val GPUDeviceLostReason.Companion.unknown: GPUDeviceLostReason
    get() = unsafeCast("unknown")
