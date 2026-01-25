@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUBufferBindingType {
    companion object
}

inline val GPUBufferBindingType.Companion.readOnlyStorage: GPUBufferBindingType
    get() = "read-only-storage".unsafeCast<GPUBufferBindingType>()

inline val GPUBufferBindingType.Companion.storage: GPUBufferBindingType
    get() = "storage".unsafeCast<GPUBufferBindingType>()

inline val GPUBufferBindingType.Companion.uniform: GPUBufferBindingType
    get() = "uniform".unsafeCast<GPUBufferBindingType>()
