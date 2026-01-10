package com.cws.kanvas.math

import com.cws.std.memory.Stack

data class StdVec4(
    var _x: Float = 0f,
    var _y: Float = 0f,
    var _z: Float = 0f,
    var _w: Float = 0f,
) : Vec4

actual fun Vec4(): Vec4 = StdVec4()

actual fun Vec4(x: Float, y: Float, z: Float, w: Float): Vec4 = StdVec4(x, y, z, w)

actual fun Stack.Vec4(): Vec4 = StdVec4()

actual fun Stack.Vec4(x: Float, y: Float, z: Float, w: Float): Vec4 = StdVec4(x, y, z, w)

actual fun Vec4.clone(): Vec4 {
    this as StdVec4
    return copy()
}

actual var Vec4.x: Float
    get() = (this as StdVec4)._x
    set(value) { (this as StdVec4)._x = value }

actual var Vec4.y: Float
    get() = (this as StdVec4)._y
    set(value) { (this as StdVec4)._y = value }

actual var Vec4.z: Float
    get() = (this as StdVec4)._z
    set(value) { (this as StdVec4)._z = value }

actual var Vec4.w: Float
    get() = (this as StdVec4)._w
    set(value) { (this as StdVec4)._w = value }

actual operator fun Vec4.component1(): Float {
    this as StdVec4
    return _x
}

actual operator fun Vec4.component2(): Float {
    this as StdVec4
    return _y
}

actual operator fun Vec4.component3(): Float {
    this as StdVec4
    return _z
}

actual operator fun Vec4.component4(): Float {
    this as StdVec4
    return _w
}

actual operator fun Vec4.get(i: Int): Float {
    this as StdVec4
    return when (i) {
        0 -> _x
        1 -> _y
        2 -> _z
        3 -> _w
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 3]")
    }
}

actual operator fun Vec4.set(i: Int, v: Float) {
    this as StdVec4
    return when (i) {
        0 -> _x = v
        1 -> _y = v
        2 -> _z = v
        3 -> _w = v
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 3]")
    }
}