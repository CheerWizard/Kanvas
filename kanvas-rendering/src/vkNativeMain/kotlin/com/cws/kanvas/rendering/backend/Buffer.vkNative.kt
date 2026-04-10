package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK
import com.cws.print.Print
import com.cws.std.memory.NativeBuffer

actual open class Buffer actual constructor(
    private val context: RenderContext,
    info: BufferInfo
) : Resource<BufferHandle, BufferInfo>(info) {

    companion object {
        private const val TAG = "Buffer"
    }

    actual override fun onCreate() {
        context.handle?.let { ctx ->
            info.pack()?.let { info ->
                handle = BufferHandle(VK.VkBufferResource_create(ctx.value, info.buffer))
            }
        }
    }

    actual override fun onDestroy() {
        handle?.let {
            VK.VkBufferResource_destroy(it.value)
        }
    }

    actual override fun setInfo() {
        handle?.let { handle ->
            info.pack()?.buffer?.let { info ->
                VK.VkBufferResource_setInfo(handle.value, info)
            }
        }
    }

    actual fun write(
        frame: Int,
        data: NativeBuffer,
        srcOffset: Int,
        destOffset: Int,
        size: Int
    ) {
        handle?.let {
            val buffer = VK.VkBufferResource_map(it.value, frame)
            if (buffer != null) {
                data.copyTo(NativeBuffer(buffer), srcOffset, destOffset, size)
                VK.VkBufferResource_unmap(it.value)
            } else {
                Print.e(TAG, "Failed to write data to VkBuffer, map returns null")
            }
        }
    }

    actual fun read(
        frame: Int,
        data: NativeBuffer,
        srcOffset: Int,
        destOffset: Int,
        size: Int
    ) {
        handle?.let {
            val buffer = VK.VkBufferResource_map(it.value, frame)
            if (buffer != null) {
                NativeBuffer(buffer).copyTo(data, srcOffset, destOffset, size)
                VK.VkBufferResource_unmap(it.value)
            } else {
                Print.e(TAG, "Failed to read data from VkBuffer, map returns null")
            }
        }
    }

}