package com.cws.kanvas.audio

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.set

class FloatRingBuffer(private val capacity: Int) {

    private val buffer = FloatArray(capacity)
    private var readPos = 0
    private var writePos = 0
    private var size = 0

    fun write(data: FloatArray, offset: Int, length: Int): Int {
        val writeSize = minOf(length, capacity - size)
        repeat(writeSize) { i ->
            buffer[(writePos + i) % capacity] = data[offset + i]
        }
        writePos = (writePos + writeSize) % capacity
        size += writeSize
        return writeSize
    }

    @OptIn(ExperimentalForeignApi::class)
    fun read(dst: CPointer<FloatVar>, count: Int): Int {
        val readSize = minOf(count, size)
        for (i in 0 until readSize) {
            dst[i] = buffer[(readPos + i) % capacity]
        }
        readPos = (readPos + readSize) % capacity
        size -= readSize
        return readSize
    }

    fun freeSize(): Int = capacity - size

}