package com.cws.kanvas.core.async

import kotlinx.atomicfu.atomic

class ConcurrentQueue<T>(private val capacity: Int) {

    private val buffer = Array<Any?>(capacity) { null }
    private val head = atomic(0)
    private val tail = atomic(0)

    fun push(data: T): Boolean {
        while (true) {
            val currentHead = head.value
            val nextHead = (currentHead + 1) % capacity

            if (nextHead == tail.value) {
                return false
            }

            if (head.compareAndSet(currentHead, nextHead)) {
                buffer[nextHead] = data
                return true
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun pop(): T? {
        while (true) {
            val currentTail = tail.value
            if (currentTail == head.value) {
                return null
            }

            val nextTail = (currentTail + 1) % capacity
            val value = buffer[currentTail] as T
            if (tail.compareAndSet(currentTail, nextTail)) {
                buffer[nextTail] = null
                return value
            }
        }
    }

    fun isEmpty(): Boolean = head.value == tail.value

    fun isFull(): Boolean = (head.value + 1) % capacity == tail.value

    fun size(): Int = capacity

}
