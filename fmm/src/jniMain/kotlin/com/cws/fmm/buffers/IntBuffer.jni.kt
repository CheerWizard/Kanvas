package com.cws.fmm.buffers

import com.cws.fmm.CMemory
import java.nio.ByteBuffer

actual class IntBuffer actual constructor(capacity: Int) {

    var buffer: ByteBuffer = CMemory.malloc(capacity)
        ?: throw RuntimeException("Failed to allocate for NativeBuffer $capacity bytes")

    actual var position: Int
        set(value) {
            buffer.position(value)
        }
        get() = buffer.position()

    actual val capacity: Int get() = buffer.capacity()

    private var intView: java.nio.IntBuffer = buffer.asIntBuffer()

    actual fun release() {
        CMemory.free(buffer)
    }

    actual fun view(): Any = buffer

    actual fun resize(newCapacity: Int) {
        buffer = CMemory.realloc(buffer, newCapacity)
            ?: throw RuntimeException("Failed to reallocate for NativeBuffer $newCapacity bytes")
        intView = buffer.asIntBuffer()
    }

    actual fun copyTo(
        dest: IntBuffer,
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

    actual fun setArray(index: Int, intArray: IntArray) {
        intView.put(intArray, index, intArray.size)
    }

    actual fun setTo(value: Int, destIndex: Int, size: Int) {
        repeat(size) { i -> intView.put(destIndex + i, value) }
    }

    actual operator fun set(index: Int, value: Int) { intView.put(index, value) }

    actual operator fun get(index: Int): Int = intView.get(index)

}