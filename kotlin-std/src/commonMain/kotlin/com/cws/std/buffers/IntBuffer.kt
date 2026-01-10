package com.cws.std.buffers

expect class IntBuffer(capacity: Int) {

    var position: Int
    val capacity: Int

    fun view(): Any

    fun release()

    fun resize(newCapacity: Int)

    fun copyTo(
        dest: IntBuffer,
        srcIndex: Int,
        destIndex: Int,
        size: Int,
    )

    fun setTo(value: Int, destIndex: Int, size: Int)

    fun setArray(index: Int, intArray: IntArray)

    operator fun set(index: Int, value: Int)
    operator fun get(index: Int): Int

}