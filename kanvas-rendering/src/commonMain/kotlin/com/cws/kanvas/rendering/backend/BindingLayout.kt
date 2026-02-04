package com.cws.kanvas.rendering.backend

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer

expect class BindingLayout(context: RenderContext, info: BindingInfo)
    : Resource<BindingLayoutHandle, BindingInfo>, INativeData {
    override fun onCreate()
    override fun onDestroy()
    override fun setInfo()
    override val buffer: NativeBuffer?
    override fun sizeBytes(layout: MemoryLayout): Int
    override fun pack(buffer: NativeBuffer)
    override fun unpack(buffer: NativeBuffer): INativeData
}
