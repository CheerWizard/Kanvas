package com.cws.kanvas.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.NativeDataList
import com.cws.std.memory.Stack
import com.cws.std.memory.next
import com.cws.std.memory.push
import com.cws.std.memory.stackPush

// Used to generate fresh version
//@FastObject
//class _Mat3(
//    var v1: Vec3,
//    var v2: Vec3
//    var v3: Vec3
//)

interface Mat3 : INativeData {

    companion object {
        val SIZE_BYTES = Vec3.SIZE_BYTES * 3
        val STD140_SIZE_BYTES = Vec3.STD140_SIZE_BYTES * 3
        val STD430_SIZE_BYTES = Vec3.STD430_SIZE_BYTES * 3
    }

    override fun sizeBytes(layout: MemoryLayout): Int {
        return when (layout) {
            MemoryLayout.KOTLIN -> SIZE_BYTES
            MemoryLayout.STD140 -> STD140_SIZE_BYTES
            MemoryLayout.STD430 -> STD430_SIZE_BYTES
        }
    }

    override fun serialize(buffer: NativeBuffer) {
        buffer.push(v1)
        buffer.push(v2)
        buffer.push(v3)
    }

    override fun deserialize(buffer: NativeBuffer) = Mat3(
        buffer.next(v1),
        buffer.next(v2),
        buffer.next(v3),
    )

}

expect fun Mat3(): Mat3
expect fun Mat3(
    m00: Float,
    m01: Float,
    m02: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m20: Float,
    m21: Float,
    m22: Float,
): Mat3
expect fun Mat3(v1: Vec3, v2: Vec3, v3: Vec3): Mat3

expect fun Stack.Mat3(): Mat3
expect fun Stack.Mat3(
    m00: Float,
    m01: Float,
    m02: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m20: Float,
    m21: Float,
    m22: Float,
): Mat3
expect fun Stack.Mat3(v1: Vec3, v2: Vec3, v3: Vec3): Mat3

expect fun Mat3.reset()

expect fun Mat3.clone(stackScope: Boolean): Mat3

expect var Mat3.v1: Vec3
expect var Mat3.v2: Vec3
expect var Mat3.v3: Vec3

expect operator fun Mat3.component1(): Vec3
expect operator fun Mat3.component2(): Vec3
expect operator fun Mat3.component3(): Vec3

expect operator fun Mat3.get(i: Int): Vec3

fun Mat3.identity(): Mat3 {
    reset()
    get(0)[0] = 1f
    get(1)[1] = 1f
    get(2)[2] = 1f
    return this
}

fun Mat3.transpose(): Mat3 = transpose(this, this)

fun Mat3.inverse(): Mat3 = inverse(this, this)

operator fun Mat3.minus(v: Float): Mat3 = stackPush {
    Mat3(v1 - v, v2 - v, v3 - v)
}

operator fun Mat3.times(v: Float): Mat3 = stackPush {
    Mat3(v1 * v, v2 * v, v3 * v)
}

operator fun Mat3.div(v: Float): Mat3 = stackPush {
    Mat3(v1 / v, v2 / v, v3 / v)
}

operator fun Mat3.plus(m: Mat3): Mat3 = stackPush {
    Mat3(v1 + m.v1, v2 + m.v2, v3 + m.v3)
}

operator fun Mat3.minus(m: Mat3): Mat3 = stackPush {
    Mat3(v1 - m.v1, v2 - m.v2, v3 - m.v3)
}

operator fun Mat3.div(m: Mat3): Mat3 = stackPush {
    Mat3(v1 / m.v1, v2 / m.v2, v3 / m.v3)
}

operator fun Mat3.times(m: Mat3): Mat3 = stackPush {
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

operator fun Mat3.unaryMinus(): Mat3 = stackPush {
    val m1 = this@unaryMinus
    val m2 = Mat3()
    for (r in 0..2) {
        for (c in 0..2) {
            m2[r][c] = -m1[r][c]
        }
    }
    return m2
}