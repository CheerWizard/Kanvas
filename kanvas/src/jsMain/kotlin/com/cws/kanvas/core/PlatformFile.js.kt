package com.cws.kanvas.core

actual class PlatformFile actual constructor(filepath: String) : AutoCloseable {

    actual override fun close() {
    }

    actual fun open(filepath: String) {
    }

    actual fun write(bytes: ByteArray, offset: Int, size: Int): Int {

    }

    actual fun read(bytes: ByteArray, offset: Int, size: Int): Int {

    }

}