@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

sealed external interface GPUAddressMode {
    companion object
}

inline val GPUAddressMode.Companion.clampToEdge: GPUAddressMode
    get() = "clamp-to-edge".unsafeCast<GPUAddressMode>()

inline val GPUAddressMode.Companion.mirrorRepeat: GPUAddressMode
    get() = "mirror-repeat".unsafeCast<GPUAddressMode>()

inline val GPUAddressMode.Companion.repeat: GPUAddressMode
    get() = "repeat".unsafeCast<GPUAddressMode>()
