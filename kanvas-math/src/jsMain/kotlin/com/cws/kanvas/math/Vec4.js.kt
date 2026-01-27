package com.cws.kanvas.math

import com.cws.std.memory.Heap
import com.cws.std.memory.MemoryHandle
import com.cws.std.memory.NULL
import com.cws.std.memory.Stack
import com.cws.std.memory.checkNotNull
import com.cws.kanvas.math.JsVec3.Companion.SIZE_BYTES

value class JsVec4(
    val handle: MemoryHandle = create().handle,
) : Vec4 {
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

    fun free(): Vec4 {
        Heap.free(handle, SIZE_BYTES)
        return JsVec4(NULL)
    }

    infix fun `=`(other: Vec3) {
        other as JsVec3
        Heap.copyTo(Heap, other.handle, handle, SIZE_BYTES)
    }

    override fun toString(): String {
        return "Vec3(x=$x,y=$y,z=$z)"
    }

    companion object {
        const val SIZE_BYTES: Int = Float.SIZE_BYTES + Float.SIZE_BYTES + Float.SIZE_BYTES

        fun create(): JsVec3 = JsVec3(Heap.allocate(SIZE_BYTES))
    }

}

actual fun Vec4(): Vec4 = JsVec4()

actual fun Vec4(x: Float, y: Float, z: Float, w: Float): Vec4 = JsVec4(x,y,z,w)

actual fun Stack.Vec4(): Vec4 = JsVec4(push(SIZE_BYTES))

actual fun Stack.Vec4(x: Float, y: Float, z: Float, w: Float): Vec4 = JsVec4(x,y,z,w,push(SIZE_BYTES))

actual fun Vec4.clone(): Vec4 {
    this as JsVec4
    val clone = JsVec4()
    Heap.copyTo(Heap, handle, clone.handle, SIZE_BYTES)
    return clone
}

actual var Vec4.x: Float
    get() = (this as JsVec4)._x
    set(value) { (this as JsVec4)._x = value }

actual var Vec4.y: Float
    get() = (this as JsVec4)._y
    set(value) { (this as JsVec4)._y = value }

actual var Vec4.z: Float
    get() = (this as JsVec4)._z
    set(value) { (this as JsVec4)._z = value }

actual var Vec4.w: Float
    get() = (this as JsVec4)._w
    set(value) { (this as JsVec4)._w = value }

actual operator fun Vec4.component1(): Float {
    this as JsVec4
    return _x
}

actual operator fun Vec4.component2(): Float {
    this as JsVec4
    return _y
}

actual operator fun Vec4.component3(): Float {
    this as JsVec4
    return _z
}

actual operator fun Vec4.component4(): Float {
    this as JsVec4
    return _w
}

actual operator fun Vec4.get(i: Int): Float {
    this as JsVec4
    return Heap.getFloat(handle + i * Float.SIZE_BYTES)
}

actual operator fun Vec4.set(i: Int, v: Float) {
    this as JsVec4
    Heap.setFloat(handle + i * Float.SIZE_BYTES, v)
}