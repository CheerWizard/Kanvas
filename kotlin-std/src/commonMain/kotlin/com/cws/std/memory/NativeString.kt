package com.cws.std.memory

import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.String
import io.ktor.utils.io.core.toByteArray

data class NativeString(
    var value: String = "",
    override val buffer: NativeBuffer? = null,
): INativeData {

    val address: Long get() = buffer?.address ?: 0L

    constructor(value: String) : this(value, NativeBuffer(value.length + 1)) {
        fill(value)
    }

    fun fill(value: String) {
        this.value = value
        buffer?.let {
            pack(buffer)
        }
    }

    operator fun set(i: Int, c: Char) {
        buffer?.setByte(i, c.code.toByte())
    }

    operator fun get(i: Int): Char {
        buffer ?: throw IndexOutOfBoundsException("CString buffer is null!")
        return buffer.getByte(i).toInt().toChar()
    }

    override fun toString(): String {
        return value
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.clear()
        buffer.pushBytes(value.toByteArray(Charsets.UTF_8))
        buffer.push(0)
        buffer.flip()
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        val chars = ByteArray(buffer.capacity)
        repeat(buffer.capacity) { i ->
            chars[i] = buffer.getByte(i)
        }
        value = String(chars, 0, buffer.capacity, Charsets.UTF_8)
        return this
    }

    override fun sizeBytes(layout: MemoryLayout): Int = value.length

}