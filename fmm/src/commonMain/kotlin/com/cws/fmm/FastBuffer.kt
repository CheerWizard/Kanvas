package com.cws.fmm

expect class FastBuffer(capacity: Int) {

    var position: Int
    val capacity: Int

    fun view(): Any
    fun release()
    fun resize(newCapacity: Int)
    fun setBytes(index: Int, bytes: ByteArray)
    fun clone(): FastBuffer
    fun copyTo(
        dest: FastBuffer,
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

}