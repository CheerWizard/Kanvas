package com.cws.kanvas.math

import com.cws.fmm.StackMemory

data class StdVec2(
    var _x: Float = 0f,
    var _y: Float = 0f
) : Vec2

actual fun Vec2(): Vec2 = StdVec2()

actual fun Vec2(x: Float, y: Float): Vec2 = StdVec2(x, y)

actual fun StackMemory.Vec2(): Vec2 = StdVec2()

actual fun StackMemory.Vec2(x: Float, y: Float): Vec2 = StdVec2(x, y)

actual fun Vec2.clone(): Vec2 {
    this as StdVec2
    return copy()
}

actual var Vec2.x: Float
    get() = (this as StdVec2)._x
    set(value) { (this as StdVec2)._x = value }

actual var Vec2.y: Float
    get() = (this as StdVec2)._y
    set(value) { (this as StdVec2)._y = value }

actual operator fun Vec2.component1(): Float {
    this as StdVec2
    return _x
}

actual operator fun Vec2.component2(): Float {
    this as StdVec2
    return _y
}

actual operator fun Vec2.get(i: Int): Float {
    this as StdVec2
    return when (i) {
        0 -> _x
        1 -> _y
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 1]")
    }
}

actual operator fun Vec2.set(i: Int, v: Float) {
    this as StdVec2
    return when (i) {
        0 -> _x = v
        1 -> _y = v
        else -> throw IndexOutOfBoundsException("i=$i out of range [0, 1]")
    }
}