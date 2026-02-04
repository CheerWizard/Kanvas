package com.cws.kanvas.rendering.backend

import com.cws.kanvas.rendering.backend.BufferHandle
import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.nextLong
import com.cws.std.memory.pushLong

actual class BindingLayoutHandle(
    var value: Long,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(0)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}

actual class BufferHandle(
    var value: Long,
    actual override val buffer: NativeBuffer? = null,
) : ResourceHandle {

    actual constructor() : this(0)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}

actual class RenderContextHandle(
    var value: Long,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(0)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}

actual class RenderPipelineHandle(
    var value: Long,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(0)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}

actual class RenderTargetHandle(
    var value: Long,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(0)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}

actual class SamplerHandle(
    var value: Long,
    actual override val buffer: NativeBuffer? = null,
) : ResourceHandle {

    actual constructor() : this(0)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}

actual class TextureHandle(
    var value: Long,
    actual override val buffer: NativeBuffer? = null,
) : ResourceHandle {

    actual constructor() : this(0)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}

actual class ShaderHandle(
    var value: Long,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(0)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}

actual class CommandBufferHandle(
    var value: Long,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(0)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}