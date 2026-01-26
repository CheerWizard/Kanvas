package com.cws.std.memory

interface IPackedData {
    val buffer: NativeBuffer?

    fun pack(buffer: NativeBuffer?): NativeBuffer?

    fun pack(): NativeBuffer? = buffer?.apply {
        clear()
        pack(buffer)
        flip()
    }

    fun release() {
        buffer?.release()
    }
}