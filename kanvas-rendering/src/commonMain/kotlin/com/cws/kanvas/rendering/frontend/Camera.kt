package com.cws.kanvas.rendering.frontend

import com.cws.std.math.*
import com.cws.std.memory.NativeData

@NativeData
data class _CameraData(
    val position: Vec4,
    val projection: Mat4,
    val view: Mat4,
)