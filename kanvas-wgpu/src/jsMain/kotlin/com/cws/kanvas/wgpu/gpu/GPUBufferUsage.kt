package com.cws.kanvas.wgpu.gpu

external object GPUBufferUsage {
    val MAP_READ: GPUBufferUsageFlags
    val MAP_WRITE: GPUBufferUsageFlags
    val COPY_SRC: GPUBufferUsageFlags
    val COPY_DST: GPUBufferUsageFlags
    val INDEX: GPUBufferUsageFlags
    val VERTEX: GPUBufferUsageFlags
    val UNIFORM: GPUBufferUsageFlags
    val STORAGE: GPUBufferUsageFlags
    val INDIRECT: GPUBufferUsageFlags
    val QUERY_RESOLVE: GPUBufferUsageFlags
}
