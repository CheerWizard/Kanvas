package com.cws.std.memory

data class PackedDataList<T : IPackedData>(
    val list: MutableList<T>,
    override val buffer: NativeBuffer?,
) : IPackedData {

    val address: Long get() = buffer?.address ?: 0L

    constructor(typeSize: Int, values: MutableList<T>) : this(values, NativeBuffer(typeSize * values.size)) {
        pack()
    }

    override fun pack(buffer: NativeBuffer?): NativeBuffer? {
        list.forEach { it.pack(buffer) }
        return buffer
    }

}