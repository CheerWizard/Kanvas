package com.cws.kanvas.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.STD140_SIZE_BYTES
import com.cws.std.memory.STD430_SIZE_BYTES
import com.cws.std.memory.Stack
import com.cws.std.memory.nextFloat
import com.cws.std.memory.pushFloat
import com.cws.std.memory.stackPush
import kotlin.math.sqrt

// Used to generate fresh version
//@FastObject
//class _Vec3(
//    var x: Float,
//    var y: Float,
//    var z: Float
//)

interface Vec3 : INativeData {

    companion object {
        val SIZE_BYTES = Float.SIZE_BYTES * 3
        val STD140_SIZE_BYTES = Float.STD140_SIZE_BYTES * 3
        val STD430_SIZE_BYTES = Float.STD430_SIZE_BYTES * 3
    }

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

}

expect fun Vec3(): Vec3
expect fun Vec3(x: Float, y: Float, z: Float): Vec3

expect fun Stack.Vec3(): Vec3
expect fun Stack.Vec3(x: Float, y: Float, z: Float): Vec3

expect fun Vec3.clone(): Vec3

expect var Vec3.x: Float
expect var Vec3.y: Float
expect var Vec3.z: Float

expect operator fun Vec3.component1(): Float
expect operator fun Vec3.component2(): Float
expect operator fun Vec3.component3(): Float

expect operator fun Vec3.get(i: Int): Float
expect operator fun Vec3.set(i: Int, v: Float)

val Vec3.length: Float get() {
    val x = x
    val y = y
    val z = z
    return sqrt(x * x + y * y + z * z)
}

fun Vec3.normalize(): Vec3 {
    return normalize(this, this)
}

operator fun Vec3.plus(v: Float): Vec3 = stackPush {
    Vec3(x + v, y + v, z + v)
}

operator fun Vec3.minus(v: Float): Vec3 = stackPush {
    Vec3(x - v, y - v, z - v)
}

operator fun Vec3.times(v: Float): Vec3 = stackPush {
    Vec3(x * v, y * v, z * v)
}

operator fun Vec3.div(v: Float): Vec3 = stackPush {
    Vec3(x / v, y / v, z / v)
}

operator fun Vec3.plus(v: Vec3): Vec3 = stackPush {
    Vec3(x + v.x, y + v.y, z + v.z)
}

operator fun Vec3.minus(v: Vec3): Vec3 = stackPush {
    Vec3(x - v.x, y - v.y, z - v.z)
}

operator fun Vec3.times(v: Vec3): Vec3 = stackPush {
    Vec3(x * v.x, y * v.y, z * v.z)
}

operator fun Vec3.div(v: Vec3): Vec3 = stackPush {
    Vec3(x / v.x, y / v.y, z / v.z)
}

operator fun Vec3.unaryMinus(): Vec3 = stackPush {
    Vec3(-x, -y, -z)
}