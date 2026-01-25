@file:OptIn(ExperimentalJsCollectionsApi::class)
@file:Suppress(
    "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

import org.khronos.webgl.Uint32Array
import kotlin.js.collections.JsReadonlyArray
import kotlin.js.definedExternally

external interface GPUBindingCommandsMixin {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUComputePassEncoder/setBindGroup)
     */
    fun setBindGroup(
        index: GPUIndex32,
        bindGroup: GPUBindGroup?,
        dynamicOffsets: JsReadonlyArray<GPUBufferDynamicOffset> = definedExternally,
    ): Unit = definedExternally

    fun setBindGroup(
        index: GPUIndex32,
        bindGroup: GPUBindGroup?,
        dynamicOffsetsData: Uint32Array,
        dynamicOffsetsDataStart: GPUSize64 = definedExternally,
        dynamicOffsetsDataLength: GPUSize32 = definedExternally,
    ): Unit = definedExternally
}
