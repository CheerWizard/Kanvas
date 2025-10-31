package com.cws.fmm.collections

import kotlin.random.Random

class ShortList(
    val buffer: ShortArray,
) {

    constructor(
        capacity: Int = 0,
        init: (Int) -> Short = { 0 }
    ) : this(ShortArray(capacity, init))

    private var position: Int = 0

    val size: Int get() = position

    val indices: IntRange get() = buffer.indices

    val lastIndex: Int get() = buffer.lastIndex

    fun isEmpty(): Boolean {
        return position == 0
    }

    fun first(): Short = buffer.first()

    inline fun first(crossinline predicate: (Short) -> Boolean): Short {
        return buffer.first(predicate)
    }

    fun last(): Short = buffer.last()

    inline fun last(crossinline predicate: (Short) -> Boolean): Short {
        return buffer.last(predicate)
    }

    fun clone(): ShortList = ShortList(buffer.size)

    fun add(value: Short) {
        buffer[position++] = value
    }

    operator fun set(index: Int, value: Short) {
        buffer[index] = value
    }

    operator fun get(index: Int): Short = buffer[index]

    fun clear() {
        position = 0
    }

    inline fun forEachIndexed(crossinline block: (i: Int, v: Short) -> Unit) {
        buffer.forEachIndexed(block)
    }

    inline fun forEach(crossinline block: (v: Short) -> Unit) {
        buffer.forEach(block)
    }

    inline fun filterIndexed(crossinline block: (i: Int, v: Short) -> Boolean): ShortList {
        val result = ShortList(buffer.size)
        buffer.forEachIndexed { i, v ->
            if (block(i, v)) {
                result.add(v)
            }
        }
        return result
    }

    inline fun filter(crossinline block: (v: Short) -> Boolean): ShortList {
        val result = ShortList(buffer.size)
        buffer.forEach {
            if (block(it)) {
                result.add(it)
            }
        }
        return result
    }

    inline fun findIndexed(default: Short, crossinline block: (i: Int, v: Short) -> Boolean): Short {
        buffer.forEachIndexed { i, v ->
            if (block(i, v)) return v
        }
        return default
    }

    inline fun find(default: Short, crossinline block: (v: Short) -> Boolean): Short {
        buffer.forEach {
            if (block(it)) return it
        }
        return default
    }

    fun sort() {
        buffer.sort()
    }

    fun sortDescending() {
        buffer.sortDescending()
    }

    fun sorted(): ShortList {
        val result = ShortList(buffer.size)
        result.sort()
        return result
    }

    fun sortedDescending(): ShortList {
        val result = ShortList(buffer.size)
        result.sortedDescending()
        return result
    }

    inline fun sortWith(crossinline comparator: (v1: Short, v2: Short) -> Int) {
        fun quicksort(from: Int, to: Int) {
            if (from >= to) return
            val pivot = this[(from + to) ushr 1]
            var i = from
            var j = to
            while (i <= j) {
                while (comparator(this[i], pivot) < 0) i++
                while (comparator(this[j], pivot) > 0) j--
                if (i <= j) {
                    val tmp = this[i]
                    this[i] = this[j]
                    this[j] = tmp
                    i++
                    j--
                }
            }
            if (from < j) quicksort(from, j)
            if (i < to) quicksort(i, to)
        }
        quicksort(0, buffer.lastIndex)
    }

    inline fun sortedWith(crossinline comparator: (v1: Short, v2: Short) -> Int): ShortList {
        return ShortList(buffer.size).apply {
            sortWith(comparator)
        }
    }

    inline fun sortBy(crossinline selector: (v: Short) -> Int) {
        fun quicksort(from: Int, to: Int) {
            if (from >= to) return
            val pivotKey = selector(this[(from + to) ushr 1])
            var i = from
            var j = to
            while (i <= j) {
                while (selector(this[i]) < pivotKey) i++
                while (selector(this[j]) > pivotKey) j--
                if (i <= j) {
                    val tmp = this[i]
                    this[i] = this[j]
                    this[j] = tmp
                    i++
                    j--
                }
            }
            if (from < j) quicksort(from, j)
            if (i < to) quicksort(i, to)
        }
        quicksort(0, buffer.lastIndex)
    }

    inline fun sortedBy(crossinline selector: (v: Short) -> Int): ShortList {
        return ShortList(buffer.size).apply {
            sortBy(selector)
        }
    }

    fun slice(indices: IntRange): ShortList {
        return ShortList(buffer.sliceArray(indices))
    }

    fun sum(): Int {
        return buffer.sum()
    }

    inline fun sumOf(crossinline selector: (Short) -> Int): Int {
        return buffer.sumOf(selector)
    }

    fun shuffle(random: Random = Random) {
        buffer.shuffle(random)
    }

    fun shuffled(random: Random = Random): ShortList {
        return ShortList(buffer.size).apply {
            shuffle(random)
        }
    }

    fun fill(element: Short, fromIndex: Int = 0, toIndex: Int = size) {
        buffer.fill(element, fromIndex, toIndex)
    }

}