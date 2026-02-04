package com.cws.kanvas.rendering.frontend

import com.cws.kanvas.rendering.backend.Binding
import com.cws.kanvas.rendering.backend.BindingLayout
import com.cws.kanvas.rendering.backend.BindingLayoutHandle
import com.cws.kanvas.rendering.backend.Buffer
import com.cws.kanvas.rendering.backend.BufferInfo
import com.cws.kanvas.rendering.backend.BufferUsage
import com.cws.kanvas.rendering.backend.MemoryType
import com.cws.kanvas.rendering.backend.RenderContext
import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeData
import com.cws.std.memory.NativeDataList
import com.cws.std.memory.NativeString

@NativeData
data class _IndirectIndexData(
    val indices: Int = 0,
    val instances: Int = 1,
    val indexOffset: Int = 0,
    val vertexOffset: Int = 0,
    val instanceOffset: Int = 0,
)

class IndirectIndexBuffer(
    context: RenderContext,
    name: String,
    size: Int,
) : Buffer(
    context = context,
    info = BufferInfo(
        name = NativeString(name),
        bindingLayout = BindingLayoutHandle(),
        binding = Binding(),
        memoryType = MemoryType.HOST,
        usages = BufferUsage.INDIRECT_BUFFER.value,
        size = (IndirectIndexData().sizeBytes(MemoryLayout.KOTLIN) * size).toLong(),
        isStatic = false,
    ),
) {
    val list: NativeDataList<IndirectIndexData> = NativeDataList(MemoryLayout.KOTLIN, size, { IndirectIndexData() })
}
