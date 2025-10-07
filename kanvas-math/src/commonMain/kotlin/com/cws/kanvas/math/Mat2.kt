package com.cws.kanvas.math

import com.cws.fmm.FastObject
import com.cws.fmm.HeapMemory
import com.cws.fmm.MemoryHandle
import com.cws.fmm.NULL
import com.cws.fmm.StackMemory
import com.cws.fmm.checkNotNull
import com.cws.fmm.stackPush

// Used to generate fresh version
//@FastObject
//class _Mat2(
//    var v1: Vec2,
//    var v2: Vec2
//)

@JvmInline
value class Mat2(
    val handle: MemoryHandle = create().handle,
) {
    var v1: Vec2
        get() {
            handle.checkNotNull()
            return Vec2(handle)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.copy(value.handle, handle, Vec2.SIZE_BYTES)
        }

    var v2: Vec2
        get() {
            handle.checkNotNull()
            return Vec2(handle + Vec2.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.copy(value.handle, handle + Vec2.SIZE_BYTES, Vec2.SIZE_BYTES)
        }

    constructor(
        m00: Float,
        m01: Float,
        m10: Float,
        m11: Float,
        handle: Int = create().handle,
    ) : this(handle) {
        this.v1.x = m00
        this.v1.y = m01
        this.v2.x = m10
        this.v2.y = m11
    }

    constructor(
        v1: Vec2,
        v2: Vec2,
        handle: Int = create().handle,
    ) : this(handle) {
        this.v1 `=` v1
        this.v2 `=` v2
    }

    fun free(): Mat2 {
        HeapMemory.free(handle, SIZE_BYTES)
        return Mat2(NULL)
    }

    fun clone(stackScope: Boolean): Mat2 {
        val clone = if (stackScope) stackPush { Mat2() } else Mat2()
        HeapMemory.copy(handle, clone.handle, SIZE_BYTES)
        return clone
    }

    infix fun `=`(other: Mat2) {
        HeapMemory.copy(other.handle, handle, SIZE_BYTES)
    }

    companion object {
        const val SIZE_BYTES: Int = Vec2.SIZE_BYTES + Vec2.SIZE_BYTES

        fun create(): Mat2 = Mat2(HeapMemory.allocate(SIZE_BYTES))
    }

    operator fun get(i: Int): Vec2 = Vec2(handle + i * Vec2.SIZE_BYTES)

    fun identity(): Mat2 {
        HeapMemory.reset(handle, SIZE_BYTES)
        get(0)[0] = 1f
        get(1)[1] = 1f
        return this
    }

    fun transpose(): Mat2 = transpose(this, this)

    fun inverse(): Mat2 = inverse(this, this)

    operator fun minus(v: Float): Mat2 = stackPush {
        Mat2(v1 - v, v2 - v)
    }

    operator fun times(v: Float): Mat2 = stackPush {
        Mat2(v1 * v, v2 * v)
    }

    operator fun div(v: Float): Mat2 = stackPush {
        Mat2(v1 / v, v2 / v)
    }

    operator fun plus(m: Mat2): Mat2 = stackPush {
        Mat2(v1 + m.v1, v2 + m.v2)
    }

    operator fun minus(m: Mat2): Mat2 = stackPush {
        Mat2(v1 - m.v1, v2 - m.v2)
    }

    operator fun div(m: Mat2): Mat2 = stackPush {
        Mat2(v1 / m.v1, v2 / m.v2)
    }

    operator fun times(m: Mat2): Mat2 = stackPush {
        val m1 = this@Mat2
        val m2 = m
        val m3 = Mat2()
        for (r in 0..1) {
            for (c in 0..1) {
                for (i in 0..1) {
                    m3[r][c] += m1[r][i] * m2[i][c]
                }
            }
        }
        return m3
    }

    operator fun unaryMinus(): Mat2 = stackPush {
        val m1 = this@Mat2
        val m2 = Mat2()
        for (r in 0..1) {
            for (c in 0..1) {
                m2[r][c] = -m1[r][c]
            }
        }
        return m2
    }

}

fun StackMemory.Mat2(): Mat2 = Mat2(push(Mat2.SIZE_BYTES))

fun StackMemory.Mat2(
    m00: Float,
    m01: Float,
    m10: Float,
    m11: Float
): Mat2 = Mat2(m00, m01, m10, m11, push(Mat2.SIZE_BYTES))

fun StackMemory.Mat2(v1: Vec2, v2: Vec2): Mat2 = Mat2(v1, v2, push(Mat2.SIZE_BYTES))

inline fun <T> Mat2.use(block: (`value`: Mat2) -> T): T {
    try {
        return block(this)
    } finally {
        free()
    }
}