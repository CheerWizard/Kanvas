@file:OptIn(ExperimentalJsCollectionsApi::class, ExperimentalStdlibApi::class)
@file:Suppress(
    "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.navigator

import kotlin.js.collections.JsReadonlyArray
import kotlin.js.definedExternally

@JsExternalInheritorsOnly
external interface NavigatorLanguage {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Navigator/language)
     */
    val language: String
        get() = definedExternally

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Navigator/languages)
     */
    val languages: JsReadonlyArray<String>
        get() = definedExternally
}
