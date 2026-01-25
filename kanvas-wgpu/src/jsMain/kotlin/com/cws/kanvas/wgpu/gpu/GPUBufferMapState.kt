@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUBufferMapState {
    companion object
}

inline val GPUBufferMapState.Companion.mapped: GPUBufferMapState
    get() = "mapped".unsafeCast<GPUBufferMapState>()

inline val GPUBufferMapState.Companion.pending: GPUBufferMapState
    get() = "pending".unsafeCast<GPUBufferMapState>()

inline val GPUBufferMapState.Companion.unmapped: GPUBufferMapState
    get() = "unmapped".unsafeCast<GPUBufferMapState>()
