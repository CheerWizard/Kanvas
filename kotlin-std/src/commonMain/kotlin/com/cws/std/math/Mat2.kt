package com.cws.std.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.next
import com.cws.std.memory.push

data class Mat2(
    var v1: Vec2 = Vec2(),
    var v2: Vec2 = Vec2(),
) : INativeData {

    companion object {
        val SIZE_BYTES = Vec2.SIZE_BYTES * 2
        val STD140_SIZE_BYTES = Vec2.STD140_SIZE_BYTES * 2
        val STD430_SIZE_BYTES = Vec2.STD430_SIZE_BYTES * 2
    }

    override val buffer: NativeBuffer?
        get() = null

    override fun sizeBytes(layout: MemoryLayout): Int {
        return when (layout) {
            MemoryLayout.KOTLIN -> SIZE_BYTES
            MemoryLayout.STD140 -> STD140_SIZE_BYTES
            MemoryLayout.STD430 -> STD430_SIZE_BYTES
        }
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.push(v1)
        buffer.push(v2)
    }

    override fun unpack(buffer: NativeBuffer) = Mat2(
        buffer.next(v1),
        buffer.next(v2),
    )

    operator fun get(i: Int): Vec2 {
        return when (i) {
            0 -> v1
            1 -> v2
            else -> throw IndexOutOfBoundsException("i=$i out of range [0, 1]")
        }
    }

}

fun Mat2.identity(): Mat2 {
    v1.x = 1f
    v1.y = 0f
    v2.x = 0f
    v2.y = 1f
    return this
}

fun Mat2.transpose(): Mat2 = transpose(this, this)

fun Mat2.inverse(): Mat2 = inverse(this, this)

operator fun Mat2.minus(v: Float): Mat2 {
    return Mat2(v1 - v, v2 - v)
}

operator fun Mat2.times(v: Float): Mat2 {
    return Mat2(v1 * v, v2 * v)
}

operator fun Mat2.div(v: Float): Mat2 {
    return Mat2(v1 / v, v2 / v)
}

operator fun Mat2.plus(m: Mat2): Mat2 {
    return Mat2(v1 + m.v1, v2 + m.v2)
}

operator fun Mat2.minus(m: Mat2): Mat2 {
    return Mat2(v1 - m.v1, v2 - m.v2)
}

operator fun Mat2.div(m: Mat2): Mat2 {
    return Mat2(v1 / m.v1, v2 / m.v2)
}

operator fun Mat2.times(m: Mat2): Mat2 {
    val m1 = this@times
    val m2 = m
    val m3 = Mat2()
    for (r in 0..1) {
        for (c in 0..1) {
            for (i in 0..1) {
                m3[r][c] += m1[r][i] * m2[i][c]
            }
        }
    }
    return m3
}

operator fun Mat2.unaryMinus(): Mat2 {
    val m1 = this@unaryMinus
    val m2 = Mat2()
    for (r in 0..1) {
        for (c in 0..1) {
            m2[r][c] = -m1[r][c]
        }
    }
    return m2
}