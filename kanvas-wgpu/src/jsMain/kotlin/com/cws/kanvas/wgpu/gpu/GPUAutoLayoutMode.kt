@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUAutoLayoutMode {
    companion object
}

inline val GPUAutoLayoutMode.Companion.auto: GPUAutoLayoutMode
    get() = "auto".unsafeCast<GPUAutoLayoutMode>()
