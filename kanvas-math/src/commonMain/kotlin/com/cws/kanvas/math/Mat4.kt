package com.cws.kanvas.math

import com.cws.fmm.HeapMemory
import com.cws.fmm.MemoryHandle
import com.cws.fmm.NULL
import com.cws.fmm.StackMemory
import com.cws.fmm.checkNotNull
import com.cws.fmm.stackPush
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

// Used to generate fresh version
//@FastObject
//class _Mat4(
//    var v1: Vec4,
//    var v2: Vec4,
//    var v3: Vec4,
//    var v4: Vec4
//)

@JvmInline
value class Mat4(
    val handle: MemoryHandle = create().handle,
) {
    var v1: Vec4
        get() {
            handle.checkNotNull()
            return Vec4(handle)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.copy(value.handle, handle, Vec4.SIZE_BYTES)
        }

    var v2: Vec4
        get() {
            handle.checkNotNull()
            return Vec4(handle + Vec4.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.copy(value.handle, handle + Vec4.SIZE_BYTES, Vec4.SIZE_BYTES)
        }

    var v3: Vec4
        get() {
            handle.checkNotNull()
            return Vec4(handle + Vec4.SIZE_BYTES + Vec4.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.copy(value.handle, handle + Vec4.SIZE_BYTES + Vec4.SIZE_BYTES, Vec4.SIZE_BYTES)
        }

    var v4: Vec4
        get() {
            handle.checkNotNull()
            return Vec4(handle + Vec4.SIZE_BYTES + Vec4.SIZE_BYTES + Vec4.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.copy(value.handle, handle + Vec4.SIZE_BYTES + Vec4.SIZE_BYTES + Vec4.SIZE_BYTES,
                Vec4.SIZE_BYTES)
        }

    constructor(
        m00: Float,
        m01: Float,
        m02: Float,
        m03: Float,
        m10: Float,
        m11: Float,
        m12: Float,
        m13: Float,
        m20: Float,
        m21: Float,
        m22: Float,
        m23: Float,
        m30: Float,
        m31: Float,
        m32: Float,
        m33: Float,
        handle: Int = Mat2.Companion.create().handle,
    ) : this(handle) {
        get(0)[0] = m00
        get(0)[1] = m01
        get(0)[2] = m02
        get(0)[3] = m03

        get(1)[0] = m10
        get(1)[1] = m11
        get(1)[2] = m12
        get(1)[3] = m13

        get(2)[0] = m20
        get(2)[1] = m21
        get(2)[2] = m22
        get(2)[3] = m23

        get(3)[0] = m30
        get(3)[1] = m31
        get(3)[2] = m32
        get(3)[3] = m33
    }

    constructor(
        v1: Vec4,
        v2: Vec4,
        v3: Vec4,
        v4: Vec4,
        handle: Int = create().handle,
    ) : this(handle) {
        this.v1 `=` v1
        this.v2 `=` v2
        this.v3 `=` v3
        this.v4 `=` v4
    }

    constructor(
        q: Quaternion,
        handle: MemoryHandle = create().handle
    ) : this(handle) {
        val xx = q.x * q.x
        val xy = q.x * q.y
        val xz = q.x * q.z
        val yy = q.y * q.y
        val zz = q.z * q.z
        val yz = q.y * q.z
        val wx = q.w * q.x
        val wy = q.w * q.y
        val wz = q.w * q.z

        get(0)[0] = 1.0f - 2.0f * (yy + zz);
        get(1)[0] = 2.0f * (xy - wz);
        get(2)[0] = 2.0f * (xz + wy);
        get(3)[0] = 0f

        get(0)[1] = 2.0f * (xy + wz);
        get(1)[1] = 1.0f - 2.0f * (xx + zz);
        get(2)[1] = 2.0f * (yz - wx);
        get(3)[1] = 0f

        get(0)[2] = 2.0f * (xz - wy);
        get(1)[2] = 2.0f * (yz + wx);
        get(2)[2] = 1.0f - 2.0f * (xx + yy);
        get(3)[2] = 0f

        get(0)[3] = 0f
        get(1)[3] = 0f
        get(2)[3] = 0f
        get(3)[3] = 1f
    }

    fun free(): Mat4 {
        HeapMemory.free(handle, SIZE_BYTES)
        return Mat4(NULL)
    }

    fun clone(stackScope: Boolean): Mat4 {
        val clone = if (stackScope) stackPush { Mat4() } else Mat4()
        HeapMemory.copy(handle, clone.handle, SIZE_BYTES)
        return clone
    }

    infix fun `=`(other: Mat4) {
        HeapMemory.copy(other.handle, handle, SIZE_BYTES)
    }

    companion object {
        const val SIZE_BYTES: Int = Vec4.SIZE_BYTES + Vec4.SIZE_BYTES + Vec4.SIZE_BYTES +
                Vec4.SIZE_BYTES

        fun create(): Mat4 = Mat4(HeapMemory.allocate(SIZE_BYTES))

        fun model(translation: Vec3, rx: Float, ry: Float, rz: Float, scalar: Vec3): Mat4 = stackPush {
            Mat4()
                .identity()
                .translate(translation)
                .rotate(rx, ry, rz, Vec3(1f, 1f, 1f))
                .scale(scalar)
        }

        fun model(translation: Vec3, quaternion: Quaternion, scalar: Vec3): Mat4 = stackPush {
            Mat4()
                .identity()
                .translate(translation)
                .rotate(quaternion)
                .scale(scalar)
        }

        fun rigid(translation: Vec3, rx: Float, ry: Float, rz: Float): Mat4 = stackPush {
            Mat4()
                .identity()
                .translate(translation)
                .rotate(rx, ry, rz, Vec3(1f, 1f, 1f))
        }

        fun rigid(translation: Vec3, quaternion: Quaternion): Mat4 = stackPush {
            Mat4()
                .identity()
                .translate(translation)
                .rotate(quaternion)
        }

        fun view(position: Vec3, front: Vec3, up: Vec3): Mat4 = stackPush {
            val right = cross(front, up).normalize()
            val f = -front
            val c = cross(right, front)
            val m = Mat4(
                right.x, right.y, right.z, 0f,
                c.x, c.y, c.z, 0f,
                f.x, f.y, f.z, 0f,
                position.x, position.y, position.z, 0f
            )
            return m.transpose().inverse()
        }

        fun ortho(left: Float, right: Float, bottom: Float, top: Float, zNear: Float, zFar: Float): Mat4 = stackPush {
            Mat4(
                2.0f / (right - left), 0.0f, 0.0f, 0.0f,
                0.0f, 2.0f / (bottom - top), 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f / (zNear - zFar), 0.0f,
                -(right + left) / (right - left), -(bottom + top) / (bottom - top), zNear / (zNear - zFar), 1.0f
            )
        }

        fun perspective(aspectRatio: Float, fov: Degree, zNear: Float, zFar: Float): Mat4 = stackPush {
            val f = 1.0f / tan((fov * 0.5f).radians.value)
            Mat4(
                f / aspectRatio, 0.0f, 0.0f, 0.0f,
                0.0f, -f, 0.0f, 0.0f,
                0.0f, 0.0f, zFar / (zNear - zFar), -1.0f,
                0.0f, 0.0f, zNear * zFar / (zNear - zFar), 0.0f
            )
        }

        fun normal(): Mat4 = stackPush {
            return Mat4()
                .identity()
                .inverse()
                .transpose()
        }

    }

    operator fun get(i: Int): Vec4 = Vec4(handle + i * Vec4.SIZE_BYTES)

    fun identity(): Mat4 {
        HeapMemory.reset(handle, SIZE_BYTES)
        get(0)[0] = 1f
        get(1)[1] = 1f
        get(2)[2] = 1f
        get(3)[3] = 1f
        return this
    }

    fun transpose(): Mat4 = transpose(this, this)

    fun inverse(): Mat4 = inverse(this, this)

    fun translate(translation: Vec3): Mat4 {
        get(0)[3] = translation.x
        get(1)[3] = translation.y
        get(2)[3] = translation.z
        return this
    }

    fun scale(scalar: Vec3): Mat4 {
        get(0)[0] = scalar.x
        get(1)[1] = scalar.y
        get(2)[2] = scalar.z
        return this
    }

    fun rotate(rx: Float, ry: Float, rz: Float, axis: Vec3): Mat4 {
        var m = this
        val mx = stackPush { Mat4() }
        val my = stackPush { Mat4() }
        val mz = stackPush { Mat4() }

        val sinx = sin(rx)
        val cosx = cos(rx)
        mx[1][1] = cosx
        mx[1][2] = -sinx
        mx[2][1] = sinx
        mx[2][2] = cosx
        mx[0][0] = axis.x

        val siny = sin(ry)
        val cosy = cos(ry)
        my[0][0] = cosy
        my[0][2] = siny
        my[2][0] = -siny
        my[2][2] = cosy
        my[1][1] = axis.y

        val sinz = sin(rz)
        val cosz = cos(rz)
        mz[0][0] = cosz
        mz[0][1] = -sinz
        mz[1][0] = sinz
        mz[1][1] = cosz
        mz[2][2] = axis.z

        m *= mz
        m *= my
        m *= mx

        return m
    }

    fun rotate(quaternion: Quaternion): Mat4 {
        var m = this
        m *= stackPush { Mat4(quaternion) }
        return m
    }

    operator fun plus(v: Float): Mat4 = stackPush {
        Mat4(v1 + v, v2 + v, v3 + v, v4 + v)
    }

    operator fun minus(v: Float): Mat4 = stackPush {
        Mat4(v1 - v, v2 - v, v3 - v, v4 - v)
    }

    operator fun times(v: Float): Mat4 = stackPush {
        Mat4(v1 * v, v2 * v, v3 * v, v4 * v)
    }

    operator fun div(v: Float): Mat4 = stackPush {
        Mat4(v1 / v, v2 / v, v3 / v, v4 / v)
    }

    operator fun plus(m: Mat4): Mat4 = stackPush {
        Mat4(v1 + m.v1, v2 + m.v2, v3 + m.v3, v4 + m.v4)
    }

    operator fun minus(m: Mat4): Mat4 = stackPush {
        Mat4(v1 - m.v1, v2 - m.v2, v3 - m.v3, v4 - m.v4)
    }

    operator fun div(m: Mat4): Mat4 = stackPush {
        Mat4(v1 / m.v1, v2 / m.v2, v3 / m.v3, v4 / m.v4)
    }

    operator fun times(m: Mat4): Mat4 = stackPush {
        val m1 = this@Mat4
        val m2 = m
        val m3 = Mat4()
        for (r in 0..3) {
            for (c in 0..3) {
                for (i in 0..3) {
                    m3[r][c] += m1[r][i] * m2[i][c]
                }
            }
        }
        return m3
    }

    operator fun unaryMinus(): Mat4 = stackPush {
        val m1 = this@Mat4
        val m2 = Mat4()
        for (r in 0..3) {
            for (c in 0..3) {
                m2[r][c] = -m1[r][c]
            }
        }
        return m2
    }

}

fun StackMemory.Mat4(): Mat4 = Mat4(push(Mat4.SIZE_BYTES))

fun StackMemory.Mat4(
    q: Quaternion
): Mat4 = Mat4(q, push(Mat4.SIZE_BYTES))

fun StackMemory.Mat4(
    m00: Float,
    m01: Float,
    m02: Float,
    m03: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m13: Float,
    m20: Float,
    m21: Float,
    m22: Float,
    m23: Float,
    m30: Float,
    m31: Float,
    m32: Float,
    m33: Float
): Mat4 = Mat4(
    m00, m01, m02, m03,
    m10, m11, m12, m13,
    m20, m21, m22, m23,
    m30, m31, m32, m33,
    push(Mat4.SIZE_BYTES)
)

fun StackMemory.Mat4(
    v1: Vec4,
    v2: Vec4,
    v3: Vec4,
    v4: Vec4,
): Mat4 = Mat4(v1,v2,v3,v4, push(Mat4.SIZE_BYTES))

inline fun <T> Mat4.use(block: (`value`: Mat4) -> T): T {
    try {
        return block(this)
    } finally {
        free()
    }
}
