package com.cws.kanvas.math

import com.cws.std.memory.Heap
import com.cws.std.memory.MemoryHandle
import com.cws.std.memory.NULL
import com.cws.std.memory.Stack
import com.cws.std.memory.checkNotNull
import com.cws.kanvas.math.JsVec3.Companion.SIZE_BYTES

value class JsQuaternion(
    val handle: MemoryHandle = create().handle,
) : Quaternion {
    var _x: Float
        get() {
            handle.checkNotNull()
            return Heap.getFloat(handle)
        }
        set(`value`) {
            handle.checkNotNull()
            Heap.setFloat(handle, value)
        }

    var _y: Float
        get() {
            handle.checkNotNull()
            return Heap.getFloat(handle + Float.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            Heap.setFloat(handle + Float.SIZE_BYTES, value)
        }

    var _z: Float
        get() {
            handle.checkNotNull()
            return Heap.getFloat(handle + Float.SIZE_BYTES + Float.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            Heap.setFloat(handle + Float.SIZE_BYTES + Float.SIZE_BYTES, value)
        }

    var _w: Float
        get() {
            handle.checkNotNull()
            return Heap.getFloat(handle + Float.SIZE_BYTES + Float.SIZE_BYTES + Float.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            Heap.setFloat(handle + Float.SIZE_BYTES + Float.SIZE_BYTES + Float.SIZE_BYTES, value)
        }

    constructor(
        x: Float,
        y: Float,
        z: Float,
        w: Float,
        handle: Int = create().handle,
    ) : this(handle) {
        this._x = x
        this._y = y
        this._z = z
        this._w = w
    }

    fun free(): Quaternion {
        Heap.free(handle, SIZE_BYTES)
        return JsQuaternion(NULL)
    }

    infix fun `=`(other: Vec3) {
        other as JsVec3
        Heap.copy(other.handle, handle, SIZE_BYTES)
    }

    override fun toString(): String {
        return "Vec3(x=$x,y=$y,z=$z)"
    }

    companion object {
        const val SIZE_BYTES: Int = Float.SIZE_BYTES + Float.SIZE_BYTES + Float.SIZE_BYTES

        fun create(): JsVec3 = JsVec3(Heap.allocate(SIZE_BYTES))
    }

}

actual fun Quaternion(): Quaternion = JsQuaternion()

actual fun Quaternion(x: Float, y: Float, z: Float, w: Float): Quaternion = JsQuaternion(x,y,z,w)

actual fun Stack.Quaternion(): Quaternion = JsQuaternion(push(SIZE_BYTES))

actual fun Stack.Quaternion(x: Float, y: Float, z: Float, w: Float): Quaternion = JsQuaternion(x,y,z,w,push(SIZE_BYTES))

actual fun Quaternion.clone(): Quaternion {
    this as JsQuaternion
    val clone = JsQuaternion()
    Heap.copy(handle, clone.handle, SIZE_BYTES)
    return clone
}

actual var Quaternion.x: Float
    get() = (this as JsQuaternion)._x
    set(value) { (this as JsQuaternion)._x = value }

actual var Quaternion.y: Float
    get() = (this as JsQuaternion)._y
    set(value) { (this as JsQuaternion)._y = value }

actual var Quaternion.z: Float
    get() = (this as JsQuaternion)._z
    set(value) { (this as JsQuaternion)._z = value }

actual var Quaternion.w: Float
    get() = (this as JsQuaternion)._w
    set(value) { (this as JsQuaternion)._w = value }

actual operator fun Quaternion.component1(): Float {
    this as JsQuaternion
    return _x
}

actual operator fun Quaternion.component2(): Float {
    this as JsQuaternion
    return _y
}

actual operator fun Quaternion.component3(): Float {
    this as JsQuaternion
    return _z
}

actual operator fun Quaternion.component4(): Float {
    this as JsQuaternion
    return _w
}

actual operator fun Quaternion.get(i: Int): Float {
    this as JsQuaternion
    return Heap.getFloat(handle + i * Float.SIZE_BYTES)
}

actual operator fun Quaternion.set(i: Int, v: Float) {
    this as JsQuaternion
    Heap.setFloat(handle + i * Float.SIZE_BYTES, v)
}