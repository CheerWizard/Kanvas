package com.cws.kanvas.rendering.backend

import com.cws.std.memory.NativeString

class IndexBuffer(
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
        usages = BufferUsage.INDEX_BUFFER.value,
        size = (UShort.SIZE_BYTES * size).toLong(),
        isStatic = false,
    ),
)