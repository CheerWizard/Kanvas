package com.cws.kanvas.math

import com.cws.std.memory.Stack

data class StdMat4(
    var _v1: Vec4 = Vec4(),
    var _v2: Vec4 = Vec4(),
    var _v3: Vec4 = Vec4(),
    var _v4: Vec4 = Vec4(),
) : Mat4

actual fun Mat4(): Mat4 = StdMat4()

actual fun Mat4(
    m00: Float,
    m01: Float,
    m02: Float,
    m03: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m13: Float,
    m20: Float,
    m21: Float,
    m22: Float,
    m23: Float,
    m30: Float,
    m31: Float,
    m32: Float,
    m33: Float,
): Mat4 = StdMat4(
    Vec4(m00, m01, m02, m03),
    Vec4(m10, m11, m12, m13),
    Vec4(m20, m21, m22, m23),
    Vec4(m30, m31, m32, m33),
)

actual fun Mat4(v1: Vec4, v2: Vec4, v3: Vec4, v4: Vec4): Mat4 = StdMat4(v1, v2, v3, v4)

actual fun Stack.Mat4(): Mat4 = StdMat4()

actual fun Stack.Mat4(
    m00: Float,
    m01: Float,
    m02: Float,
    m03: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m13: Float,
    m20: Float,
    m21: Float,
    m22: Float,
    m23: Float,
    m30: Float,
    m31: Float,
    m32: Float,
    m33: Float,
): Mat4 = StdMat4(
    Vec4(m00, m01, m02, m03),
    Vec4(m10, m11, m12, m13),
    Vec4(m20, m21, m22, m23),
    Vec4(m30, m31, m32, m33),
)

actual fun Stack.Mat4(v1: Vec4, v2: Vec4, v3: Vec4, v4: Vec4): Mat4 = StdMat4(v1, v2, v3, v4)

actual fun Mat4.reset() {
    this as StdMat4

    _v1.x = 0f
    _v1.y = 0f
    _v1.z = 0f
    _v1.w = 0f

    _v2.x = 0f
    _v2.y = 0f
    _v2.z = 0f
    _v2.w = 0f

    _v3.x = 0f
    _v3.y = 0f
    _v3.z = 0f
    _v3.w = 0f

    _v4.x = 0f
    _v4.y = 0f
    _v4.z = 0f
    _v4.w = 0f
}

actual fun Mat4.clone(stackScope: Boolean): Mat4 {
    this as StdMat4
    return copy()
}

actual var Mat4.v1: Vec4
    get() = (this as StdMat4)._v1
    set(value) { (this as StdMat4)._v1 = value }

actual var Mat4.v2: Vec4
    get() = (this as StdMat4)._v2
    set(value) { (this as StdMat4)._v2 = value }

actual var Mat4.v3: Vec4
    get() = (this as StdMat4)._v3
    set(value) { (this as StdMat4)._v3 = value }

actual var Mat4.v4: Vec4
    get() = (this as StdMat4)._v4
    set(value) { (this as StdMat4)._v4 = value }

actual operator fun Mat4.component1(): Vec4 {
    this as StdMat4
    return _v1
}

actual operator fun Mat4.component2(): Vec4 {
    this as StdMat4
    return _v2
}

actual operator fun Mat4.component3(): Vec4 {
    this as StdMat4
    return _v3
}

actual operator fun Mat4.component4(): Vec4 {
    this as StdMat4
    return _v4
}

actual operator fun Mat4.get(i: Int): Vec4 {
    this as StdMat4
    return when (i) {
        0 -> _v1
        1 -> _v2
        2 -> _v3
        3 -> _v4
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 3]")
    }
}