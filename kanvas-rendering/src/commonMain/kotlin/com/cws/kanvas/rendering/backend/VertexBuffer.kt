package com.cws.kanvas.rendering.backend

import com.cws.kanvas.rendering.frontend.DefaultVertexAttributes
import com.cws.kanvas.rendering.frontend.sizeInBytes
import com.cws.std.memory.NativeString

class VertexBuffer(
    context: RenderContext,
    name: String,
    attributes: Array<Attribute> = DefaultVertexAttributes,
    size: Int,
    var slot: Int = 0,
) : Buffer(
    context = context,
    info = BufferInfo(
        name = NativeString(name),
        bindingLayout = BindingLayoutHandle(),
        binding = Binding(),
        memoryType = MemoryType.HOST,
        usages = BufferUsage.VERTEX_BUFFER.value,
        size = (attributes.sizeInBytes * size).toLong(),
        isStatic = false,
    ),
)