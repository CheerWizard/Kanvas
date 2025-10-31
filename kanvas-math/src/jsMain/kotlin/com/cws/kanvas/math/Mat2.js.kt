package com.cws.kanvas.math

import com.cws.fmm.HeapMemory
import com.cws.fmm.MemoryHandle
import com.cws.fmm.NULL
import com.cws.fmm.StackMemory
import com.cws.fmm.checkNotNull
import com.cws.fmm.stackPush
import com.cws.kanvas.math.JsMat2.Companion.SIZE_BYTES

value class JsMat2(
    val handle: MemoryHandle = create().handle,
) : Mat2 {
    var _v1: Vec2
        get() {
            handle.checkNotNull()
            return JsVec2(handle)
        }
        set(`value`) {
            handle.checkNotNull()
            value as JsVec2
            HeapMemory.copy(value.handle, handle, JsVec2.SIZE_BYTES)
        }

    var _v2: Vec2
        get() {
            handle.checkNotNull()
            return JsVec2(handle + JsVec2.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            value as JsVec2
            HeapMemory.copy(value.handle, handle + JsVec2.SIZE_BYTES, JsVec2.SIZE_BYTES)
        }

    constructor(
        m00: Float,
        m01: Float,
        m10: Float,
        m11: Float,
        handle: Int = create().handle,
    ) : this(handle) {
        this._v1.x = m00
        this._v1.y = m01
        this._v2.x = m10
        this._v2.y = m11
    }

    constructor(
        v1: Vec2,
        v2: Vec2,
        handle: Int = create().handle,
    ) : this(handle) {
        this._v1 = v1
        this._v2 = v2
    }

    fun free(): Mat2 {
        HeapMemory.free(handle, SIZE_BYTES)
        return JsMat2(NULL)
    }

    infix fun `=`(other: Mat2) {
        other as JsMat2
        HeapMemory.copy(other.handle, handle, SIZE_BYTES)
    }

    companion object {
        const val SIZE_BYTES: Int = JsVec2.SIZE_BYTES + JsVec2.SIZE_BYTES

        fun create(): JsMat2 = JsMat2(HeapMemory.allocate(SIZE_BYTES))
    }

}

actual fun Mat2(): Mat2 = JsMat2()

actual fun Mat2(
    m00: Float,
    m01: Float,
    m10: Float,
    m11: Float
): Mat2 = JsMat2(m00, m01, m10, m11)

actual fun Mat2(v1: Vec2, v2: Vec2): Mat2 = JsMat2(v1, v2)

actual fun StackMemory.Mat2(): Mat2 = JsMat2(push(SIZE_BYTES))

actual fun StackMemory.Mat2(
    m00: Float,
    m01: Float,
    m10: Float,
    m11: Float
): Mat2 = JsMat2(m00, m01, m10, m11, push(SIZE_BYTES))

actual fun StackMemory.Mat2(v1: Vec2, v2: Vec2): Mat2 = JsMat2(v1, v2, push(SIZE_BYTES))

actual fun Mat2.reset() {
    this as JsMat2
    HeapMemory.reset(handle, SIZE_BYTES)
}

actual fun Mat2.clone(stackScope: Boolean): Mat2 {
    val clone = if (stackScope) stackPush { Mat2() } else Mat2()
    this as JsMat2
    clone as JsMat2
    HeapMemory.copy(handle, clone.handle, SIZE_BYTES)
    return clone
}

actual var Mat2.v1: Vec2
    get() = (this as JsMat2)._v1
    set(value) { (this as JsMat2)._v1 = value }

actual var Mat2.v2: Vec2
    get() = (this as JsMat2)._v2
    set(value) { (this as JsMat2)._v2 = value }

actual operator fun Mat2.component1(): Vec2 {
    this as JsMat2
    return _v1
}

actual operator fun Mat2.component2(): Vec2 {
    this as JsMat2
    return _v2
}

actual operator fun Mat2.get(i: Int): Vec2 {
    this as JsMat2
    return JsVec2(handle + i * JsVec2.SIZE_BYTES)
}