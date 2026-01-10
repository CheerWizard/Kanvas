package com.cws.kanvas.math

import com.cws.std.memory.Stack

data class StdMat2(
    var _v1: Vec2 = Vec2(),
    var _v2: Vec2 = Vec2(),
) : Mat2

actual fun Mat2(): Mat2 = StdMat2()

actual fun Mat2(
    m00: Float,
    m01: Float,
    m10: Float,
    m11: Float
): Mat2 = StdMat2(Vec2(m00, m01), Vec2(m10, m11))

actual fun Mat2(v1: Vec2, v2: Vec2): Mat2 = StdMat2(v1, v2)

actual fun Stack.Mat2(): Mat2 = StdMat2()

actual fun Stack.Mat2(
    m00: Float,
    m01: Float,
    m10: Float,
    m11: Float
): Mat2 = StdMat2(Vec2(m00, m01), Vec2(m10, m11))

actual fun Stack.Mat2(v1: Vec2, v2: Vec2): Mat2 = StdMat2(v1, v2)

actual fun Mat2.reset() {
    this as StdMat2

    _v1.x = 0f
    _v1.y = 0f

    _v2.x = 0f
    _v2.y = 0f
}

actual fun Mat2.clone(stackScope: Boolean): Mat2 {
    this as StdMat2
    return copy()
}

actual var Mat2.v1: Vec2
    get() = (this as StdMat2)._v1
    set(value) { (this as StdMat2)._v1 = value }

actual var Mat2.v2: Vec2
    get() = (this as StdMat2)._v2
    set(value) { (this as StdMat2)._v2 = value }

actual operator fun Mat2.component1(): Vec2 {
    this as StdMat2
    return _v1
}

actual operator fun Mat2.component2(): Vec2 {
    this as StdMat2
    return _v2
}

actual operator fun Mat2.get(i: Int): Vec2 {
    this as StdMat2
    return when (i) {
        0 -> _v1
        1 -> _v2
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 1]")
    }
}