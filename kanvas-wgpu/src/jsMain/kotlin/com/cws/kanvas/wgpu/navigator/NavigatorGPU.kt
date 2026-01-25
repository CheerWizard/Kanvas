@file:OptIn(ExperimentalStdlibApi::class)

@file:Suppress(
    "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.navigator

import com.cws.kanvas.wgpu.gpu.GPU
import kotlin.js.definedExternally

@JsExternalInheritorsOnly
external interface NavigatorGPU {
    /**
     * Available only in secure contexts.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Navigator/gpu)
     */
    val gpu: GPU
        get() = definedExternally
}
