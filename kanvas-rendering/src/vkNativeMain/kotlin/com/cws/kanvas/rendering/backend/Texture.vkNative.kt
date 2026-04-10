package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK
import com.cws.print.Print
import com.cws.std.memory.NativeBuffer

actual class Texture actual constructor(
    private val context: RenderContext,
    info: TextureInfo,
) : Resource<TextureHandle, TextureInfo>(info) {

    companion object {
        private const val TAG = "Texture"
    }

    actual override fun onCreate() {
        context.handle?.value?.let { ctx ->
            info.pack()?.buffer?.let { info ->
                handle = TextureHandle(VK.VkTextureResource_create(ctx, info))
            }
        }
    }

    actual override fun onDestroy() {
        handle?.value?.let { handle ->
            VK.VkTextureResource_destroy(handle)
        }
    }

    actual override fun setInfo() {
        handle?.value?.let { handle ->
            info.pack()?.buffer?.let { info ->
                VK.VkTextureResource_setInfo(handle, info)
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
        handle?.value?.let {
            val buffer = VK.VkTextureResource_map(it, frame)
            if (buffer != null) {
                data.copyTo(NativeBuffer(buffer), srcOffset, destOffset, size)
                VK.VkTextureResource_unmap(it)
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
        handle?.value?.let {
            val buffer = VK.VkTextureResource_map(it, frame)
            if (buffer != null) {
                NativeBuffer(buffer).copyTo(data, srcOffset, destOffset, size)
                VK.VkTextureResource_unmap(it)
            } else {
                Print.e(TAG, "Failed to read data from VkBuffer, map returns null")
            }
        }
    }

}