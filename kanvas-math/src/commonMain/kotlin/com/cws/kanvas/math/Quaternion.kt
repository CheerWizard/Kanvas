package com.cws.kanvas.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.NativeDataList
import com.cws.std.memory.Stack
import com.cws.std.memory.stackPush
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

// Used to generate fresh version
//@FastObject
//class _Quaternion(
//    var x: Float,
//    var y: Float,
//    var z: Float,
//    var w: Float
//)

interface Quaternion : INativeData {

    companion object {
        const val SIZE_BYTES = Float.SIZE_BYTES * 4
    }

    override val sizeBytes: Int get() = SIZE_BYTES

    override fun serialize(list: NativeDataList) {
        list.addFloat(x)
        list.addFloat(y)
        list.addFloat(z)
        list.addFloat(w)
    }

    override fun deserialize(list: NativeDataList) = Quaternion(
        list.getFloat(),
        list.getFloat(),
        list.getFloat(),
        list.getFloat(),
    )

}

expect fun Quaternion(): Quaternion
expect fun Quaternion(x: Float, y: Float, z: Float, w: Float): Quaternion

expect fun Stack.Quaternion(): Quaternion
expect fun Stack.Quaternion(x: Float, y: Float, z: Float, w: Float): Quaternion

expect fun Quaternion.clone(): Quaternion

expect var Quaternion.x: Float
expect var Quaternion.y: Float
expect var Quaternion.z: Float
expect var Quaternion.w: Float

expect operator fun Quaternion.component1(): Float
expect operator fun Quaternion.component2(): Float
expect operator fun Quaternion.component3(): Float
expect operator fun Quaternion.component4(): Float

expect operator fun Quaternion.get(i: Int): Float
expect operator fun Quaternion.set(i: Int, v: Float)

val Quaternion.length: Float get() {
    val x = x
    val y = y
    val z = z
    val w = w
    return sqrt(x * x + y * y + z * z + w * w)
}

fun Quaternion.normalize(): Quaternion {
    return normalize(this, this)
}

operator fun Quaternion.plus(v: Float): Quaternion = stackPush {
    Quaternion(x + v, y + v, z + v, w + v)
}

operator fun Quaternion.minus(v: Float): Quaternion = stackPush {
    Quaternion(x - v, y - v, z - v, w - v)
}

operator fun Quaternion.times(v: Float): Quaternion = stackPush {
    Quaternion(x * v, y * v, z * v, w * v)
}

operator fun Quaternion.div(v: Float): Quaternion = stackPush {
    Quaternion(x / v, y / v, z / v, w / v)
}

operator fun Quaternion.plus(v: Quaternion): Quaternion = stackPush {
    Quaternion(x + v.x, y + v.y, z + v.z, w + v.w)
}

operator fun Quaternion.minus(v: Quaternion): Quaternion = stackPush {
    Quaternion(x - v.x, y - v.y, z - v.z, w - v.w)
}

operator fun Quaternion.times(v: Quaternion): Quaternion = stackPush {
    Quaternion(
        w * v.x + x * v.w + y * v.z - z * v.y,
        w * v.y - x * v.z + y * v.w + z * v.x,
        w * v.z + x * v.y - y * v.x + z * v.w,
        w * v.w - x * v.x - y * v.y - z * v.z
    )
}

operator fun Quaternion.div(v: Quaternion): Quaternion = stackPush {
    Quaternion(x / v.x, y / v.y, z / v.z, w / v.w)
}

operator fun Quaternion.unaryMinus(): Quaternion = stackPush {
    Quaternion(-x, -y, -z, w)
}

fun Quaternion.fromAngle(nx: Float, ny: Float, nz: Float, r: Radians): Quaternion {
    x = nx * sin(r * 0.5f)
    y = ny * sin(r * 0.5f)
    z = nz * sin(r * 0.5f)
    w = cos(r * 0.5f)
    return this
}

fun Quaternion.fromAngle(n: Vec3, r: Radians): Quaternion = fromAngle(n.x, n.y, n.z, r)

fun Quaternion.rotate(n: Vec3): Quaternion = stackPush {
    val q = this@rotate
    val r = Quaternion()
    return q * r.fromAngle(n, 0f.radians) * -q
}

fun Quaternion.slerp(q: Quaternion, t: Float): Quaternion = stackPush {
    val q1 = this@slerp
    val q2 = q

    val dot = dot(q1, q2)
    var theta = acos(dot)
    if (theta < 0.0) {
        theta = -theta
    }

    val st = sin(theta)
    val sut = sin(t * theta)
    val sout = sin((1 - t) * theta)
    val coeff1 = sout / st
    val coeff2 = sut / st

    return Quaternion(
        coeff1 * q1.x + coeff2 * q2.x,
        coeff1 * q1.y + coeff2 * q2.y,
        coeff1 * q1.z + coeff2 * q2.z,
        coeff1 * q1.w + coeff2 * q2.w,
    ).normalize()
}
