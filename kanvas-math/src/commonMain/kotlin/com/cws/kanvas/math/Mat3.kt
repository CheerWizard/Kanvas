package com.cws.kanvas.math

import com.cws.fmm.HeapMemory
import com.cws.fmm.MemoryHandle
import com.cws.fmm.NULL
import com.cws.fmm.StackMemory
import com.cws.fmm.checkNotNull
import com.cws.fmm.stackPush
import com.cws.kanvas.math.Mat2

// Used to generate fresh version
//@FastObject
//class _Mat3(
//    var v1: Vec3,
//    var v2: Vec3,
//    var v3: Vec3
//)

@JvmInline
value class Mat3(
    val handle: MemoryHandle = create().handle,
) {
    var v1: Vec3
        get() {
            handle.checkNotNull()
            return Vec3(handle)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.copy(value.handle, handle, Vec3.SIZE_BYTES)
        }

    var v2: Vec3
        get() {
            handle.checkNotNull()
            return Vec3(handle + Vec3.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.copy(value.handle, handle + Vec3.SIZE_BYTES, Vec3.SIZE_BYTES)
        }

    var v3: Vec3
        get() {
            handle.checkNotNull()
            return Vec3(handle + Vec3.SIZE_BYTES + Vec3.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.copy(value.handle, handle + Vec3.SIZE_BYTES + Vec3.SIZE_BYTES, Vec3.SIZE_BYTES)
        }

    constructor(
        m00: Float,
        m01: Float,
        m02: Float,
        m10: Float,
        m11: Float,
        m12: Float,
        m20: Float,
        m21: Float,
        m22: Float,
        handle: Int = Mat2.Companion.create().handle,
    ) : this(handle) {
        get(0)[0] = m00
        get(0)[1] = m01
        get(0)[2] = m02
        get(1)[0] = m10
        get(1)[1] = m11
        get(1)[2] = m12
        get(2)[0] = m20
        get(2)[1] = m21
        get(2)[2] = m22
    }

    constructor(
        v1: Vec3 = Vec3(1f, 0f, 0f),
        v2: Vec3 = Vec3(0f, 1f, 0f),
        v3: Vec3 = Vec3(0f, 0f, 1f),
        handle: Int = create().handle,
    ) : this(handle) {
        this.v1 `=` v1
        this.v2 `=` v2
        this.v3 `=` v3
    }

    fun free(): Mat3 {
        HeapMemory.free(handle, SIZE_BYTES)
        return Mat3(NULL)
    }

    fun clone(stackScope: Boolean): Mat3 {
        val clone = if (stackScope) stackPush { Mat3() } else Mat3()
        HeapMemory.copy(handle, clone.handle, SIZE_BYTES)
        return clone
    }

    infix fun `=`(other: Mat3) {
        HeapMemory.copy(other.handle, handle, SIZE_BYTES)
    }

    companion object {
        const val SIZE_BYTES: Int = Vec3.SIZE_BYTES + Vec3.SIZE_BYTES + Vec3.SIZE_BYTES

        fun create(): Mat3 = Mat3(HeapMemory.allocate(SIZE_BYTES))
    }

    operator fun get(i: Int): Vec3 = Vec3(handle + i * Vec3.SIZE_BYTES)

    operator fun plus(v: Float): Mat3 = stackPush {
        Mat3(v1 + v, v2 + v, v3 + v)
    }

    operator fun minus(v: Float): Mat3 = stackPush {
        Mat3(v1 - v, v2 - v, v3 - v)
    }

    operator fun times(v: Float): Mat3 = stackPush {
        Mat3(v1 * v, v2 * v, v3 * v)
    }

    operator fun div(v: Float): Mat3 = stackPush {
        Mat3(v1 / v, v2 / v, v3 / v)
    }

    operator fun plus(m: Mat3): Mat3 = stackPush {
        Mat3(v1 + m.v1, v2 + m.v2, v3 + m.v3)
    }

    operator fun minus(m: Mat3): Mat3 = stackPush {
        Mat3(v1 - m.v1, v2 - m.v2, v3 - m.v3)
    }

    operator fun div(m: Mat3): Mat3 = stackPush {
        Mat3(v1 / m.v1, v2 / m.v2, v3 / m.v3)
    }

    operator fun times(m: Mat3): Mat3 = stackPush {
        val m1 = this@Mat3
        val m2 = m
        val m3 = Mat3()
        for (r in 0..2) {
            for (c in 0..2) {
                for (i in 0..2) {
                    m3[r][c] += m1[r][i] * m2[i][c]
                }
            }
        }
        return m3
    }

    operator fun unaryMinus(): Mat3 = stackPush {
        val m1 = this@Mat3
        val m2 = Mat3()
        for (r in 0..2) {
            for (c in 0..2) {
                m2[r][c] = -m1[r][c]
            }
        }
        return m2
    }

    fun identity(): Mat3 {
        HeapMemory.reset(handle, SIZE_BYTES)
        get(0)[0] = 1f
        get(1)[1] = 1f
        get(2)[2] = 1f
        return this
    }

    fun transpose(): Mat3 = transpose(this, this)

    fun inverse(): Mat3 = inverse(this, this)

}

fun StackMemory.Mat3(): Mat3 = Mat3(push(Mat3.SIZE_BYTES))

fun StackMemory.Mat3(
    m00: Float,
    m01: Float,
    m02: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m20: Float,
    m21: Float,
    m22: Float,
): Mat3 = Mat3(m00, m01, m02, m10, m11, m12, m20, m21, m22, push(Mat3.SIZE_BYTES))

fun StackMemory.Mat3(
    v1: Vec3,
    v2: Vec3,
    v3: Vec3,
): Mat3 = Mat3(v1,v2,v3, push(Mat3.SIZE_BYTES))

inline fun <T> Mat3.use(block: (`value`: Mat3) -> T): T {
    try {
        return block(this)
    } finally {
        free()
    }
}
