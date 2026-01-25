@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUVertexStepMode {
    companion object
}

inline val GPUVertexStepMode.Companion.instance: GPUVertexStepMode
    get() = unsafeCast("instance")

inline val GPUVertexStepMode.Companion.vertex: GPUVertexStepMode
    get() = unsafeCast("vertex")
