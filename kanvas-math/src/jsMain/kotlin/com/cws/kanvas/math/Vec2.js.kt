package com.cws.kanvas.math

import com.cws.fmm.HeapMemory
import com.cws.fmm.MemoryHandle
import com.cws.fmm.NULL
import com.cws.fmm.StackMemory
import com.cws.fmm.checkNotNull
import com.cws.kanvas.math.JsVec2.Companion.SIZE_BYTES

value class JsVec2(
    val handle: MemoryHandle = create().handle,
) : Vec2 {
    var _x: Float
        get() {
            handle.checkNotNull()
            return HeapMemory.getFloat(handle)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.setFloat(handle, value)
        }

    var _y: Float
        get() {
            handle.checkNotNull()
            return HeapMemory.getFloat(handle + Float.SIZE_BYTES)
        }
        set(`value`) {
            handle.checkNotNull()
            HeapMemory.setFloat(handle + Float.SIZE_BYTES, value)
        }

    constructor(
        x: Float,
        y: Float,
        handle: MemoryHandle = create().handle
    ) : this(handle) {
        this._x = x
        this._y = y
    }

    fun free(): Vec2 {
        HeapMemory.free(handle, SIZE_BYTES)
        return JsVec2(NULL)
    }

    infix fun `=`(other: Vec2) {
        other as JsVec2
        HeapMemory.copy(other.handle, handle, SIZE_BYTES)
    }

    override fun toString(): String {
        return "Vec2(x=$_x,y=$_y)"
    }

    companion object {
        const val SIZE_BYTES: Int = Float.SIZE_BYTES + Float.SIZE_BYTES

        fun create(): JsVec2 = JsVec2(HeapMemory.allocate(SIZE_BYTES))
    }

}

actual fun Vec2(): Vec2 = JsVec2()

actual fun Vec2(x: Float, y: Float): Vec2 = JsVec2(x,y)

actual fun StackMemory.Vec2(): Vec2 = JsVec2(push(SIZE_BYTES))

actual fun StackMemory.Vec2(x: Float, y: Float): Vec2 = JsVec2(x,y, push(SIZE_BYTES))

actual fun Vec2.clone(): Vec2 {
    this as JsVec2
    val clone = JsVec2()
    HeapMemory.copy(handle, clone.handle, SIZE_BYTES)
    return clone
}

actual var Vec2.x: Float
    get() = (this as JsVec2)._x
    set(value) { (this as JsVec2)._x = value }

actual var Vec2.y: Float
    get() = (this as JsVec2)._y
    set(value) { (this as JsVec2)._y = value }

actual operator fun Vec2.component1(): Float {
    this as JsVec2
    return _x
}

actual operator fun Vec2.component2(): Float {
    this as JsVec2
    return _y
}

actual operator fun Vec2.get(i: Int): Float {
    this as JsVec2
    return HeapMemory.getFloat(handle + i * Float.SIZE_BYTES)
}

actual operator fun Vec2.set(i: Int, v: Float) {
    this as JsVec2
    HeapMemory.setFloat(handle + i * Float.SIZE_BYTES, v)
}