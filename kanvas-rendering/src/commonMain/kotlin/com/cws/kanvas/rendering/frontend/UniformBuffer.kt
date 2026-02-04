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
import com.cws.std.memory.NativeDataList
import com.cws.std.memory.NativeString

class UniformBuffer<T : INativeData>(
    context: RenderContext,
    name: String,
    bindingLayout: BindingLayout,
    binding: Binding,
    size: Int,
    factory: () -> T,
) : Buffer(
    context = context,
    info = BufferInfo(
        name = NativeString(name),
        bindingLayout = bindingLayout.handle ?: BindingLayoutHandle(),
        binding = binding,
        memoryType = MemoryType.HOST,
        usages = BufferUsage.UNIFORM_BUFFER.value,
        size = (factory().sizeBytes(MemoryLayout.STD140) * size).toLong(),
        isStatic = false,
    ),
) {
    val list: NativeDataList<T> = NativeDataList(MemoryLayout.STD140, size, factory)
}