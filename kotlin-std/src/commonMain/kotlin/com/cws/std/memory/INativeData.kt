package com.cws.std.memory

interface INativeData {
    fun sizeBytes(layout: MemoryLayout): Int
    fun serialize(buffer: NativeBuffer)
    fun deserialize(buffer: NativeBuffer): INativeData
}
