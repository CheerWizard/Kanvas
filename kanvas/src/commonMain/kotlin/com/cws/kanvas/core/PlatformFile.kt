package com.cws.kanvas.core

expect class PlatformFile(filepath: String = "") : AutoCloseable {
    override fun close()
    fun open(filepath: String)
    fun write(bytes: ByteArray, offset: Int = 0, size: Int = bytes.size): Int
    fun read(bytes: ByteArray, offset: Int = 0, size: Int = bytes.size): Int
}
