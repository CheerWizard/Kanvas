@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUQueryType {
    companion object
}

inline val GPUQueryType.Companion.occlusion: GPUQueryType
    get() = unsafeCast("occlusion")

inline val GPUQueryType.Companion.timestamp: GPUQueryType
    get() = unsafeCast("timestamp")
