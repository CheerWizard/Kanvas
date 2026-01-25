@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUCanvasToneMappingMode {
    companion object
}

inline val GPUCanvasToneMappingMode.Companion.extended: GPUCanvasToneMappingMode
    get() = "extended".unsafeCast<GPUCanvasToneMappingMode>()

inline val GPUCanvasToneMappingMode.Companion.standard: GPUCanvasToneMappingMode
    get() = "standard".unsafeCast<GPUCanvasToneMappingMode>()
