@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUCompareFunction {
    companion object
}

inline val GPUCompareFunction.Companion.always: GPUCompareFunction
    get() = "always".unsafeCast<GPUCompareFunction>()

inline val GPUCompareFunction.Companion.equal: GPUCompareFunction
    get() = "equal".unsafeCast<GPUCompareFunction>()

inline val GPUCompareFunction.Companion.greater: GPUCompareFunction
    get() = "greater".unsafeCast<GPUCompareFunction>()

inline val GPUCompareFunction.Companion.greaterEqual: GPUCompareFunction
    get() = "greater-equal".unsafeCast<GPUCompareFunction>()

inline val GPUCompareFunction.Companion.less: GPUCompareFunction
    get() = "less".unsafeCast<GPUCompareFunction>()

inline val GPUCompareFunction.Companion.lessEqual: GPUCompareFunction
    get() = "less-equal".unsafeCast<GPUCompareFunction>()

inline val GPUCompareFunction.Companion.never: GPUCompareFunction
    get() = "never".unsafeCast<GPUCompareFunction>()

inline val GPUCompareFunction.Companion.notEqual: GPUCompareFunction
    get() = "not-equal".unsafeCast<GPUCompareFunction>()
