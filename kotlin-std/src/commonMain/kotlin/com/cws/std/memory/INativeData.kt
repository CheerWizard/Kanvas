package com.cws.std.memory

interface INativeData {
    val buffer: NativeBuffer?
    fun sizeBytes(layout: MemoryLayout): Int
    fun pack(buffer: NativeBuffer)
    fun unpack(buffer: NativeBuffer): INativeData

    fun pack(): NativeBuffer? = buffer?.apply {
        clear()
        pack(buffer!!)
        flip()
    }

    fun release() {
        buffer?.release()
    }
}

fun INativeData?.pack(buffer: NativeBuffer) {
    if (this == null) {
        buffer.pushLong(0)
    } else {
        pack(buffer)
    }
}

fun INativeData?.unpack(buffer: NativeBuffer) {
    if (this == null) {
        buffer.popLong()
    } else {
        unpack(buffer)
    }
}
