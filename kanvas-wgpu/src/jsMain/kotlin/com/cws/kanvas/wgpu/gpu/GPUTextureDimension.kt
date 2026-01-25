@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUTextureDimension {
    companion object
}

inline val GPUTextureDimension.Companion._1d: GPUTextureDimension
    get() = unsafeCast("1d")

inline val GPUTextureDimension.Companion._2d: GPUTextureDimension
    get() = unsafeCast("2d")

inline val GPUTextureDimension.Companion._3d: GPUTextureDimension
    get() = unsafeCast("3d")
