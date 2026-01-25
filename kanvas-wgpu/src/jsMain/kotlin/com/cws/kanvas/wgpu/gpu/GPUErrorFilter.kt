@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUErrorFilter {
    companion object
}

inline val GPUErrorFilter.Companion.internal: GPUErrorFilter
    get() = unsafeCast("internal")

inline val GPUErrorFilter.Companion.outOfMemory: GPUErrorFilter
    get() = unsafeCast("out-of-memory")

inline val GPUErrorFilter.Companion.validation: GPUErrorFilter
    get() = unsafeCast("validation")
