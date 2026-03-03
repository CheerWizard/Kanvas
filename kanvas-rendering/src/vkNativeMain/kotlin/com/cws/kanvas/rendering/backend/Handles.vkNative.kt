@file:OptIn(ExperimentalForeignApi::class)

package com.cws.kanvas.rendering.backend

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.nextLong
import com.cws.std.memory.pushLong
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import vk.VkBindingLayout_create
import kotlin.native.internal.NativePtr

actual class BindingLayoutHandle(
    var value: NativePtr?,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(null)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        VkBindingLayout_create()
        buffer.pushLong(value)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}

actual class BufferHandle(
    var value: NativePtr?,
    actual override val buffer: NativeBuffer? = null,
) : ResourceHandle {

    actual constructor() : this(null)

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
    var value: NativePtr?,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(null)

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
    var value: NativePtr?,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(null)

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
    var value: NativePtr?,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(null)

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
    var value: NativePtr?,
    actual override val buffer: NativeBuffer? = null,
) : ResourceHandle {

    actual constructor() : this(null)

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
    var value: NativePtr?,
    actual override val buffer: NativeBuffer? = null,
) : ResourceHandle {

    actual constructor() : this(null)

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
    var value: NativePtr?,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(null)

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
    var value: COpaquePointer?,
    actual override val buffer: NativeBuffer? = null,
) : INativeData {

    actual constructor() : this(null)

    actual override fun sizeBytes(layout: MemoryLayout): Int = Long.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(value.rawValue)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        value = buffer.nextLong()
        return this
    }

}