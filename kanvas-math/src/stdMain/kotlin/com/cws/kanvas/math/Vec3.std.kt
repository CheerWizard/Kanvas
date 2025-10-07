package com.cws.kanvas.math

import com.cws.fmm.StackMemory

data class StdVec3(
    var _x: Float = 0f,
    var _y: Float = 0f,
    var _z: Float = 0f,
) : Vec3

actual fun Vec3(): Vec3 = StdVec3()

actual fun Vec3(x: Float, y: Float, z: Float): Vec3 = StdVec3(x, y, z)

actual fun StackMemory.Vec3(): Vec3 = StdVec3()

actual fun StackMemory.Vec3(x: Float, y: Float, z: Float): Vec3 = StdVec3(x, y, z)

actual fun Vec3.clone(): Vec3 {
    this as StdVec3
    return copy()
}

actual var Vec3.x: Float
    get() = (this as StdVec3)._x
    set(value) { (this as StdVec3)._x = value }

actual var Vec3.y: Float
    get() = (this as StdVec3)._y
    set(value) { (this as StdVec3)._y = value }

actual var Vec3.z: Float
    get() = (this as StdVec3)._z
    set(value) { (this as StdVec3)._z = value }

actual operator fun Vec3.component1(): Float {
    this as StdVec3
    return _x
}

actual operator fun Vec3.component2(): Float {
    this as StdVec3
    return _y
}

actual operator fun Vec3.component3(): Float {
    this as StdVec3
    return _z
}

actual operator fun Vec3.get(i: Int): Float {
    this as StdVec3
    return when (i) {
        0 -> _x
        1 -> _y
        2 -> _z
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 2]")
    }
}

actual operator fun Vec3.set(i: Int, v: Float) {
    this as StdVec3
    return when (i) {
        0 -> _x = v
        1 -> _y = v
        2 -> _z = v
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 2]")
    }
}