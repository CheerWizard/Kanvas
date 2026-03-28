package com.cws.std.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.STD140_SIZE_BYTES
import com.cws.std.memory.STD430_SIZE_BYTES
import com.cws.std.memory.nextFloat
import com.cws.std.memory.pushFloat
import kotlin.math.sqrt

data class Vec2(
    var x: Float = 0f,
    var y: Float = 0f
): INativeData {

    companion object {
        val SIZE_BYTES = Float.SIZE_BYTES * 2
        val STD140_SIZE_BYTES = Float.STD140_SIZE_BYTES * 2
        val STD430_SIZE_BYTES = Float.STD430_SIZE_BYTES * 2
    }

    override val buffer: NativeBuffer?
        get() = null

    override fun sizeBytes(layout: MemoryLayout): Int {
        return when (layout) {
            MemoryLayout.KOTLIN -> SIZE_BYTES
            MemoryLayout.STD140 -> STD140_SIZE_BYTES
            MemoryLayout.STD430 -> STD430_SIZE_BYTES
        }
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.pushFloat(x)
        buffer.pushFloat(y)
    }

    override fun unpack(buffer: NativeBuffer) = Vec2(
        buffer.nextFloat(),
        buffer.nextFloat(),
    )

    operator fun get(i: Int): Float {
        return when (i) {
            0 -> x
            1 -> y
            else -> throw IndexOutOfBoundsException("i=$i out of range [0, 1]")
        }
    }

    operator fun set(i: Int, v: Float) {
        return when (i) {
            0 -> x = v
            1 -> y = v
            else -> throw IndexOutOfBoundsException("i=$i out of range [0, 1]")
        }
    }

}

val Vec2.length: Float get() {
    val x = x
    val y = y
    return sqrt(x * x + y * y)
}

fun Vec2.normalize(): Vec2 {
    return normalize(this, this)
}

operator fun Vec2.plus(v: Float): Vec2 {
    return Vec2(x + v, y + v)
}

operator fun Vec2.minus(v: Float): Vec2 {
    return Vec2(x - v, y - v)
}

operator fun Vec2.times(v: Float): Vec2 {
    return Vec2(x * v, y * v)
}

operator fun Vec2.div(v: Float): Vec2 {
    return Vec2(x / v, y / v)
}

operator fun Vec2.plus(v: Vec2): Vec2 {
    return Vec2(x + v.x, y + v.y)
}

operator fun Vec2.minus(v: Vec2): Vec2 {
    return Vec2(x - v.x, y - v.y)
}

operator fun Vec2.times(v: Vec2): Vec2 {
    return Vec2(x * v.x, y * v.y)
}

operator fun Vec2.div(v: Vec2): Vec2 {
    return Vec2(x / v.x, y / v.y)
}

operator fun Vec2.unaryMinus(): Vec2 {
    return Vec2(-x, -y)
}