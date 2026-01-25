package com.cws.kanvas.wgpu.gpu

external interface GPUComputePassTimestampWrites {
    var querySet: GPUQuerySet
    var beginningOfPassWriteIndex: GPUSize32?
    var endOfPassWriteIndex: GPUSize32?
}

fun GPUComputePassTimestampWrites(
    querySet: GPUQuerySet,
    beginningOfPassWriteIndex: GPUSize32? = null,
    endOfPassWriteIndex: GPUSize32? = null
): GPUComputePassTimestampWrites =
    jsObject {
        this.querySet = querySet
        this.beginningOfPassWriteIndex = beginningOfPassWriteIndex
        this.endOfPassWriteIndex = endOfPassWriteIndex
    }