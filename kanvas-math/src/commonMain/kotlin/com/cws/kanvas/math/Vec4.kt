package com.cws.kanvas.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeDataList
import com.cws.std.memory.STD140_SIZE_BYTES
import com.cws.std.memory.STD430_SIZE_BYTES
import com.cws.std.memory.Stack
import com.cws.std.memory.stackPush
import kotlin.math.sqrt

// Used to generate fresh version
//@FastObject
//class _Vec4(
//    var x: Float,
//    var y: Float,
//    var z: Float,
//    var w: Float
//)

interface Vec4 : INativeData {

    companion object {
        val SIZE_BYTES = Float.SIZE_BYTES * 4
        val STD140_SIZE_BYTES = Float.STD140_SIZE_BYTES * 4
        val STD430_SIZE_BYTES = Float.STD430_SIZE_BYTES * 4
    }

    override fun sizeBytes(layout: MemoryLayout): Int {
        return when (layout) {
            MemoryLayout.KOTLIN -> SIZE_BYTES
            MemoryLayout.STD140 -> STD140_SIZE_BYTES
            MemoryLayout.STD430 -> STD430_SIZE_BYTES
        }
    }

    override fun serialize(list: NativeDataList) {
        list.setFloat(x)
        list.setFloat(y)
        list.setFloat(z)
        list.setFloat(w)
    }

    override fun deserialize(list: NativeDataList) = Vec4(
        list.getFloat(),
        list.getFloat(),
        list.getFloat(),
        list.getFloat(),
    )

}

expect fun Vec4(): Vec4
expect fun Vec4(x: Float, y: Float, z: Float, w: Float): Vec4

expect fun Stack.Vec4(): Vec4
expect fun Stack.Vec4(x: Float, y: Float, z: Float, w: Float): Vec4

expect fun Vec4.clone(): Vec4

expect var Vec4.x: Float
expect var Vec4.y: Float
expect var Vec4.z: Float
expect var Vec4.w: Float

expect operator fun Vec4.component1(): Float
expect operator fun Vec4.component2(): Float
expect operator fun Vec4.component3(): Float
expect operator fun Vec4.component4(): Float

expect operator fun Vec4.get(i: Int): Float
expect operator fun Vec4.set(i: Int, v: Float)

val Vec4.length: Float get() {
    val x = x
    val y = y
    val z = z
    val w = w
    return sqrt(x * x + y * y + z * z + w * w)
}

fun Vec4.normalize(): Vec4 {
    return normalize(this, this)
}

operator fun Vec4.plus(v: Float): Vec4 = stackPush {
    Vec4(x + v, y + v, z + v, w + v)
}

operator fun Vec4.minus(v: Float): Vec4 = stackPush {
    Vec4(x - v, y - v, z - v, w - v)
}

operator fun Vec4.times(v: Float): Vec4 = stackPush {
    Vec4(x * v, y * v, z * v, w * v)
}

operator fun Vec4.div(v: Float): Vec4 = stackPush {
    Vec4(x / v, y / v, z / v, w / v)
}

operator fun Vec4.plus(v: Vec4): Vec4 = stackPush {
    Vec4(x + v.x, y + v.y, z + v.z, w + v.w)
}

operator fun Vec4.minus(v: Vec4): Vec4 = stackPush {
    Vec4(x - v.x, y - v.y, z - v.z, w - v.w)
}

operator fun Vec4.times(v: Vec4): Vec4 = stackPush {
    Vec4(x * v.x, y * v.y, z * v.z, w * v.w)
}

operator fun Vec4.div(v: Vec4): Vec4 = stackPush {
    Vec4(x / v.x, y / v.y, z / v.z, w / v.w)
}

operator fun Vec4.unaryMinus(): Vec4 = stackPush {
    Vec4(-x, -y, -z, -w)
}