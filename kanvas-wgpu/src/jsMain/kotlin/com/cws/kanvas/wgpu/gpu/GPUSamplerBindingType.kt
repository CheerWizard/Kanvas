@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUSamplerBindingType {
    companion object
}

inline val GPUSamplerBindingType.Companion.comparison: GPUSamplerBindingType
    get() = unsafeCast("comparison")

inline val GPUSamplerBindingType.Companion.filtering: GPUSamplerBindingType
    get() = unsafeCast("filtering")

inline val GPUSamplerBindingType.Companion.nonFiltering: GPUSamplerBindingType
    get() = unsafeCast("non-filtering")
