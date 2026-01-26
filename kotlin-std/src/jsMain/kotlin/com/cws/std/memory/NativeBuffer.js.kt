package com.cws.std.memory

import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Float32Array
import org.khronos.webgl.Int32Array
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.khronos.webgl.set

actual open class NativeBuffer actual constructor(capacity: Int) {

    actual var position: Int
        set(value) {
            _position = value
        }
        get() = _position

    actual val capacity: Int get() = Module.HEAPU8.byteLength

    actual val address: Long get() = 0L

    var buffer = Module.HEAPU8.buffer
    protected var _position: Int = 0

    private var byteView = Uint8Array(buffer)
    private var intView = Int32Array(buffer)
    private var floatView = Float32Array(buffer)

    actual fun view(): Any = byteView

    actual fun resize(newCapacity: Int) {
        buffer = ArrayBuffer(newCapacity)
        byteView = Uint8Array(buffer)
        intView = Int32Array(buffer)
        floatView = Float32Array(buffer)
    }

    actual fun clear() {
        position = 0
    }

    actual fun flip() {
        position = 0
    }

    actual fun setBytes(index: Int, bytes: ByteArray) {
        byteView.set(bytes.toTypedArray())
    }

    actual fun clone(): NativeBuffer {
        return NativeBuffer(capacity)
    }

    actual fun release() = Unit

    actual fun copyTo(
        dest: NativeBuffer,
        srcIndex: Int,
        destIndex: Int,
        size: Int,
    ) {
        val byteView = dest.view() as Uint8Array
        byteView.set(this.byteView.subarray(srcIndex, srcIndex + size), destIndex)
    }

    actual fun setTo(value: Byte, destIndex: Int, size: Int) {
        repeat(size) { i -> byteView[destIndex + i] = value }
    }

    actual fun setByte(index: Int, value: Byte) {
        byteView[index] = value
    }

    actual fun getByte(index: Int): Byte {
        return byteView[index]
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

    actual fun pop(): Byte = byteView[position--]

    actual fun popInt(): Int = intView[position--]

    actual fun popFloat(): Float = floatView[position--]

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