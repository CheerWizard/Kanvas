package com.cws.kanvas.vk

import kotlin.random.Random

class IntList(
    val buffer: IntArray,
) {

    constructor(
        capacity: Int = 0,
        init: (Int) -> Int = { -1 },
    ) : this(IntArray(capacity, init))

    private var position: Int = 0

    val size: Int get() = position

    val indices: IntRange get() = buffer.indices

    val lastIndex: Int get() = buffer.lastIndex

    fun isEmpty(): Boolean {
        return position == 0
    }

    fun first(): Int = buffer.first()

    inline fun first(crossinline predicate: (Int) -> Boolean): Int {
        return buffer.first(predicate)
    }

    fun last(): Int = buffer.last()

    inline fun last(crossinline predicate: (Int) -> Boolean): Int {
        return buffer.last(predicate)
    }

    fun clone(): IntList = IntList(buffer.size)

    fun add(value: Int) {
        buffer[position++] = value
    }

    operator fun set(index: Int, value: Int) {
        buffer[index] = value
    }

    operator fun get(index: Int): Int = buffer[index]

    fun push(element: Int) {
        add(element)
    }

    fun pop(): Int {
        if (position > 0) {
            return buffer[position--]
        } else {
            throw IndexOutOfBoundsException("list position <= 0")
        }
    }

    fun clear() {
        position = 0
    }

    inline fun forEachIndexed(crossinline block: (i: Int, v: Int) -> Unit) {
        buffer.forEachIndexed(block)
    }

    inline fun forEach(crossinline block: (v: Int) -> Unit) {
        buffer.forEach(block)
    }

    inline fun filterIndexed(crossinline block: (i: Int, v: Int) -> Boolean): IntList {
        val result = IntList(buffer.size)
        buffer.forEachIndexed { i, v ->
            if (block(i, v)) {
                result.add(v)
            }
        }
        return result
    }

    inline fun filter(crossinline block: (v: Int) -> Boolean): IntList {
        val result = IntList(buffer.size)
        buffer.forEach {
            if (block(it)) {
                result.add(it)
            }
        }
        return result
    }

    inline fun findIndexed(default: Int, crossinline block: (i: Int, v: Int) -> Boolean): Int {
        buffer.forEachIndexed { i, v ->
            if (block(i, v)) return v
        }
        return default
    }

    inline fun find(default: Int, crossinline block: (v: Int) -> Boolean): Int {
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

    fun sorted(): IntList {
        val result = IntList(buffer.size)
        result.sort()
        return result
    }

    fun sortedDescending(): IntList {
        val result = IntList(buffer.size)
        result.sortedDescending()
        return result
    }

    inline fun sortWith(crossinline comparator: (v1: Int, v2: Int) -> Int) {
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

    inline fun sortedWith(crossinline comparator: (v1: Int, v2: Int) -> Int): IntList {
        return IntList(buffer.size).apply {
        sortWith(comparator)
    }
    }

    inline fun sortBy(crossinline selector: (v: Int) -> Int) {
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

    inline fun sortedBy(crossinline selector: (v: Int) -> Int): IntList {
        return IntList(buffer.size).apply {
        sortBy(selector)
    }
    }

    fun slice(indices: IntRange): IntList {
        return IntList(buffer.sliceArray(indices))
    }

    fun sum(): Int {
        return buffer.sum()
    }

    inline fun sumOf(crossinline selector: (Int) -> Int): Int {
        return buffer.sumOf(selector)
    }

    fun shuffle(random: Random = Random) {
        buffer.shuffle(random)
    }

    fun shuffled(random: Random = Random): IntList {
        return IntList(buffer.size).apply {
        shuffle(random)
    }
    }

    fun fill(element: Int, fromIndex: Int = 0, toIndex: Int = size) {
        buffer.fill(element, fromIndex, toIndex)
    }

}