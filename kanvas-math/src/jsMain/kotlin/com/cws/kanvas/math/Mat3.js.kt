package com.cws.kanvas.math

import com.cws.std.memory.Heap
import com.cws.std.memory.MemoryHandle
import com.cws.std.memory.NULL
import com.cws.std.memory.Stack
import com.cws.std.memory.checkNotNull
import com.cws.std.memory.stackPush
import com.cws.kanvas.math.JsMat2.Companion.SIZE_BYTES

value class JsMat3(
    val handle: MemoryHandle = create().handle,
) : Mat3 {
    var _v1: Vec3
        get() {
            handle.checkNotNull()
            return JsVec3(handle)
        }
        set(`value`) {
            handle.checkNotNull()
            value as JsVec3
            Heap.copy(value.handle, handle, JsVec3.SIZE_BYTES)
        }

    var _v2: Vec3
        get() {
            handle.checkNotNull()
            return JsVec3(handle + JsVec3.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            value as JsVec3
            Heap.copy(value.handle, handle + JsVec3.SIZE_BYTES, JsVec3.SIZE_BYTES)
        }

    var _v3: Vec3
        get() {
            handle.checkNotNull()
            return JsVec3(handle + JsVec3.SIZE_BYTES + JsVec3.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            value as JsVec3
            Heap.copy(value.handle, handle + JsVec3.SIZE_BYTES + JsVec3.SIZE_BYTES, JsVec3.SIZE_BYTES)
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
        handle: Int = create().handle,
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
        _v1 = v1
        _v2 = v2
        _v3 = v3
    }

    fun free(): Mat3 {
        Heap.free(handle, SIZE_BYTES)
        return JsMat3(NULL)
    }

    infix fun `=`(other: Mat3) {
        other as JsMat3
        Heap.copy(other.handle, handle, SIZE_BYTES)
    }

    companion object {
        const val SIZE_BYTES: Int = JsVec3.SIZE_BYTES + JsVec3.SIZE_BYTES + JsVec3.SIZE_BYTES

        fun create(): JsMat3 = JsMat3(Heap.allocate(SIZE_BYTES))
    }

}

actual fun Mat3(): Mat3 = JsMat3()

actual fun Mat3(
    m00: Float,
    m01: Float,
    m02: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m20: Float,
    m21: Float,
    m22: Float,
): Mat3 = JsMat3(m00, m01, m02, m10, m11, m12, m20, m21, m22)

actual fun Mat3(v1: Vec3, v2: Vec3, v3: Vec3): Mat3 = JsMat3(v1, v2, v3)

actual fun Stack.Mat3(): Mat3 = JsMat3(push(SIZE_BYTES))

actual fun Stack.Mat3(
    m00: Float,
    m01: Float,
    m02: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m20: Float,
    m21: Float,
    m22: Float,
): Mat3 = JsMat3(m00, m01, m02, m10, m11, m12, m20, m21, m22, push(SIZE_BYTES))

actual fun Stack.Mat3(v1: Vec3, v2: Vec3, v3: Vec3): Mat3 = JsMat3(v1, v2, v3, push(SIZE_BYTES))

actual fun Mat3.reset() {
    this as JsMat3
    Heap.reset(handle, SIZE_BYTES)
}

actual fun Mat3.clone(stackScope: Boolean): Mat3 {
    val clone = if (stackScope) stackPush { Mat3() } else Mat3()
    this as JsMat3
    clone as JsMat3
    Heap.copy(handle, clone.handle, SIZE_BYTES)
    return clone
}

actual var Mat3.v1: Vec3
    get() = (this as JsMat3)._v1
    set(value) { (this as JsMat3)._v1 = value }

actual var Mat3.v2: Vec3
    get() = (this as JsMat3)._v2
    set(value) { (this as JsMat3)._v2 = value }

actual var Mat3.v3: Vec3
    get() = (this as JsMat3)._v3
    set(value) { (this as JsMat3)._v3 = value }

actual operator fun Mat3.component1(): Vec3 {
    this as JsMat3
    return _v1
}

actual operator fun Mat3.component2(): Vec3 {
    this as JsMat3
    return _v2
}

actual operator fun Mat3.component3(): Vec3 {
    this as JsMat3
    return _v3
}

actual operator fun Mat3.get(i: Int): Vec3 {
    this as JsMat3
    return JsVec3(handle + i * JsVec3.SIZE_BYTES)
}