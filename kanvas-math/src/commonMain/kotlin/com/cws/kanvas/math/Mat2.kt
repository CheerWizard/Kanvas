package com.cws.kanvas.math

import com.cws.fmm.StackMemory
import com.cws.fmm.stackPush

// Used to generate fresh version
//@FastObject
//class _Mat2(
//    var v1: Vec2,
//    var v2: Vec2
//)

interface Mat2

expect fun Mat2(): Mat2
expect fun Mat2(
    m00: Float,
    m01: Float,
    m10: Float,
    m11: Float
): Mat2
expect fun Mat2(v1: Vec2, v2: Vec2): Mat2

expect fun StackMemory.Mat2(): Mat2
expect fun StackMemory.Mat2(
    m00: Float,
    m01: Float,
    m10: Float,
    m11: Float
): Mat2
expect fun StackMemory.Mat2(v1: Vec2, v2: Vec2): Mat2

expect fun Mat2.reset()

expect fun Mat2.clone(stackScope: Boolean): Mat2

expect var Mat2.v1: Vec2
expect var Mat2.v2: Vec2

expect operator fun Mat2.component1(): Vec2
expect operator fun Mat2.component2(): Vec2

expect operator fun Mat2.get(i: Int): Vec2

fun Mat2.identity(): Mat2 {
    reset()
    get(0)[0] = 1f
    get(1)[1] = 1f
    return this
}

fun Mat2.transpose(): Mat2 = transpose(this, this)

fun Mat2.inverse(): Mat2 = inverse(this, this)

operator fun Mat2.minus(v: Float): Mat2 = stackPush {
    Mat2(v1 - v, v2 - v)
}

operator fun Mat2.times(v: Float): Mat2 = stackPush {
    Mat2(v1 * v, v2 * v)
}

operator fun Mat2.div(v: Float): Mat2 = stackPush {
    Mat2(v1 / v, v2 / v)
}

operator fun Mat2.plus(m: Mat2): Mat2 = stackPush {
    Mat2(v1 + m.v1, v2 + m.v2)
}

operator fun Mat2.minus(m: Mat2): Mat2 = stackPush {
    Mat2(v1 - m.v1, v2 - m.v2)
}

operator fun Mat2.div(m: Mat2): Mat2 = stackPush {
    Mat2(v1 / m.v1, v2 / m.v2)
}

operator fun Mat2.times(m: Mat2): Mat2 = stackPush {
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

operator fun Mat2.unaryMinus(): Mat2 = stackPush {
    val m1 = this@unaryMinus
    val m2 = Mat2()
    for (r in 0..1) {
        for (c in 0..1) {
            m2[r][c] = -m1[r][c]
        }
    }
    return m2
}