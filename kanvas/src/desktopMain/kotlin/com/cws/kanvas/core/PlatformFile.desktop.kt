package com.cws.kanvas.core

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

actual class PlatformFile actual constructor(filepath: String) : AutoCloseable {

    actual val size: Int
        get() = file?.length()?.toInt() ?: 0

    actual val isOpened: Boolean
        get() = file != null

    private var file: File? = null
    private var outputStream: FileOutputStream? = null
    private var inputStream: FileInputStream? = null

    init {
        open(filepath)
    }

    actual override fun close() {
        outputStream?.close()
        inputStream?.close()
        outputStream = null
        inputStream = null
        file = null
    }

    actual fun open(filepath: String) {
        file = try {
            File(filepath)
        } catch (e: FileNotFoundException) {
            null
        }
        outputStream = file?.outputStream()
        inputStream = file?.inputStream()
    }

    actual fun write(bytes: ByteArray, offset: Int, size: Int): Int {
        outputStream?.write(bytes, offset, size)
        return size
    }

    actual fun read(bytes: ByteArray, offset: Int, size: Int): Int {
        return inputStream?.read(bytes, offset, size) ?: 0
    }

    actual fun flush() {
        // no-op
    }

}