package com.cws.kanvas.wgpu.gpu

import kotlin.js.definedExternally

/**
 * Available only in secure contexts.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPURenderBundleEncoder)
 */
open external class GPURenderBundleEncoder
private constructor() :
    GPUBindingCommandsMixin,
    GPUDebugCommandsMixin,
    GPUObjectBase,
    GPURenderCommandsMixin {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPURenderBundleEncoder/finish)
     */
    fun finish(descriptor: GPURenderBundleDescriptor = definedExternally): GPURenderBundle
}
