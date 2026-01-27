package com.cws.kanvas.math

import com.cws.std.memory.Heap
import com.cws.std.memory.MemoryHandle
import com.cws.std.memory.NULL
import com.cws.std.memory.Stack
import com.cws.std.memory.checkNotNull
import com.cws.kanvas.math.JsVec3.Companion.SIZE_BYTES

value class JsVec3(
    val handle: MemoryHandle = create().handle,
) : Vec3 {
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

    constructor(
        x: Float,
        y: Float,
        z: Float,
        handle: Int = create().handle,
    ) : this(handle) {
        this._x = x
        this._y = y
        this._z = z
    }

    fun free(): Vec3 {
        Heap.free(handle, SIZE_BYTES)
        return JsVec3(NULL)
    }

    infix fun `=`(other: Vec3) {
        other as JsVec3
        Heap.copyTo(Heap, other.handle, handle, SIZE_BYTES)
    }

    override fun toString(): String {
        return "Vec3(x=$_x,y=$_y,z=$_z)"
    }

    companion object {
        const val SIZE_BYTES: Int = Float.SIZE_BYTES + Float.SIZE_BYTES + Float.SIZE_BYTES

        fun create(): JsVec3 = JsVec3(Heap.allocate(SIZE_BYTES))
    }

}

actual fun Vec3(): Vec3 = JsVec3()

actual fun Vec3(x: Float, y: Float, z: Float): Vec3 = JsVec3(x,y, z)

actual fun Stack.Vec3(): Vec3 = JsVec3(push(SIZE_BYTES))

actual fun Stack.Vec3(x: Float, y: Float, z: Float): Vec3 = JsVec3(x,y, z,push(SIZE_BYTES))

actual fun Vec3.clone(): Vec3 {
    this as JsVec3
    val clone = JsVec3()
    Heap.copyTo(Heap, handle, clone.handle, SIZE_BYTES)
    return clone
}

actual var Vec3.x: Float
    get() = (this as JsVec3)._x
    set(value) { (this as JsVec3)._x = value }

actual var Vec3.y: Float
    get() = (this as JsVec3)._y
    set(value) { (this as JsVec3)._y = value }

actual var Vec3.z: Float
    get() = (this as JsVec3)._z
    set(value) { (this as JsVec3)._z = value }

actual operator fun Vec3.component1(): Float {
    this as JsVec3
    return _x
}

actual operator fun Vec3.component2(): Float {
    this as JsVec3
    return _y
}

actual operator fun Vec3.component3(): Float {
    this as JsVec3
    return _z
}

actual operator fun Vec3.get(i: Int): Float {
    this as JsVec3
    return Heap.getFloat(handle + i * Float.SIZE_BYTES)
}

actual operator fun Vec3.set(i: Int, v: Float) {
    this as JsVec3
    Heap.setFloat(handle + i * Float.SIZE_BYTES, v)
}