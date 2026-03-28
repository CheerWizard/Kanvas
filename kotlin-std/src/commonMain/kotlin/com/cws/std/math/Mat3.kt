package com.cws.std.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.Stack
import com.cws.std.memory.next
import com.cws.std.memory.push
import com.cws.std.memory.stackPush

data class Mat3(
    var v1: Vec3 = Vec3(),
    var v2: Vec3 = Vec3(),
    var v3: Vec3 = Vec3(),
) : INativeData {

    companion object {
        val SIZE_BYTES = Vec3.SIZE_BYTES * 3
        val STD140_SIZE_BYTES = Vec3.STD140_SIZE_BYTES * 3
        val STD430_SIZE_BYTES = Vec3.STD430_SIZE_BYTES * 3
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
        buffer.push(v3)
    }

    override fun unpack(buffer: NativeBuffer) = Mat3(
        buffer.next(v1),
        buffer.next(v2),
        buffer.next(v3),
    )

    constructor(
        m00: Float,
        m01: Float,
        m02: Float,
        m10: Float,
        m11: Float,
        m12: Float,
        m20: Float,
        m21: Float,
        m22: Float
    ) : this() {
        v1.x = m00
        v1.y = m01
        v1.z = m02

        v2.x = m10
        v2.y = m11
        v2.z = m12

        v3.x = m20
        v3.y = m21
        v3.z = m22
    }

    operator fun get(i: Int): Vec3 {
        return when (i) {
            0 -> v1
            1 -> v2
            2 -> v3
            else -> throw IndexOutOfBoundsException("i=$i out of range [0, 2]")
        }
    }

}

fun Mat3.identity(): Mat3 {
    v1.x = 1f
    v1.y = 0f
    v1.z = 0f

    v2.x = 0f
    v2.y = 1f
    v2.z = 0f

    v3.x = 0f
    v3.y = 0f
    v3.z = 1f

    return this
}

fun Mat3.transpose(): Mat3 = transpose(this, this)

fun Mat3.inverse(): Mat3 = inverse(this, this)

operator fun Mat3.minus(v: Float): Mat3 {
    return Mat3(v1 - v, v2 - v, v3 - v)
}

operator fun Mat3.times(v: Float): Mat3 {
    return Mat3(v1 * v, v2 * v, v3 * v)
}

operator fun Mat3.div(v: Float): Mat3 {
    return Mat3(v1 / v, v2 / v, v3 / v)
}

operator fun Mat3.plus(m: Mat3): Mat3 {
    return Mat3(v1 + m.v1, v2 + m.v2, v3 + m.v3)
}

operator fun Mat3.minus(m: Mat3): Mat3 {
    return Mat3(v1 - m.v1, v2 - m.v2, v3 - m.v3)
}

operator fun Mat3.div(m: Mat3): Mat3 {
    return Mat3(v1 / m.v1, v2 / m.v2, v3 / m.v3)
}

operator fun Mat3.times(m: Mat3): Mat3 {
    val m1 = this@times
    val m2 = m
    val m3 = Mat3()
    for (r in 0..2) {
        for (c in 0..2) {
            for (i in 0..2) {
                m3[r][c] += m1[r][i] * m2[i][c]
            }
        }
    }
    return m3
}

operator fun Mat3.unaryMinus(): Mat3 {
    val m1 = this@unaryMinus
    val m2 = Mat3()
    for (r in 0..2) {
        for (c in 0..2) {
            m2[r][c] = -m1[r][c]
        }
    }
    return m2
}