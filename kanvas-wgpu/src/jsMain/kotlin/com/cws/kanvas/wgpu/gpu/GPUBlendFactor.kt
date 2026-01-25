@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUBlendFactor {
    companion object
}

inline val GPUBlendFactor.Companion.constant: GPUBlendFactor
    get() = "constant".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.dst: GPUBlendFactor
    get() = "dst".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.dstAlpha: GPUBlendFactor
    get() = "dst-alpha".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.one: GPUBlendFactor
    get() = "one".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.oneMinusConstant: GPUBlendFactor
    get() = "one-minus-constant".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.oneMinusDst: GPUBlendFactor
    get() = "one-minus-dst".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.oneMinusDstAlpha: GPUBlendFactor
    get() = "one-minus-dst-alpha".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.oneMinusSrc: GPUBlendFactor
    get() = "one-minus-src".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.oneMinusSrcAlpha: GPUBlendFactor
    get() = "one-minus-src-alpha".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.src: GPUBlendFactor
    get() = "src".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.srcAlpha: GPUBlendFactor
    get() = "src-alpha".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.srcAlphaSaturated: GPUBlendFactor
    get() = "src-alpha-saturated".unsafeCast<GPUBlendFactor>()

inline val GPUBlendFactor.Companion.zero: GPUBlendFactor
    get() = "zero".unsafeCast<GPUBlendFactor>()
