package com.cws.kanvas.math

import com.cws.std.memory.Stack

data class StdQuaternion(
    var _x: Float = 0f,
    var _y: Float = 0f,
    var _z: Float = 0f,
    var _w: Float = 0f,
) : Quaternion

actual fun Quaternion(): Quaternion = StdQuaternion()

actual fun Quaternion(x: Float, y: Float, z: Float, w: Float): Quaternion = StdQuaternion(x, y, z, w)

actual fun Stack.Quaternion(): Quaternion = StdQuaternion()

actual fun Stack.Quaternion(x: Float, y: Float, z: Float, w: Float): Quaternion = StdQuaternion(x, y, z, w)

actual fun Quaternion.clone(): Quaternion {
    this as StdQuaternion
    return copy()
}

actual var Quaternion.x: Float
    get() = (this as StdQuaternion)._x
    set(value) { (this as StdQuaternion)._x = value }

actual var Quaternion.y: Float
    get() = (this as StdQuaternion)._y
    set(value) { (this as StdQuaternion)._y = value }

actual var Quaternion.z: Float
    get() = (this as StdQuaternion)._z
    set(value) { (this as StdQuaternion)._z = value }

actual var Quaternion.w: Float
    get() = (this as StdQuaternion)._w
    set(value) { (this as StdQuaternion)._w = value }

actual operator fun Quaternion.component1(): Float {
    this as StdQuaternion
    return _x
}

actual operator fun Quaternion.component2(): Float {
    this as StdQuaternion
    return _y
}

actual operator fun Quaternion.component3(): Float {
    this as StdQuaternion
    return _z
}

actual operator fun Quaternion.component4(): Float {
    this as StdQuaternion
    return _w
}

actual operator fun Quaternion.get(i: Int): Float {
    this as StdQuaternion
    return when (i) {
        0 -> _x
        1 -> _y
        2 -> _z
        3 -> _w
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 3]")
    }
}

actual operator fun Quaternion.set(i: Int, v: Float) {
    this as StdQuaternion
    return when (i) {
        0 -> _x = v
        1 -> _y = v
        2 -> _z = v
        3 -> _w = v
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 3]")
    }
}