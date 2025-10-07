package com.cws.fmm

import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

actual class FastBuffer actual constructor(capacity: Int) {

    var buffer: ByteBuffer = CMemory.malloc(capacity)
        ?: throw RuntimeException("Failed to allocate for NativeBuffer $capacity bytes")

    actual var position: Int
        set(value) {
            buffer.position(value)
        }
        get() = buffer.position()

    actual val capacity: Int get() = buffer.capacity()

    private var intView: IntBuffer = buffer.asIntBuffer()
    private var floatView: FloatBuffer = buffer.asFloatBuffer()

    actual fun release() {
        CMemory.free(buffer)
    }

    actual fun view(): Any = buffer

    actual fun resize(newCapacity: Int) {
        buffer = CMemory.realloc(buffer, newCapacity)
            ?: throw RuntimeException("Failed to reallocate for NativeBuffer $newCapacity bytes")
    }

    actual fun setByte(index: Int, value: Byte) {
        buffer.put(index, value)
    }

    actual fun getByte(index: Int): Byte = buffer.get(index)

    actual fun copyTo(
        dest: FastBuffer,
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

    actual fun clone(): FastBuffer {
        return FastBuffer(capacity)
    }

    actual fun setTo(value: Byte, destIndex: Int, size: Int) {
        repeat(size) { i -> buffer.put(destIndex + i, value) }
    }

    actual fun setInt(index: Int, value: Int) {
        intView.put(index, value)
    }

    actual fun getInt(index: Int): Int {
        return intView.get(index)
    }

    actual fun setFloat(index: Int, value: Float) {
        floatView.put(index, value)
    }

    actual fun getFloat(index: Int): Float {
        return floatView.get(index)
    }

}