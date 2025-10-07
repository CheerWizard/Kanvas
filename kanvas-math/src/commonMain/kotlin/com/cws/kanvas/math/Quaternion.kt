package com.cws.kanvas.math

import com.cws.fmm.HeapMemory
import com.cws.fmm.MemoryHandle
import com.cws.fmm.NULL
import com.cws.fmm.StackMemory
import com.cws.fmm.checkNotNull
import com.cws.fmm.stackPush
import kotlin.jvm.JvmInline
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

@JvmInline
value class Quaternion(
    val handle: MemoryHandle = create().handle,
) {
    var x: Float
        get() {
            handle.checkNotNull()
            return HeapMemory.getFloat(handle)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.setFloat(handle, value)
        }

    var y: Float
        get() {
            handle.checkNotNull()
            return HeapMemory.getFloat(handle + Float.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.setFloat(handle + Float.SIZE_BYTES, value)
        }

    var z: Float
        get() {
            handle.checkNotNull()
            return HeapMemory.getFloat(handle + Float.SIZE_BYTES + Float.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.setFloat(handle + Float.SIZE_BYTES + Float.SIZE_BYTES, value)
        }

    var w: Float
        get() {
            handle.checkNotNull()
            return HeapMemory.getFloat(handle + Float.SIZE_BYTES + Float.SIZE_BYTES + Float.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.setFloat(handle + Float.SIZE_BYTES + Float.SIZE_BYTES + Float.SIZE_BYTES, value)
        }

    constructor(
        x: Float,
        y: Float,
        z: Float,
        w: Float,
        handle: Int = create().handle,
    ) : this(handle) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    fun free(): Quaternion {
        HeapMemory.free(handle, SIZE_BYTES)
        return Quaternion(NULL)
    }

    fun clone(): Quaternion {
        val clone = Quaternion()
        HeapMemory.copy(handle, clone.handle, SIZE_BYTES)
        return clone
    }

    infix fun `=`(other: Quaternion) {
        HeapMemory.copy(other.handle, handle, SIZE_BYTES)
    }

    companion object {
        const val SIZE_BYTES: Int = Float.SIZE_BYTES + Float.SIZE_BYTES + Float.SIZE_BYTES +
                Float.SIZE_BYTES

        fun create(): Quaternion = Quaternion(HeapMemory.allocate(SIZE_BYTES))
    }

    operator fun component1() = x
    operator fun component2() = y
    operator fun component3() = z
    operator fun component4() = w

    fun fromAngle(nx: Float, ny: Float, nz: Float, r: Radians): Quaternion {
        x = nx * sin(r * 0.5f)
        y = ny * sin(r * 0.5f)
        z = nz * sin(r * 0.5f)
        w = cos(r * 0.5f)
        return this
    }

    fun fromAngle(n: Vec3, r: Radians): Quaternion = fromAngle(n.x, n.y, n.z, r)

    fun length(): Float {
        val x = x
        val y = y
        val z = z
        return sqrt(x * x + y * y + z * z)
    }

    fun normalize(): Quaternion = normalize(this, this)

    fun rotate(n: Vec3): Quaternion = stackPush {
        val q = this@Quaternion
        val r = Quaternion()
        return q * r.fromAngle(n, 0f.radians) * -q
    }

    fun slerp(q: Quaternion, t: Float): Quaternion = stackPush {
        val q1 = this@Quaternion
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

    operator fun plus(v: Float): Quaternion = stackPush {
        Quaternion(x + v, y + v, z + v, w + v)
    }

    operator fun minus(v: Float): Quaternion = stackPush {
        Quaternion(x - v, y - v, z - v, w - v)
    }

    operator fun times(v: Float): Quaternion = stackPush {
        Quaternion(x * v, y * v, z * v, w * v)
    }

    operator fun div(v: Float): Quaternion = stackPush {
        Quaternion(x / v, y / v, z / v, w / v)
    }

    operator fun plus(v: Quaternion): Quaternion = stackPush {
        Quaternion(x + v.x, y + v.y, z + v.z, w + v.w)
    }

    operator fun minus(v: Quaternion): Quaternion = stackPush {
        Quaternion(x - v.x, y - v.y, z - v.z, w - v.w)
    }

    operator fun times(v: Quaternion): Quaternion = stackPush {
        Quaternion(
            w * v.x + x * v.w + y * v.z - z * v.y,
            w * v.y - x * v.z + y * v.w + z * v.x,
            w * v.z + x * v.y - y * v.x + z * v.w,
            w * v.w - x * v.x - y * v.y - z * v.z
        )
    }

    operator fun div(v: Quaternion): Quaternion = stackPush {
        Quaternion(x / v.x, y / v.y, z / v.z, w / v.w)
    }

    operator fun unaryMinus(): Quaternion = stackPush {
        Quaternion(-x, -y, -z, w)
    }

}

fun StackMemory.Quaternion(): Quaternion = Quaternion(push(Quaternion.SIZE_BYTES))

fun StackMemory.Quaternion(
    x: Float,
    y: Float,
    z: Float,
    w: Float,
): Quaternion = Quaternion(x,y,z,w, push(Quaternion.SIZE_BYTES))