package com.cws.kanvas.wgpu.gpu

external interface GPUProgrammableStage {
    var constants: dynamic
    var entryPoint: String?
    var module: GPUShaderModule
}
