package com.cws.kanvas.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.NativeDataList
import com.cws.std.memory.Stack
import com.cws.std.memory.stackPush
import kotlin.math.sqrt

// Used to generate fresh version
//@FastObject
//class _Vec2(
//    var x: Float,
//    var y: Float
//)

interface Vec2 : INativeData {

    companion object {
        const val SIZE_BYTES = Float.SIZE_BYTES * 2
    }

    override val sizeBytes: Int get() = SIZE_BYTES

    override fun serialize(list: NativeDataList) {
        list.addFloat(x)
        list.addFloat(y)
    }

    override fun deserialize(list: NativeDataList) = Vec2(
        list.getFloat(),
        list.getFloat(),
    )

}

expect fun Vec2(): Vec2
expect fun Vec2(x: Float, y: Float): Vec2

expect fun Stack.Vec2(): Vec2
expect fun Stack.Vec2(x: Float, y: Float): Vec2

expect fun Vec2.clone(): Vec2

expect var Vec2.x: Float
expect var Vec2.y: Float

expect operator fun Vec2.component1(): Float
expect operator fun Vec2.component2(): Float

expect operator fun Vec2.get(i: Int): Float
expect operator fun Vec2.set(i: Int, v: Float)

val Vec2.length: Float get() {
    val x = x
    val y = y
    return sqrt(x * x + y * y)
}

fun Vec2.normalize(): Vec2 {
    return normalize(this, this)
}

operator fun Vec2.plus(v: Float): Vec2 = stackPush {
    Vec2(x + v, y + v)
}

operator fun Vec2.minus(v: Float): Vec2 = stackPush {
    Vec2(x - v, y - v)
}

operator fun Vec2.times(v: Float): Vec2 = stackPush {
    Vec2(x * v, y * v)
}

operator fun Vec2.div(v: Float): Vec2 = stackPush {
    Vec2(x / v, y / v)
}

operator fun Vec2.plus(v: Vec2): Vec2 = stackPush {
    Vec2(x + v.x, y + v.y)
}

operator fun Vec2.minus(v: Vec2): Vec2 = stackPush {
    Vec2(x - v.x, y - v.y)
}

operator fun Vec2.times(v: Vec2): Vec2 = stackPush {
    Vec2(x * v.x, y * v.y)
}

operator fun Vec2.div(v: Vec2): Vec2 = stackPush {
    Vec2(x / v.x, y / v.y)
}

operator fun Vec2.unaryMinus(): Vec2 = stackPush {
    Vec2(-x, -y)
}