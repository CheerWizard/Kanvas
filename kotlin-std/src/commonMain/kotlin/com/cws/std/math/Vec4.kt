package com.cws.std.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.STD140_SIZE_BYTES
import com.cws.std.memory.STD430_SIZE_BYTES
import com.cws.std.memory.nextFloat
import com.cws.std.memory.pushFloat
import kotlin.math.sqrt

data class Vec4(
    var x: Float = 0f,
    var y: Float = 0f,
    var z: Float = 0f,
    var w: Float = 0f,
) : INativeData {

    companion object {
        val SIZE_BYTES = Float.SIZE_BYTES * 4
        val STD140_SIZE_BYTES = Float.STD140_SIZE_BYTES * 4
        val STD430_SIZE_BYTES = Float.STD430_SIZE_BYTES * 4
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
        buffer.pushFloat(w)
    }

    override fun unpack(buffer: NativeBuffer) = Vec4(
        buffer.nextFloat(),
        buffer.nextFloat(),
        buffer.nextFloat(),
        buffer.nextFloat(),
    )

    operator fun get(i: Int): Float {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            3 -> w
            else -> throw IndexOutOfBoundsException("i=$i out of range [0, 3]")
        }
    }

    operator fun set(i: Int, v: Float) {
        return when (i) {
            0 -> x = v
            1 -> y = v
            2 -> z = v
            3 -> w = v
            else -> throw IndexOutOfBoundsException("i=$i out of range [0, 3]")
        }
    }

    val length: Float get() {
        val x = x
        val y = y
        val z = z
        val w = w
        return sqrt(x * x + y * y + z * z + w * w)
    }

    fun normalize(): Vec4 {
        return normalize(this, this)
    }

    operator fun plus(v: Float): Vec4 {
        return Vec4(x + v, y + v, z + v, w + v)
    }

    operator fun minus(v: Float): Vec4 {
        return Vec4(x - v, y - v, z - v, w - v)
    }

    operator fun times(v: Float): Vec4 {
        return Vec4(x * v, y * v, z * v, w * v)
    }

    operator fun div(v: Float): Vec4 {
        return Vec4(x / v, y / v, z / v, w / v)
    }

    operator fun plus(v: Vec4): Vec4 {
        return Vec4(x + v.x, y + v.y, z + v.z, w + v.w)
    }

    operator fun minus(v: Vec4): Vec4 {
        return Vec4(x - v.x, y - v.y, z - v.z, w - v.w)
    }

    operator fun times(v: Vec4): Vec4 {
        return Vec4(x * v.x, y * v.y, z * v.z, w * v.w)
    }

    operator fun div(v: Vec4): Vec4 {
        return Vec4(x / v.x, y / v.y, z / v.z, w / v.w)
    }

    operator fun unaryMinus(): Vec4 {
        return Vec4(-x, -y, -z, -w)
    }

}