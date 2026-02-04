package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.GPUBufferDescriptor
import com.cws.kanvas.wgpu.gpu.GPUMapMode
import com.cws.kanvas.wgpu.gpu.toGPUSize64
import com.cws.std.memory.NativeBuffer

actual open class Buffer actual constructor(
    private val context: RenderContext,
    info: BufferInfo,
) : Resource<BufferHandle, BufferInfo>(info) {

    actual override fun onCreate() {
        context.call { device ->
            handle = BufferHandle(device.createBuffer(GPUBufferDescriptor(
                label = info.name.value,
                size = info.size.toGPUSize64(),
                usage = info.usages,
            )))
        }
    }

    actual override fun onDestroy() {
        handle?.value?.destroy()
    }

    actual override fun setInfo() {
        onCreate()
    }

    actual fun write(
        frame: Int,
        data: NativeBuffer,
        srcOffset: Int,
        destOffset: Int,
        size: Int
    ) {
        handle?.value?.let { buffer ->
            buffer.mapAsync(GPUMapMode.WRITE.value, destOffset.toGPUSize64(), size.toGPUSize64()).then {
                val mapped = buffer.getMappedRange()
                data.copyTo(NativeBuffer(mapped), srcOffset, destOffset, size)
                buffer.unmap()
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
        handle?.value?.let { buffer ->
            buffer.mapAsync(GPUMapMode.READ.value, srcOffset.toGPUSize64(), size.toGPUSize64()).then {
                val mapped = buffer.getMappedRange()
                NativeBuffer(mapped).copyTo(data, srcOffset, destOffset, size)
                buffer.unmap()
            }
        }
    }

}