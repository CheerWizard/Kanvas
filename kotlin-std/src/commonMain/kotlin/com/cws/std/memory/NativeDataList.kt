package com.cws.std.memory

data class NativeDataList<T : INativeData>(
    val list: MutableList<T>,
    override val buffer: NativeBuffer? = null,
) : INativeData {

    val address: Long get() = buffer?.address ?: 0L
    val count: Long get() = list.size.toLong()

    override fun sizeBytes(layout: MemoryLayout): Int = list.size * list.first().sizeBytes(layout)

    override fun pack(buffer: NativeBuffer) {
        list.forEach { it.pack(buffer) }
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        list.forEach { it.unpack(buffer) }
        return this
    }

}
