package com.cws.std.buffers

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.get
import kotlinx.cinterop.plus
import kotlinx.cinterop.set
import kotlinx.cinterop.toCValues
import platform.posix.free
import platform.posix.malloc
import platform.posix.memcpy
import platform.posix.memset
import platform.posix.realloc

@OptIn(ExperimentalForeignApi::class)
actual class IntBuffer actual constructor(capacity: Int) {

    actual var position: Int
        set(value) {
            _position = value
        }
        get() = _position

    actual val capacity: Int get() = _capacity

    var buffer: CPointer<IntVar> = malloc(capacity.toULong()) as CPointer<IntVar>

    private var _position = 0
    private var _capacity = capacity

    actual fun release() {
        free(buffer)
        _position = 0
        _capacity = 0
    }

    actual fun view(): Any = buffer

    actual fun resize(newCapacity: Int) {
        buffer = realloc(buffer, newCapacity.toULong()) as CPointer<IntVar>
        _capacity = newCapacity
    }

    actual fun copyTo(
        dest: IntBuffer,
        srcIndex: Int,
        destIndex: Int,
        size: Int,
    ) {
        memcpy(
            dest.buffer + destIndex,
            buffer + srcIndex,
            size.toULong()
        )
    }

    actual fun setArray(index: Int, intArray: IntArray) {
        memcpy(
            buffer + index,
            intArray.toCValues(),
            (intArray.size * Int.SIZE_BYTES).toULong()
        )
    }

    actual fun setTo(value: Int, destIndex: Int, size: Int) {
        memset(buffer + destIndex, value, size.toULong())
    }

    actual operator fun set(index: Int, value: Int) { buffer[index] = value }

    actual operator fun get(index: Int): Int = buffer[index]

}