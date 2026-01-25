@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUBlendOperation {
    companion object
}

inline val GPUBlendOperation.Companion.add: GPUBlendOperation
    get() = "add".unsafeCast<GPUBlendOperation>()

inline val GPUBlendOperation.Companion.max: GPUBlendOperation
    get() = "max".unsafeCast<GPUBlendOperation>()

inline val GPUBlendOperation.Companion.min: GPUBlendOperation
    get() = "min".unsafeCast<GPUBlendOperation>()

inline val GPUBlendOperation.Companion.reverseSubtract: GPUBlendOperation
    get() = "reverse-subtract".unsafeCast<GPUBlendOperation>()

inline val GPUBlendOperation.Companion.subtract: GPUBlendOperation
    get() = "subtract".unsafeCast<GPUBlendOperation>()
