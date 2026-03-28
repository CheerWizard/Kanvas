package com.cws.std.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.STD140_SIZE_BYTES
import com.cws.std.memory.STD430_SIZE_BYTES
import com.cws.std.memory.nextFloat
import com.cws.std.memory.pushFloat
import kotlin.math.sqrt

data class Vec3(
    var x: Float = 0f,
    var y: Float = 0f,
    var z: Float = 0f,
) : INativeData {

    companion object {
        val SIZE_BYTES = Float.SIZE_BYTES * 3
        val STD140_SIZE_BYTES = Float.STD140_SIZE_BYTES * 3
        val STD430_SIZE_BYTES = Float.STD430_SIZE_BYTES * 3
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
        buffer.pushFloat(z)
    }

    override fun unpack(buffer: NativeBuffer) = Vec3(
        buffer.nextFloat(),
        buffer.nextFloat(),
        buffer.nextFloat(),
    )

    operator fun get(i: Int): Float {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw IndexOutOfBoundsException("i=$i out of range [0, 2]")
        }
    }

    operator fun set(i: Int, v: Float) {
        return when (i) {
            0 -> x = v
            1 -> y = v
            2 -> z = v
            else -> throw IndexOutOfBoundsException("i=$i out of range [0, 2]")
        }
    }

}

val Vec3.length: Float get() {
    val x = x
    val y = y
    val z = z
    return sqrt(x * x + y * y + z * z)
}

fun Vec3.normalize(): Vec3 {
    return normalize(this, this)
}

operator fun Vec3.plus(v: Float): Vec3 {
    return Vec3(x + v, y + v, z + v)
}

operator fun Vec3.minus(v: Float): Vec3 {
    return Vec3(x - v, y - v, z - v)
}

operator fun Vec3.times(v: Float): Vec3 {
    return Vec3(x * v, y * v, z * v)
}

operator fun Vec3.div(v: Float): Vec3 {
    return Vec3(x / v, y / v, z / v)
}

operator fun Vec3.plus(v: Vec3): Vec3 {
    return Vec3(x + v.x, y + v.y, z + v.z)
}

operator fun Vec3.minus(v: Vec3): Vec3 {
    return Vec3(x - v.x, y - v.y, z - v.z)
}

operator fun Vec3.times(v: Vec3): Vec3 {
    return Vec3(x * v.x, y * v.y, z * v.z)
}

operator fun Vec3.div(v: Vec3): Vec3 {
    return Vec3(x / v.x, y / v.y, z / v.z)
}

operator fun Vec3.unaryMinus(): Vec3 {
    return Vec3(-x, -y, -z)
}