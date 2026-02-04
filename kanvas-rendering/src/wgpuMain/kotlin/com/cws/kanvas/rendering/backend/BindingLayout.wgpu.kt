@file:OptIn(ExperimentalJsCollectionsApi::class, ExperimentalJsExport::class)

package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.GPUBindGroupDescriptor
import com.cws.kanvas.wgpu.gpu.GPUBindGroupEntry
import com.cws.kanvas.wgpu.gpu.GPUBindGroupLayoutDescriptor
import com.cws.kanvas.wgpu.gpu.GPUBindGroupLayoutEntry
import com.cws.kanvas.wgpu.gpu.GPUBindingResource
import com.cws.kanvas.wgpu.gpu.GPUBufferBindingLayout
import com.cws.kanvas.wgpu.gpu.GPUSamplerBindingLayout
import com.cws.kanvas.wgpu.gpu.GPUTextureBindingLayout
import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer

actual class BindingLayout actual constructor(
    private val context: RenderContext,
    info: BindingInfo,
) : Resource<BindingLayoutHandle, BindingInfo>(info), INativeData {

    var groupIndex = 0

    actual override fun onCreate() {
        val descriptor = GPUBindGroupLayoutDescriptor(
            entries = info.bindings.list.map { binding ->
                val entry = GPUBindGroupLayoutEntry(
                    binding = binding.binding,
                    visibility = binding.shaderStages,
                )

                when (val resource = binding.resource) {
                    is BufferHandle -> {
                        entry.buffer = GPUBufferBindingLayout(binding.type.bufferType)
                    }
                    is SamplerHandle -> {
                        entry.sampler = GPUSamplerBindingLayout(binding.type.samplerType)
                    }
                    is TextureHandle -> {
                        val texture = resource.texture ?: return
                        entry.texture = GPUTextureBindingLayout(
                            multisampled = texture.sampleCount > 1,
                            sampleType = resource.sampleType,
                            viewDimension = resource.viewDimension,
                        )
                    }
                }

                entry
            }.asJsReadonlyArrayView()
        )

        context.call { device ->
            val layout = device.createBindGroupLayout(descriptor)
            val entries = mutableListOf<GPUBindGroupEntry>()

            for (binding in info.bindings.list) {
                val resource = binding.resource

                val bindingResource: GPUBindingResource? = when (resource) {
                    is BufferHandle -> resource.bindingBuffer
                    is SamplerHandle -> resource.value
                    is TextureHandle -> resource.view
                    else -> null
                }

                if (bindingResource != null) {
                    entries.add(GPUBindGroupEntry(
                        binding = binding.binding,
                        resource = bindingResource,
                    ))
                }
            }

            val group = device.createBindGroup(GPUBindGroupDescriptor(
                layout = layout,
                entries = entries.asJsReadonlyArrayView(),
            ))

            groupIndex = info.bindings.list.firstOrNull()?.set ?: 0
            handle = BindingLayoutHandle(layout, group)
        }
    }

    actual override fun onDestroy() {
        // no-op
    }

    actual override fun setInfo() {
        onCreate()
    }

    actual override val buffer: NativeBuffer? = null

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) {
        handle?.pack(buffer)
    }

    actual override fun unpack(buffer: NativeBuffer): INativeData {
        handle?.unpack(buffer)
        return this
    }

}
