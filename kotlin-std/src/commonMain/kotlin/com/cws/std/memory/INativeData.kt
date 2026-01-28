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
