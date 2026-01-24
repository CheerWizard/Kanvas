package com.cws.std.memory

import java.nio.ByteBuffer

@JvmInline
value class CString(val buffer: ByteBuffer?) {

    val address: Long get() {
        return CMemory.addressOf(buffer ?: return 0L)
    }

    constructor(value: String) : this(ByteBuffer.allocateDirect(value.length + 1)) {
        fill(value)
    }

    fun fill(value: String) {
        buffer?.let {
            buffer.clear()
            buffer.put(value.toByteArray(Charsets.UTF_8))
            buffer.put(0)
            buffer.flip()
        }
    }

    operator fun set(i: Int, c: Char) {
        buffer?.put(i, c.code.toByte())
    }

    operator fun get(i: Int): Char {
        buffer ?: throw ArrayIndexOutOfBoundsException("CString buffer is null!")
        return buffer.get(i).toInt().toChar()
    }

    override fun toString(): String {
        buffer ?: return ""
        val array = ByteArray(buffer.remaining())
        buffer.get(array)
        buffer.rewind()
        return String(array, Charsets.UTF_8)
    }

}