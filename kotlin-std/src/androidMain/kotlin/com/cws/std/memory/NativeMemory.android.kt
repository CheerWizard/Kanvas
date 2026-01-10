package com.cws.std.memory

actual open class NativeMemory {

    actual fun init() {
        System.loadLibrary("cmemory")
    }

}