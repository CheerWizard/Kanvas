package com.cws.fmm

import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Float32Array
import org.khronos.webgl.Int32Array
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.khronos.webgl.set

actual class FastBuffer actual constructor(capacity: Int) {

    actual var position: Int
        set(value) {
            _position = value
        }
        get() = _position

    actual val capacity: Int get() = buffer.byteLength

    var buffer = ArrayBuffer(capacity)
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

    actual fun setBytes(index: Int, bytes: ByteArray) {
        byteView.set(bytes.toTypedArray())
    }

    actual fun clone(): FastBuffer {
        return FastBuffer(capacity)
    }

    actual fun release() = Unit

    actual fun copyTo(
        dest: FastBuffer,
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

}