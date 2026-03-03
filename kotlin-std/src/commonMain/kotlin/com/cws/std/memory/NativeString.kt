package com.cws.std.memory

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
        buffer ?: throw IndexOutOfBoundsException("NativeString buffer is null!")
        return buffer.getByte(i).toInt().toChar()
    }

    override fun toString(): String {
        return value
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.clear()
        buffer.pushBytes(value.encodeToByteArray())
        buffer.push(0)
        buffer.flip()
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        val chars = CharArray(buffer.capacity)
        repeat(buffer.capacity - 1) { i ->
            chars[i] = buffer.pop().toInt().toChar()
        }
        buffer.pop() // pop null-terminated char
        value = chars.concatToString(0, buffer.capacity)
        return this
    }

    override fun sizeBytes(layout: MemoryLayout): Int = value.length

}