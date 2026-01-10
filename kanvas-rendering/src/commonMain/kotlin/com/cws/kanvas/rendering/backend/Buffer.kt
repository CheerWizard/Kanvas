package com.cws.kanvas.rendering.backend

import com.cws.std.memory.NativeBuffer
import com.cws.kanvas.rendering.backend.MemoryType

expect enum class BufferUsage {
    TRANSFER_SRC,
    TRANSFER_DST,
    UNIFORM_TEXEL,
    STORAGE_TEXEL,
    UNIFORM_BUFFER,
    STORAGE_BUFFER,
    INDEX_BUFFER,
    VERTEX_BUFFER,
    INDIRECT_BUFFER;

    val value: Int
};

data class BufferConfig(
    val usages: Int,
    val size: Long,
    val memoryType: MemoryType,
)

expect class BufferHandle

expect open class Buffer(device: Device, config: BufferConfig) : Resource<BufferHandle, BufferConfig> {
    override fun onCreate()
    override fun onRelease()

    fun write(data: NativeBuffer, srcOffset: Int, destOffset: Int, size: Int)
    fun read(data: NativeBuffer, srcOffset: Int, destOffset: Int, size: Int)
}
