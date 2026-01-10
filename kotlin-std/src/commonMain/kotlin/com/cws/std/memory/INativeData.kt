package com.cws.std.memory

interface INativeData {
    fun sizeBytes(layout: MemoryLayout): Int
    fun serialize(list: NativeDataList, layout: MemoryLayout)
    fun deserialize(list: NativeDataList, layout: MemoryLayout): INativeData
}
