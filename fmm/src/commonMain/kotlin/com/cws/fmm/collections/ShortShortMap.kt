package com.cws.fmm.collections

class ShortShortMap(capacity: Int) {

    private val keys = ShortList(capacity)
    private val values = ShortList(capacity)
    private val used = BooleanList(capacity)

    val size: Int = keys.size

    fun clear() {
        keys.clear()
        values.clear()
        used.clear()
    }

    operator fun set(key: Short, value: Short) {
        val id = (key.hashCode() and Int.MAX_VALUE) % keys.size
        keys[id] = key
        values[id] = value
        used[id] = true
    }

    operator fun get(key: Short, default: Short): Short {
        val id = (key.hashCode() and Int.MAX_VALUE) % keys.size
        return if (used[id] && keys[id] == key) values[id] else default
    }

    fun contains(key: Short): Boolean {
        val id = (key.hashCode() and Int.MAX_VALUE) % keys.size
        return used[id] && keys[id] == key
    }

}