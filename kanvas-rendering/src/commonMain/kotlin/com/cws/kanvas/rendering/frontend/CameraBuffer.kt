package com.cws.kanvas.rendering.frontend

import com.cws.kanvas.math.Mat4
import com.cws.kanvas.math.Vec4
import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.StructNode
import com.cws.std.memory.NativeData

@NativeData
@StructNode
data class _CameraData(
    val position: Vec4,
    val projection: Mat4,
    val view: Mat4,
)