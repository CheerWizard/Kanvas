package com.cws.kanvas.core

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.pin
import platform.posix.__sFILE
import platform.posix.fclose
import platform.posix.feof
import platform.posix.fflush
import platform.posix.fopen
import platform.posix.fread
import platform.posix.fseek
import platform.posix.fwrite

@OptIn(ExperimentalForeignApi::class)
actual class PlatformFile actual constructor(filepath: String) : AutoCloseable {

    actual val size: Int get() {
        return if (file == null) 0 else feof(file)
    }

    actual val isOpened: Boolean
        get() = file != null

    init {
        open(filepath)
    }

    private var file: CPointer<__sFILE>? = null

    actual override fun close() {
        fclose(file)
        file = null
    }

    actual fun open(filepath: String) {
        file = fopen(filepath, "rw")
    }

    actual fun write(bytes: ByteArray, offset: Int, size: Int): Int {
        file?.let { file ->
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
        file?.let { file ->
            return fread(
                bytes.pin().addressOf(offset),
                1u,
                size.toULong(),
                file,
            ).toInt()
        }
        return 0
    }

    actual fun flush() {
        file?.let { file ->
            fflush(file)
        }
    }

}