@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUCompilationMessageType {
    companion object
}

inline val GPUCompilationMessageType.Companion.error: GPUCompilationMessageType
    get() = "error".unsafeCast<GPUCompilationMessageType>()

inline val GPUCompilationMessageType.Companion.info: GPUCompilationMessageType
    get() = "info".unsafeCast<GPUCompilationMessageType>()

inline val GPUCompilationMessageType.Companion.warning: GPUCompilationMessageType
    get() = "warning".unsafeCast<GPUCompilationMessageType>()
