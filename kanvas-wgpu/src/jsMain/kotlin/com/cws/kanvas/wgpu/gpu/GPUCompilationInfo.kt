@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

/**
 * Available only in secure contexts.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUCompilationInfo)
 */
open external class GPUCompilationInfo
private constructor() {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUCompilationInfo/messages)
     */
    val messages: JsReadonlyArray<GPUCompilationMessage>
}
