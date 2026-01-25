@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface PredefinedColorSpace {
    companion object
}

inline val PredefinedColorSpace.Companion.displayP3: PredefinedColorSpace
    get() = unsafeCast("display-p3")

inline val PredefinedColorSpace.Companion.srgb: PredefinedColorSpace
    get() = unsafeCast("srgb")