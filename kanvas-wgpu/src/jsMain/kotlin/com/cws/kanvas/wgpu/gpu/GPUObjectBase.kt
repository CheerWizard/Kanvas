@file:Suppress(
    "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.definedExternally

@OptIn(ExperimentalStdlibApi::class)
@JsExternalInheritorsOnly
external interface GPUObjectBase {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUBindGroup/label)
     */
    var label: String
        get() = definedExternally
        set(value) = definedExternally
}
