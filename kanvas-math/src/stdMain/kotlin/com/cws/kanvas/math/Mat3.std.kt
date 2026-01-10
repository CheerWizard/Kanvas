package com.cws.kanvas.math

import com.cws.std.memory.Stack

data class StdMat3(
    var _v1: Vec3 = Vec3(),
    var _v2: Vec3 = Vec3(),
    var _v3: Vec3 = Vec3(),
) : Mat3

actual fun Mat3(): Mat3 = StdMat3()

actual fun Mat3(
    m00: Float,
    m01: Float,
    m02: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m20: Float,
    m21: Float,
    m22: Float
): Mat3 = StdMat3(
    Vec3(m00, m01, m02),
    Vec3(m10, m11, m12),
    Vec3(m20, m21, m22)
)

actual fun Mat3(v1: Vec3, v2: Vec3, v3: Vec3): Mat3 = StdMat3(v1, v2, v3)

actual fun Stack.Mat3(): Mat3 = StdMat3()

actual fun Stack.Mat3(
    m00: Float,
    m01: Float,
    m02: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m20: Float,
    m21: Float,
    m22: Float
): Mat3 = StdMat3(
    Vec3(m00, m01, m02),
    Vec3(m10, m11, m12),
    Vec3(m20, m21, m22)
)

actual fun Stack.Mat3(v1: Vec3, v2: Vec3, v3: Vec3): Mat3 = StdMat3(v1, v2, v3)

actual fun Mat3.reset() {
    this as StdMat3

    _v1.x = 0f
    _v1.y = 0f
    _v1.z = 0f

    _v2.x = 0f
    _v2.y = 0f
    _v2.z = 0f

    _v3.x = 0f
    _v3.y = 0f
    _v3.z = 0f
}

actual fun Mat3.clone(stackScope: Boolean): Mat3 {
    this as StdMat3
    return copy()
}

actual var Mat3.v1: Vec3
    get() = (this as StdMat3)._v1
    set(value) { (this as StdMat3)._v1 = value }

actual var Mat3.v2: Vec3
    get() = (this as StdMat3)._v2
    set(value) { (this as StdMat3)._v2 = value }

actual var Mat3.v3: Vec3
    get() = (this as StdMat3)._v3
    set(value) { (this as StdMat3)._v3 = value }

actual operator fun Mat3.component1(): Vec3 {
    this as StdMat3
    return _v1
}

actual operator fun Mat3.component2(): Vec3 {
    this as StdMat3
    return _v2
}

actual operator fun Mat3.component3(): Vec3 {
    this as StdMat3
    return _v3
}

actual operator fun Mat3.get(i: Int): Vec3 {
    this as StdMat3
    return when (i) {
        0 -> _v1
        1 -> _v2
        2 -> _v3
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 2]")
    }
}