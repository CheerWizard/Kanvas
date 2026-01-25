@file:Suppress(
    "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.definedExternally

@OptIn(ExperimentalStdlibApi::class)
@JsExternalInheritorsOnly
external interface GPUPipelineBase {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUComputePipeline/getBindGroupLayout)
     */
    fun getBindGroupLayout(index: Int): GPUBindGroupLayout = definedExternally
}
