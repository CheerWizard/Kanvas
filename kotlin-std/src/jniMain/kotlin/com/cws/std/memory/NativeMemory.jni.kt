package com.cws.std.memory

import java.nio.ByteBuffer

expect open class NativeMemory() {
    protected fun init()
}

object CMemory : NativeMemory() {

    init {
        init()
    }

    external fun malloc(size: Int): ByteBuffer?
    external fun free(buffer: ByteBuffer)
    external fun realloc(buffer: ByteBuffer, size: Int): ByteBuffer?
    external fun addressOf(buffer: ByteBuffer): Long
    external fun toByteBuffer(ptr: Long, capacity: Int): ByteBuffer?

}