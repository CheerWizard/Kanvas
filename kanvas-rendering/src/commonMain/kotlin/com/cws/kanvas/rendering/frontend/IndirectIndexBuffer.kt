package com.cws.kanvas.rendering.frontend

import com.cws.kanvas.rendering.backend.BufferConfig
import com.cws.kanvas.rendering.backend.BufferUsage
import com.cws.kanvas.rendering.backend.Device
import com.cws.kanvas.rendering.backend.MemoryType
import com.cws.std.memory.NativeData

@NativeData
data class _IndirectIndexData(
    val indices: Int = 0,
    val instances: Int = 1,
    val indexOffset: Int = 0,
    val vertexOffset: Int = 0,
    val instanceOffset: Int = 0,
)

class IndirectIndexBuffer(device: Device, size: Int) : TypedBuffer<IndirectIndexData>(
    device = device,
    config = BufferConfig(
        usages = BufferUsage.INDIRECT_BUFFER.value,
        size = (size * IndirectIndexData().sizeBytes).toLong(),
        memoryType = MemoryType.HOST,
    ),
    factory = { IndirectIndexData() },
)