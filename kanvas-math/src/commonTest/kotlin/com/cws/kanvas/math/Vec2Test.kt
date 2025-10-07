package com.cws.kanvas.math

import com.cws.fmm.HeapMemory
import com.cws.fmm.stackScope
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

data class StdVec2(
    val x: Float = 0f,
    val y: Float = 0f
) {
    operator fun plus(v: StdVec2): StdVec2 = StdVec2(x + v.x, y + v.y)
}

class Vec2Test {

    private val heap = HeapMemory

    @Test
    fun testArithmetics() {
        val v1 = Vec2(0f, 1f)
        val v2 = Vec2(1f, 0f)
        val actual = v1 + v2
        val expected = Vec2(1f, 1f)
        assertTrue(expected.x == actual.x && expected.y == actual.y)
    }

    private var v1Heap = Vec2Heap()
    private var v2Heap = Vec2Heap()
    private var vHeap = Vec2Heap()

    private var v1 = Vec2()
    private var v2 = Vec2()
    private var v = Vec2()

    private inline fun measure(function: () -> Unit): Long {
        val start = System.nanoTime()
        function()
        val end = System.nanoTime()
        return end - start
    }

    @Test
    fun testFmmPerformance() {
        val fmmDuration = measure {
            stackScope {
                repeat(1000) { i ->
                    v1 = Vec2(i + Random.nextFloat(), i + Random.nextFloat())
                    v2 = Vec2(i + Random.nextFloat(), i + Random.nextFloat())
                    v = v1 + v2
//                    println("Vec2: x=${v.x}, y=${v.y}")
                }
            }
        }
        println("FMM duration: ${fmmDuration} nanos")
    }

    @Test
    fun testHeapPerformance() {
        val heapDuration = measure {
            repeat(1000) { i ->
                v1Heap = Vec2Heap(i + Random.nextFloat(), i + Random.nextFloat())
                v2Heap = Vec2Heap(i + Random.nextFloat(), i + Random.nextFloat())
                vHeap = v1Heap + v2Heap
//                println("Vec2Heap: x=${vHeap.x}, y=${vHeap.y}")
            }
        }
        println("Heap duration: ${heapDuration} nanos")
    }

}