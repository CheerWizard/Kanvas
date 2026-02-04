@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPUFragmentState : GPUProgrammableStage {
    var targets: JsReadonlyArray<GPUColorTargetState?>
}

fun GPUFragmentState(
    module: GPUShaderModule,
    entryPoint: String,
    targets: JsReadonlyArray<GPUColorTargetState?>,
    constants: dynamic = null,
): GPUFragmentState = jsObject {
    this.module = module
    this.entryPoint = entryPoint
    this.targets = targets
    this.constants = constants
}
