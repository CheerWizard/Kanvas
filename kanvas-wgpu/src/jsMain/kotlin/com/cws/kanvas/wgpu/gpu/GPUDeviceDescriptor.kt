@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray

external interface GPUDeviceDescriptor :
    GPUObjectDescriptorBase {
    var defaultQueue: GPUQueueDescriptor?
    var requiredFeatures: JsReadonlyArray<GPUFeatureName>?
    var requiredLimits: GPULimits?
}

fun GPUDeviceDescriptor(
    defaultQueue: GPUQueueDescriptor? = null,
    requiredFeatures: JsReadonlyArray<GPUFeatureName>? = null,
    requiredLimits: GPULimits? = null,
    label: String? = null
): GPUDeviceDescriptor =
    jsObject {
        this.defaultQueue = defaultQueue
        this.requiredFeatures = requiredFeatures
        this.requiredLimits = requiredLimits
        this.label = label
    }
