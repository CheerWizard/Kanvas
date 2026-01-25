@file:OptIn(ExperimentalStdlibApi::class)

@file:Suppress(
    "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.navigator

import com.cws.kanvas.wgpu.gpu.UInt53
import kotlin.js.definedExternally

/* mixin */
@JsExternalInheritorsOnly
external interface NavigatorConcurrentHardware {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Navigator/hardwareConcurrency)
     */
    val hardwareConcurrency: UInt53
        get() = definedExternally
}
