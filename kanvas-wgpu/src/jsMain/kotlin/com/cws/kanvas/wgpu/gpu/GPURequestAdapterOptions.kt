package com.cws.kanvas.wgpu.gpu

external interface GPURequestAdapterOptions {
    var forceFallbackAdapter: Boolean?
    var powerPreference: GPUPowerPreference?
}

fun GPURequestAdapterOptions(
    powerPreference: GPUPowerPreference? = null,
    forceFallbackAdapter: Boolean? = null
): GPURequestAdapterOptions = jsObject {
    this.powerPreference = powerPreference
    this.forceFallbackAdapter = forceFallbackAdapter
}
