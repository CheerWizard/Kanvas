package com.cws.fmm

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.get
import kotlinx.cinterop.plus
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.toCValues
import platform.posix.free
import platform.posix.malloc
import platform.posix.memcpy
import platform.posix.memset
import platform.posix.realloc

@OptIn(ExperimentalForeignApi::class)
actual class FastBuffer actual constructor(capacity: Int) {

    actual var position: Int
        set(value) {
            _position = value
        }
        get() = _position

    actual val capacity: Int get() = _capacity

    var buffer: CPointer<ByteVar> = malloc(capacity.toULong()) as CPointer<ByteVar>
    private var _position = 0
    private var _capacity = capacity

    private var intView: CPointer<IntVar> = buffer.reinterpret()
    private var floatView: CPointer<FloatVar> = buffer.reinterpret()

    actual fun release() {
        free(buffer)
        _position = 0
        _capacity = 0
    }

    actual fun view(): Any = buffer

    actual fun resize(newCapacity: Int) {
        buffer = realloc(buffer, newCapacity.toULong()) as CPointer<ByteVar>
        _capacity = newCapacity
        intView = buffer.reinterpret()
        floatView = buffer.reinterpret()
    }

    actual fun copyTo(
        dest: FastBuffer,
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

    actual fun setBytes(index: Int, bytes: ByteArray) {
        memcpy(
            buffer + index,
            bytes.toCValues(),
            bytes.size.toULong()
        )
    }

    actual fun clone(): FastBuffer {
        return FastBuffer(capacity)
    }

    actual fun setTo(value: Byte, destIndex: Int, size: Int) {
        memset(buffer + destIndex, value.toInt(), size.toULong())
    }

    actual fun setByte(index: Int, value: Byte) {
        buffer[index] = value
    }

    actual fun getByte(index: Int): Byte {
        return buffer[index]
    }

    actual fun setInt(index: Int, value: Int) {
        intView[index] = value
    }

    actual fun getInt(index: Int): Int {
        return intView[index]
    }

    actual fun setFloat(index: Int, value: Float) {
        floatView[index] = value
    }

    actual fun getFloat(index: Int): Float {
        return floatView[index]
    }

}