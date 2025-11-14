package com.cws.kanvas.core

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.pin
import platform.posix.__sFILE
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fread
import platform.posix.fwrite

@OptIn(ExperimentalForeignApi::class)
actual class PlatformFile actual constructor(filepath: String) : AutoCloseable {

    init {
        open(filepath)
    }

    private var file: CPointer<__sFILE>? = null

    actual override fun close() {
        fclose(file)
    }

    actual fun open(filepath: String) {
        file = fopen(filepath, "rw")
    }

    actual fun write(bytes: ByteArray, offset: Int, size: Int): Int {
        if (file != null) {
            return fwrite(
                bytes.pin().addressOf(offset),
                1u,
                size.toULong(),
                file
            ).toInt()
        }
        return 0
    }

    actual fun read(bytes: ByteArray, offset: Int, size: Int): Int {
        if (file != null) {
            return fread(
                bytes.pin().addressOf(offset),
                1u,
                size.toULong(),
                file
            ).toInt()
        }
        return 0
    }

}