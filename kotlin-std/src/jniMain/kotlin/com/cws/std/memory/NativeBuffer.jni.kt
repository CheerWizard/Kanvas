package com.cws.std.memory

import java.nio.ByteBuffer

actual open class NativeBuffer actual constructor(capacity: Int) {

    constructor(buffer: ByteBuffer) : this(buffer.capacity()) {
        this.buffer = buffer
    }

    constructor(ptr: Long, capacity: Int) : this(capacity) {
        this.buffer = CMemory.toByteBuffer(ptr, capacity) ?: throw RuntimeException("Failed to allocate for NativeBuffer $capacity bytes from ptr $ptr")
    }

    var buffer: ByteBuffer = CMemory.malloc(capacity)
        ?: throw RuntimeException("Failed to allocate for NativeBuffer $capacity bytes")

    actual val address: Long = CMemory.addressOf(buffer)

    actual var position: Int
        set(value) {
            buffer.position(value)
        }
        get() = buffer.position()

    actual val capacity: Int get() = buffer.capacity()
    val size: Int get() = buffer.remaining()

    actual fun release() {
        CMemory.free(buffer)
    }

    actual fun view(): Any = buffer

    actual fun resize(newCapacity: Int) {
        buffer = CMemory.realloc(buffer, newCapacity)
            ?: throw RuntimeException("Failed to reallocate for NativeBuffer $newCapacity bytes")
    }

    actual fun clear() {
        buffer.clear()
    }

    actual fun flip() {
        buffer.flip()
    }

    actual fun setByte(index: Int, value: Byte) {
        buffer.put(index, value)
    }

    actual fun getByte(index: Int): Byte = buffer.get(index)

    actual fun copyTo(
        dest: NativeBuffer,
        srcIndex: Int,
        destIndex: Int,
        size: Int,
    ) {
        val srcSlice = buffer.duplicate()
        srcSlice.position(srcIndex)
        srcSlice.limit(srcIndex + size)
        val destLastPosition = dest.position
        dest.position = destIndex
        dest.buffer.put(srcSlice)
        dest.position = destLastPosition
    }

    actual fun setBytes(index: Int, bytes: ByteArray) {
        buffer.put(bytes, index, bytes.size)
    }

    actual fun clone(): NativeBuffer {
        return NativeBuffer(capacity)
    }

    actual fun setTo(value: Byte, destIndex: Int, size: Int) {
        repeat(size) { i -> buffer.put(destIndex + i, value) }
    }

    actual fun setInt(index: Int, value: Int) {
        buffer.putInt(index, value)
    }

    actual fun getInt(index: Int): Int {
        return buffer.getInt(index)
    }

    actual fun setFloat(index: Int, value: Float) {
        buffer.putFloat(index, value)
    }

    actual fun getFloat(index: Int): Float {
        return buffer.getFloat(index)
    }

    actual fun push(value: Byte) {
        buffer.put(value)
    }

    actual fun pushInt(value: Int) {
        buffer.putInt(value)
    }

    actual fun pushFloat(value: Float) {
        buffer.putFloat(value)
    }

    actual fun pushLong(value: Long) {
        buffer.putLong(value)
    }

    actual fun pop(): Byte = buffer.get()

    actual fun popInt(): Int = buffer.int

    actual fun popFloat(): Float = buffer.float

    actual fun popLong(): Long = buffer.long

}