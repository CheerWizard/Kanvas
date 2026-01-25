package com.cws.kanvas.wgpu.gpu

external interface GPURenderPassTimestampWrites {
    var beginningOfPassWriteIndex: GPUSize32?
    var endOfPassWriteIndex: GPUSize32?
    var querySet: GPUQuerySet
}

fun GPURenderPassTimestampWrites(
    querySet: GPUQuerySet,
    beginningOfPassWriteIndex: GPUSize32? = null,
    endOfPassWriteIndex: GPUSize32? = null
): GPURenderPassTimestampWrites = jsObject {
    this.querySet = querySet
    this.beginningOfPassWriteIndex = beginningOfPassWriteIndex
    this.endOfPassWriteIndex = endOfPassWriteIndex
}
