package com.cws.kanvas.core

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

actual class PlatformFile actual constructor(filepath: String) : AutoCloseable {

    private var outputStream: FileOutputStream? = null
    private var inputStream: FileInputStream? = null

    init {
        open(filepath)
    }

    actual override fun close() {
        outputStream?.close()
        inputStream?.close()
    }

    actual fun open(filepath: String) {
        outputStream = try {
            File(filepath).outputStream()
        } catch (e: FileNotFoundException) {
            null
        }

        inputStream = try {
            File(filepath).inputStream()
        } catch (e: FileNotFoundException) {
            null
        }
    }

    actual fun write(bytes: ByteArray, offset: Int, size: Int): Int {
        outputStream?.write(bytes, offset, size)
        return size
    }

    actual fun read(bytes: ByteArray, offset: Int, size: Int): Int {
        return inputStream?.read(bytes, offset, size) ?: 0
    }

}