package com.cws.std.memory

interface INativeData {
    fun sizeBytes(layout: MemoryLayout): Int
    fun serialize(list: NativeDataList)
    fun deserialize(list: NativeDataList): INativeData
}
