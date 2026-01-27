package com.cws.kanvas.math

import com.cws.std.memory.Heap
import com.cws.std.memory.MemoryHandle
import com.cws.std.memory.NULL
import com.cws.std.memory.Stack
import com.cws.std.memory.checkNotNull
import com.cws.std.memory.stackPush

value class JsMat4(
    val handle: MemoryHandle = create().handle,
) : Mat4 {
    var _v1: Vec4
        get() {
            handle.checkNotNull()
            return JsVec4(handle)
        }
        set(`value`) {
            handle.checkNotNull()
            value as JsVec4
            Heap.copyTo(Heap, value.handle, handle, JsVec4.SIZE_BYTES)
        }

    var _v2: Vec4
        get() {
            handle.checkNotNull()
            return JsVec4(handle + JsVec4.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            value as JsVec4
            Heap.copyTo(Heap, value.handle, handle + JsVec4.SIZE_BYTES, JsVec4.SIZE_BYTES)
        }

    var _v3: Vec4
        get() {
            handle.checkNotNull()
            return JsVec4(handle + JsVec4.SIZE_BYTES + JsVec4.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            value as JsVec4
            Heap.copyTo(Heap, value.handle, handle + JsVec4.SIZE_BYTES + JsVec4.SIZE_BYTES, JsVec4.SIZE_BYTES)
        }

    var _v4: Vec4
        get() {
            handle.checkNotNull()
            return JsVec4(handle + JsVec4.SIZE_BYTES + JsVec4.SIZE_BYTES + JsVec4.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            value as JsVec4
            Heap.copyTo(Heap, value.handle, handle + JsVec4.SIZE_BYTES + JsVec4.SIZE_BYTES + JsVec4.SIZE_BYTES, JsVec4.SIZE_BYTES)
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
        handle: Int = create().handle,
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
        this._v1 = v1
        this._v2 = v2
        this._v3 = v3
        this._v4 = v4
    }

    fun free(): JsMat4 {
        Heap.free(handle, SIZE_BYTES)
        return JsMat4(NULL)
    }

    infix fun `=`(other: Mat4) {
        other as JsMat4
        Heap.copyTo(Heap, other.handle, handle, SIZE_BYTES)
    }

    companion object {
        const val SIZE_BYTES: Int = JsVec4.SIZE_BYTES + JsVec4.SIZE_BYTES + JsVec4.SIZE_BYTES +
                JsVec4.SIZE_BYTES
        fun create(): JsMat4 = JsMat4(Heap.allocate(SIZE_BYTES))
    }

}

actual fun Mat4(): Mat4 = JsMat4()

actual fun Mat4(
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
): Mat4 = JsMat4(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33)

actual fun Mat4(v1: Vec4, v2: Vec4, v3: Vec4, v4: Vec4): Mat4 = JsMat4(v1, v2, v3, v4)

actual fun Stack.Mat4(): Mat4 = JsMat4(push(JsMat4.SIZE_BYTES))

actual fun Stack.Mat4(
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
): Mat4 = JsMat4(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33, push(JsMat4.SIZE_BYTES))

actual fun Stack.Mat4(v1: Vec4, v2: Vec4, v3: Vec4, v4: Vec4): Mat4 = JsMat4(v1, v2, v3, v4, push(JsMat4.SIZE_BYTES))

actual fun Mat4.reset() {
    this as JsMat4
    Heap.reset(handle, JsMat4.SIZE_BYTES)
}

actual fun Mat4.clone(stackScope: Boolean): Mat4 {
    val clone = if (stackScope) stackPush { Mat4() } else Mat4()
    this as JsMat4
    clone as JsMat4
    Heap.copyTo(Heap, handle, clone.handle, JsMat4.SIZE_BYTES)
    return clone
}

actual var Mat4.v1: Vec4
    get() = (this as JsMat4)._v1
    set(value) { (this as JsMat4)._v1 = value }

actual var Mat4.v2: Vec4
    get() = (this as JsMat4)._v2
    set(value) { (this as JsMat4)._v2 = value }

actual var Mat4.v3: Vec4
    get() = (this as JsMat4)._v3
    set(value) { (this as JsMat4)._v3 = value }

actual var Mat4.v4: Vec4
    get() = (this as JsMat4)._v4
    set(value) { (this as JsMat4)._v4 = value }

actual operator fun Mat4.component1(): Vec4 {
    this as JsMat4
    return _v1
}

actual operator fun Mat4.component2(): Vec4 {
    this as JsMat4
    return _v2
}

actual operator fun Mat4.component3(): Vec4 {
    this as JsMat4
    return _v3
}

actual operator fun Mat4.component4(): Vec4 {
    this as JsMat4
    return _v4
}

actual operator fun Mat4.get(i: Int): Vec4 {
    this as JsMat4
    return JsVec4(handle + i * JsVec4.SIZE_BYTES)
}
