package com.cws.std.memory

class NativeDataList(
    private val layout: MemoryLayout,
    capacity: Int,
    private val factory: () -> INativeData,
): NativeList(capacity, factory().sizeBytes(layout)) {

    private var currentIndex = 0

    fun add(data: INativeData) {
        data.serialize(this)
    }

    operator fun set(index: Int, data: INativeData) {
        currentIndex = index * data.sizeBytes(layout)
        add(data)
    }

    operator fun get(index: Int): INativeData {
        return factory().deserialize(this@NativeDataList)
    }

    fun setBoolean(value: Boolean) {
        setBoolean(currentIndex, value)
        currentIndex += value.sizeBytes(layout)
    }

    fun setShort(value: Short) {
        setShort(currentIndex, value)
        currentIndex += value.sizeBytes(layout)
    }

    fun setInt(value: Int) {
        setInt(currentIndex, value)
        currentIndex += value.sizeBytes(layout)
    }

    fun setFloat(value: Float) {
        setFloat(currentIndex, value)
        currentIndex += value.sizeBytes(layout)
    }

    fun setLong(value: Long) {
        setLong(currentIndex, value)
        currentIndex += value.sizeBytes(layout)
    }

    fun setDouble(value: Double) {
        setDouble(currentIndex, value)
        currentIndex += value.sizeBytes(layout)
    }

    fun set(data: INativeData) {
        data.serialize(this)
        currentIndex += data.sizeBytes(layout)
    }

    fun getBoolean(): Boolean {
        val value = getBoolean(currentIndex)
        currentIndex += value.sizeBytes(layout)
        return value
    }

    fun getShort(): Short {
        val value = getShort(currentIndex)
        currentIndex += value.sizeBytes(layout)
        return value
    }

    fun getInt(): Int {
        val value = getInt(currentIndex)
        currentIndex += value.sizeBytes(layout)
        return value
    }

    fun getFloat(): Float {
        val value = getFloat(currentIndex)
        currentIndex += value.sizeBytes(layout)
        return value
    }

    fun getLong(): Long {
        val value = getLong(currentIndex)
        currentIndex += value.sizeBytes(layout)
        return value
    }

    fun getDouble(): Double {
        val value = getDouble(currentIndex)
        currentIndex += value.sizeBytes(layout)
        return value
    }

    fun <T : INativeData> get(data: INativeData): T {
        val newData = data.deserialize(this)
        currentIndex += data.sizeBytes(layout)
        return newData as T
    }

}
