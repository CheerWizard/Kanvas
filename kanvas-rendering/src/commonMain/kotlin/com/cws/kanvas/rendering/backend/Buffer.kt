package com.cws.kanvas.rendering.backend

import com.cws.std.memory.NativeBuffer

expect open class Buffer(context: RenderContext, info: BufferInfo) : Resource<BufferHandle, BufferInfo> {
    override fun onCreate()
    override fun onDestroy()
    override fun setInfo()
    fun write(frame: Int, data: NativeBuffer, srcOffset: Int, destOffset: Int, size: Int)
    fun read(frame: Int, data: NativeBuffer, srcOffset: Int, destOffset: Int, size: Int)
}