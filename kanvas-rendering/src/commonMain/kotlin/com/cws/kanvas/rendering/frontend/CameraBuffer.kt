package com.cws.kanvas.rendering.frontend

import com.cws.kanvas.math.Mat4
import com.cws.kanvas.math.Vec4
import com.cws.kanvas.rendering.backend.BufferConfig
import com.cws.kanvas.rendering.backend.BufferUsage
import com.cws.kanvas.rendering.backend.Device
import com.cws.kanvas.rendering.backend.MemoryType
import com.cws.std.memory.NativeData

@NativeData
data class _CameraData(
    val position: Vec4,
    val projection: Mat4,
    val view: Mat4,
)

class CameraBuffer(device: Device, size: Int) : TypedBuffer<CameraData>(
    device = device,
    config = BufferConfig(
        usages = BufferUsage.UNIFORM_BUFFER.value,
        size = (size * CameraData().sizeBytes).toLong(),
        memoryType = MemoryType.HOST,
    ),
    factory = { CameraData() },
)