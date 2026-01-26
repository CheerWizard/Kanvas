package com.cws.std.memory

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.get
import kotlinx.cinterop.plus
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toCValues
import platform.posix.free
import platform.posix.malloc
import platform.posix.memcpy
import platform.posix.memset
import platform.posix.realloc

@OptIn(ExperimentalForeignApi::class)
actual open class NativeBuffer actual constructor(capacity: Int) {

    constructor(ptr: Long, capacity: Int) : this(capacity) {
        this.buffer = ptr.toCPointer<ByteVar>()
            ?: throw RuntimeException("Failed to convert Long ptr to Native CPointer!")
    }

    constructor(ptr: CPointer<ByteVar>, capacity: Int) : this(capacity) {
        this.buffer = ptr
    }

    actual var position: Int
        set(value) {
            _position = value
        }
        get() = _position

    actual val capacity: Int get() = _capacity
    var buffer: CPointer<ByteVar> = malloc(capacity.toULong()) as CPointer<ByteVar>
    actual val address: Long = buffer.rawValue.toLong()

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

    actual fun clear() {
        position = 0
    }

    actual fun flip() {
        position = 0
    }

    actual fun copyTo(
        dest: NativeBuffer,
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

    actual fun clone(): NativeBuffer {
        return NativeBuffer(capacity)
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

    actual fun push(value: Byte) {
        setByte(position++, value)
    }

    actual fun pushInt(value: Int) {
        setInt(position++, value)
    }

    actual fun pushFloat(value: Float) {
        setFloat(position++, value)
    }

    actual fun pushLong(value: Long) {
        packLong(position++, value)
    }

    actual fun pop(): Byte = getByte(position--)

    actual fun popInt(): Int = getInt(position--)

    actual fun popFloat(): Float = getFloat(position--)

    actual fun popLong(): Long {
        val long = unpackLong(position)
        position -= 2
        return long
    }

    private fun packLong(index: Int, value: Long) {
        val high = (value shr 32).toInt()
        val low = value.toInt()
        intView[index] = high
        intView[index + 1] = low
    }

    private fun unpackLong(index: Int): Long {
        val high = intView[index]
        val low = intView[index + 1]
        return (high.toLong() shl 32) or (low.toLong() and 0xFFFFFFFF)
    }

}