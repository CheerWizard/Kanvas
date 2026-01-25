@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUStoreOp {
    companion object
}

inline val GPUStoreOp.Companion.discard: GPUStoreOp
    get() = unsafeCast("discard")

inline val GPUStoreOp.Companion.store: GPUStoreOp
    get() = unsafeCast("store")
