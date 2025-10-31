package com.cws.kanvas.math

import com.cws.fmm.HeapMemory
import com.cws.fmm.profile
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

data class TestVec2(
    val x: Float = 0f,
    val y: Float = 0f
) {
    operator fun plus(v: TestVec2): TestVec2 = TestVec2(x + v.x, y + v.y)
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

    private var v1Heap = TestVec2()
    private var v2Heap = TestVec2()
    private var vHeap = TestVec2()

    private var v1 = Vec2()
    private var v2 = Vec2()
    private var v = Vec2()

    @Test
    fun testFmmPerformance() {
        val fmmDuration = profile {
            repeat(1000000) { i ->
                v1 = Vec2(i + Random.nextFloat(), i + Random.nextFloat())
                v2 = Vec2(i + Random.nextFloat(), i + Random.nextFloat())
                v = v1 + v2
            }
        }
        println("FMM duration: $fmmDuration")
    }

    @Test
    fun testHeapPerformance() {
        val heapDuration = profile {
            repeat(1000000) { i ->
                v1Heap = TestVec2(i + Random.nextFloat(), i + Random.nextFloat())
                v2Heap = TestVec2(i + Random.nextFloat(), i + Random.nextFloat())
                vHeap = v1Heap + v2Heap
            }
        }
        println("Heap duration: $heapDuration")
    }

}