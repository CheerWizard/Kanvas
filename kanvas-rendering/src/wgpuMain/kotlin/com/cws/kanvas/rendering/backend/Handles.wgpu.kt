package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.GPUBindGroup
import com.cws.kanvas.wgpu.gpu.GPUBindGroupLayout
import com.cws.kanvas.wgpu.gpu.GPUBuffer
import com.cws.kanvas.wgpu.gpu.GPUBufferBinding
import com.cws.kanvas.wgpu.gpu.GPUCanvasContext
import com.cws.kanvas.wgpu.gpu.GPUCommandEncoder
import com.cws.kanvas.wgpu.gpu.GPURenderPassDescriptor
import com.cws.kanvas.wgpu.gpu.GPURenderPipeline
import com.cws.kanvas.wgpu.gpu.GPUSampler
import com.cws.kanvas.wgpu.gpu.GPUShaderModule
import com.cws.kanvas.wgpu.gpu.GPUTexture
import com.cws.kanvas.wgpu.gpu.GPUTextureSampleType
import com.cws.kanvas.wgpu.gpu.GPUTextureView
import com.cws.kanvas.wgpu.gpu.GPUTextureViewDimension
import com.cws.std.memory.*

/* ===================== BINDING LAYOUT ===================== */

actual class BindingLayoutHandle(
    val layout: GPUBindGroupLayout?,
    val group: GPUBindGroup?,
    actual override val buffer: NativeBuffer? = null
) : INativeData {

    actual constructor() : this(null, null)

    var id: Int = nextId()

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES

    actual override fun pack(buffer: NativeBuffer) { buffer.pushInt(id) }
    actual override fun unpack(buffer: NativeBuffer): INativeData {
        id = buffer.nextInt()
        return this
    }

    companion object {
        private var counter = 1
        private fun nextId(): Int = counter++
    }
}

/* ===================== BUFFER ===================== */

actual class BufferHandle(
    val value: GPUBuffer?,
    val bindingBuffer: GPUBufferBinding? = null,
    actual override val buffer: NativeBuffer? = null
) : ResourceHandle {

    actual constructor() : this(null)

    var id: Int = nextId()

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES
    actual override fun pack(buffer: NativeBuffer) { buffer.pushInt(id) }
    actual override fun unpack(buffer: NativeBuffer): INativeData {
        id = buffer.nextInt()
        return this
    }

    companion object {
        private var counter = 1
        private fun nextId(): Int = counter++
    }
}

/* ===================== RENDER CONTEXT ===================== */

actual class RenderContextHandle(
    val value: GPUCanvasContext?,
    actual override val buffer: NativeBuffer? = null
) : INativeData {

    actual constructor() : this(null)

    var id: Int = nextId()

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES
    actual override fun pack(buffer: NativeBuffer) { buffer.pushInt(id) }
    actual override fun unpack(buffer: NativeBuffer): INativeData {
        id = buffer.nextInt()
        return this
    }

    companion object {
        private var counter = 1
        private fun nextId(): Int = counter++
    }
}

/* ===================== RENDER PIPELINE ===================== */

actual class RenderPipelineHandle(
    val value: GPURenderPipeline?,
    actual override val buffer: NativeBuffer? = null
) : INativeData {

    actual constructor() : this(null)

    var id: Int = nextId()

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES
    actual override fun pack(buffer: NativeBuffer) { buffer.pushInt(id) }
    actual override fun unpack(buffer: NativeBuffer): INativeData {
        id = buffer.nextInt()
        return this
    }

    companion object {
        private var counter = 1
        private fun nextId(): Int = counter++
    }
}

/* ===================== RENDER TARGET ===================== */

actual class RenderTargetHandle(
    val value: GPURenderPassDescriptor?,
    actual override val buffer: NativeBuffer? = null
) : INativeData {

    actual constructor() : this(null)

    var id: Int = nextId()

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES
    actual override fun pack(buffer: NativeBuffer) { buffer.pushInt(id) }
    actual override fun unpack(buffer: NativeBuffer): INativeData {
        id = buffer.nextInt()
        return this
    }

    companion object {
        private var counter = 1
        private fun nextId(): Int = counter++
    }
}

/* ===================== SAMPLER ===================== */

actual class SamplerHandle(
    val value: GPUSampler?,
    actual override val buffer: NativeBuffer? = null
) : ResourceHandle {

    actual constructor() : this(null)

    var id: Int = nextId()

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES
    actual override fun pack(buffer: NativeBuffer) { buffer.pushInt(id) }
    actual override fun unpack(buffer: NativeBuffer): INativeData {
        id = buffer.nextInt()
        return this
    }

    companion object {
        private var counter = 1
        private fun nextId(): Int = counter++
    }
}

/* ===================== TEXTURE ===================== */

actual class TextureHandle(
    val texture: GPUTexture?,
    val view: GPUTextureView?,
    var sampleType: GPUTextureSampleType? = null,
    var viewDimension: GPUTextureViewDimension? = null,
    actual override val buffer: NativeBuffer? = null
) : ResourceHandle {

    actual constructor() : this(null, null)

    var id: Int = nextId()

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES
    actual override fun pack(buffer: NativeBuffer) { buffer.pushInt(id) }
    actual override fun unpack(buffer: NativeBuffer): INativeData {
        id = buffer.nextInt()
        return this
    }

    companion object {
        private var counter = 1
        private fun nextId(): Int = counter++
    }
}

/* ===================== SHADER ===================== */

actual class ShaderHandle(
    val value: GPUShaderModule?,
    actual override val buffer: NativeBuffer? = null
) : INativeData {

    actual constructor() : this(null)

    var id: Int = nextId()

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES
    actual override fun pack(buffer: NativeBuffer) { buffer.pushInt(id) }
    actual override fun unpack(buffer: NativeBuffer): INativeData {
        id = buffer.nextInt()
        return this
    }

    companion object {
        private var counter = 1
        private fun nextId(): Int = counter++
    }
}

/* ===================== COMMAND BUFFER ===================== */

actual class CommandBufferHandle(
    val value: GPUCommandEncoder?,
    actual override val buffer: NativeBuffer? = null
) : INativeData {

    actual constructor() : this(null)

    var id: Int = nextId()

    actual override fun sizeBytes(layout: MemoryLayout): Int = Int.SIZE_BYTES
    actual override fun pack(buffer: NativeBuffer) { buffer.pushInt(id) }
    actual override fun unpack(buffer: NativeBuffer): INativeData {
        id = buffer.nextInt()
        return this
    }

    companion object {
        private var counter = 1
        private fun nextId(): Int = counter++
    }
}