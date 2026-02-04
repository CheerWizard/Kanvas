package com.cws.kanvas.rendering.backend

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer

interface ResourceHandle : INativeData

expect class BindingLayoutHandle() : INativeData {
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}

expect class BufferHandle() : ResourceHandle {
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}

expect class RenderContextHandle() : INativeData {
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}

expect class RenderPipelineHandle() : INativeData {
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}

expect class RenderTargetHandle() : INativeData {
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}

expect class SamplerHandle() : ResourceHandle {
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}

expect class TextureHandle() : ResourceHandle {
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}

expect class ShaderHandle() : INativeData {
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}

expect class CommandBufferHandle() : INativeData {
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}