package com.cws.std.memory

expect open class NativeBuffer(capacity: Int) {

    var position: Int
    val capacity: Int
    val address: Long

    fun view(): Any

    fun release()

    fun resize(newCapacity: Int)

    fun clear()

    fun flip()

    fun setBytes(index: Int, bytes: ByteArray)

    fun clone(): NativeBuffer

    fun copyTo(
        dest: NativeBuffer,
        srcIndex: Int,
        destIndex: Int,
        size: Int,
    )

    fun setTo(value: Byte, destIndex: Int, size: Int)

    fun setByte(index: Int, value: Byte)
    fun getByte(index: Int): Byte

    fun setInt(index: Int, value: Int)
    fun getInt(index: Int): Int

    fun setFloat(index: Int, value: Float)
    fun getFloat(index: Int): Float

    fun push(value: Byte)
    fun pushInt(value: Int)
    fun pushFloat(value: Float)
    fun pushLong(value: Long)

    fun pop(): Byte
    fun popInt(): Int
    fun popFloat(): Float
    fun popLong(): Long

}

val NativeBuffer.byte: Byte get() = pop()
val NativeBuffer.int: Int get() = popInt()
val NativeBuffer.float: Float get() = popFloat()
val NativeBuffer.long: Long get() = popLong()