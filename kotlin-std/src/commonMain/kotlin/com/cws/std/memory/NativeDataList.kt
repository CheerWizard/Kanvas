package com.cws.std.memory

class NativeDataList(
    private val layout: MemoryLayout,
    capacity: Int,
    private val factory: () -> INativeData,
) {



}
