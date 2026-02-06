package com.cws.kanvas.rendering.backend

import com.cws.kanvas.math.Vec4
import com.cws.std.memory.*

/* ============================================================
 * CONTEXT
 * ============================================================ */

data class ContextInfo(
    var applicationName: NativeString = NativeString(),
    var engineName: NativeString = NativeString(),
    var width: Int = 0,
    var height: Int = 0,
    var frameCount: Int = 1,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 32 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(applicationName.address)
        buffer.pushLong(engineName.address)
        buffer.pushInt(width)
        buffer.pushInt(height)
        buffer.pushInt(frameCount)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        buffer.nextLong()
        buffer.nextLong()
        width = buffer.nextInt()
        height = buffer.nextInt()
        frameCount = buffer.nextInt()
        return this
    }
}

/* ============================================================
 * BUFFER INFO
 * ============================================================ */

data class BufferInfo(
    var name: NativeString = NativeString(),
    var bindingLayout: BindingLayoutHandle = BindingLayoutHandle(),
    var binding: Binding = Binding(),
    var memoryType: MemoryType = MemoryType.HOST,
    var usages: Int = 0,
    var size: Long = 0,
    var isStatic: Boolean = false,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 44 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(name.address)
        bindingLayout.pack(buffer)
        binding.pack(buffer)
        buffer.pushInt(memoryType.value)
        buffer.pushInt(usages)
        buffer.pushLong(size)
        buffer.pushBoolean(isStatic)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        buffer.nextLong()
        bindingLayout.unpack(buffer)
        binding.unpack(buffer)
        memoryType = IntEnum.from(buffer.nextInt())
        usages = buffer.nextInt()
        size = buffer.nextLong()
        isStatic = buffer.nextBoolean()
        return this
    }
}

fun BufferInfo.getMemoryLayout(): MemoryLayout {
    return when {
        (usages and BufferUsage.UNIFORM_BUFFER.value) == BufferUsage.UNIFORM_BUFFER.value -> MemoryLayout.STD140
        (usages and BufferUsage.STORAGE_BUFFER.value) == BufferUsage.STORAGE_BUFFER.value -> MemoryLayout.STD430
        else -> MemoryLayout.KOTLIN
    }
}

/* ============================================================
 * SAMPLER INFO
 * ============================================================ */

data class SamplerInfo(
    var name: NativeString = NativeString(),
    var bindingLayout: BindingLayoutHandle = BindingLayoutHandle(),
    var binding: Binding = Binding(),

    var magFilter: SamplerFilter = SamplerFilter.LINEAR,
    var minFilter: SamplerFilter = SamplerFilter.LINEAR,

    var addressModeU: SamplerMode = SamplerMode.REPEAT,
    var addressModeV: SamplerMode = SamplerMode.REPEAT,
    var addressModeW: SamplerMode = SamplerMode.REPEAT,

    var enableAnisotropy: Boolean = true,
    var maxAnisotropy: Float = 1f,

    var unnormalizedCoordinates: Boolean = false,
    var enableCompare: Boolean = false,

    var compareOp: CompareOp = CompareOp.ALWAYS,
    var mipmapMode: SamplerMipMapMode = SamplerMipMapMode.LINEAR,
    var borderColor: BorderColor = BorderColor.FLOAT_OPAQUE_BLACK,

    var mipLodBias: Float = 0f,
    var minLod: Float = 0f,
    var maxLod: Float = 0f,

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 84 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(name.address)
        bindingLayout.pack(buffer)
        binding.pack(buffer)

        buffer.pushInt(magFilter.value)
        buffer.pushInt(minFilter.value)

        buffer.pushInt(addressModeU.value)
        buffer.pushInt(addressModeV.value)
        buffer.pushInt(addressModeW.value)

        buffer.pushBoolean(enableAnisotropy)
        buffer.pushFloat(maxAnisotropy)

        buffer.pushBoolean(unnormalizedCoordinates)
        buffer.pushBoolean(enableCompare)

        buffer.pushInt(compareOp.value)
        buffer.pushInt(mipmapMode.value)
        buffer.pushInt(borderColor.value)

        buffer.pushFloat(mipLodBias)
        buffer.pushFloat(minLod)
        buffer.pushFloat(maxLod)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        buffer.nextLong()
        bindingLayout.unpack(buffer)
        binding.unpack(buffer)

        magFilter = IntEnum.from(buffer.nextInt())
        minFilter = IntEnum.from(buffer.nextInt())

        addressModeU = IntEnum.from(buffer.nextInt())
        addressModeV = IntEnum.from(buffer.nextInt())
        addressModeW = IntEnum.from(buffer.nextInt())

        enableAnisotropy = buffer.nextBoolean()
        maxAnisotropy = buffer.nextFloat()

        unnormalizedCoordinates = buffer.nextBoolean()
        enableCompare = buffer.nextBoolean()

        compareOp = IntEnum.from(buffer.nextInt())
        mipmapMode = IntEnum.from(buffer.nextInt())
        borderColor = IntEnum.from(buffer.nextInt())

        mipLodBias = buffer.nextFloat()
        minLod = buffer.nextFloat()
        maxLod = buffer.nextFloat()

        return this
    }
}

/* ============================================================
 * TEXTURE INFO
 * ============================================================ */

data class TextureInfo(
    var name: NativeString = NativeString(),
    var bindingLayout: BindingLayoutHandle = BindingLayoutHandle(),
    var binding: Binding = Binding(),

    var type: TextureType = TextureType.TEXTURE2D,
    var memoryType: MemoryType = MemoryType.HOST,
    var usages: Int = TextureUsage.TEXTURE_BINDING.value,

    var width: Int = 0,
    var height: Int = 0,
    var depth: Int = 1,

    var format: TextureFormat = TextureFormat.FORMAT_RGBA8,

    var mips: Int = 1,
    var baseMip: Int = 0,
    var samples: Int = 1,

    var isStatic: Boolean = true,

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 52 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(name.address)
        bindingLayout.pack(buffer)
        binding.pack(buffer)

        buffer.pushInt(type.value)
        buffer.pushInt(memoryType.value)
        buffer.pushInt(usages)

        buffer.pushInt(width)
        buffer.pushInt(height)
        buffer.pushInt(depth)

        buffer.pushInt(format.value)
        buffer.pushInt(mips)
        buffer.pushInt(baseMip)
        buffer.pushInt(samples)

        buffer.pushBoolean(isStatic)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        buffer.nextLong()
        bindingLayout.unpack(buffer)
        binding.unpack(buffer)

        type = IntEnum.from(buffer.nextInt())
        memoryType = IntEnum.from(buffer.nextInt())

        width = buffer.nextInt()
        height = buffer.nextInt()
        depth = buffer.nextInt()

        format = IntEnum.from(buffer.nextInt())
        mips = buffer.nextInt()
        baseMip = buffer.nextInt()
        samples = buffer.nextInt()

        isStatic = buffer.nextBoolean()
        return this
    }
}

/* ============================================================
 * BLEND
 * ============================================================ */

data class Blend(
    var enable: Boolean = false,
    var srcFactorColor: BlendFactor = BlendFactor.ONE,
    var dstFactorColor: BlendFactor = BlendFactor.ZERO,
    var blendOpColor: BlendOp = BlendOp.ADD,

    var srcFactorAlpha: BlendFactor = BlendFactor.ONE,
    var dstFactorAlpha: BlendFactor = BlendFactor.ZERO,
    var blendOpAlpha: BlendOp = BlendOp.ADD,

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 28 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushBoolean(enable)

        buffer.pushInt(srcFactorColor.value)
        buffer.pushInt(dstFactorColor.value)
        buffer.pushInt(blendOpColor.value)

        buffer.pushInt(srcFactorAlpha.value)
        buffer.pushInt(dstFactorAlpha.value)
        buffer.pushInt(blendOpAlpha.value)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        enable = buffer.nextBoolean()

        srcFactorColor = IntEnum.from(buffer.nextInt())
        dstFactorColor = IntEnum.from(buffer.nextInt())
        blendOpColor = IntEnum.from(buffer.nextInt())

        srcFactorAlpha = IntEnum.from(buffer.nextInt())
        dstFactorAlpha = IntEnum.from(buffer.nextInt())
        blendOpAlpha = IntEnum.from(buffer.nextInt())

        return this
    }
}

/* ============================================================
 * ATTRIBUTE
 * ============================================================ */

data class Attribute(
    var location: Int = 0,
    var type: AttributeType = AttributeType.ATTRIBUTE_TYPE_VEC3,
    var format: AttributeFormat = AttributeFormat.FLOAT,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 12 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushInt(location)
        buffer.pushInt(type.value)
        buffer.pushInt(format.value)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        location = buffer.nextInt()
        type = IntEnum.from(buffer.nextInt())
        format = IntEnum.from(buffer.nextInt())
        return this
    }
}

/* ============================================================
 * BINDING
 * ============================================================ */

data class Binding(
    var type: BindingType = BindingType.UNIFORM_BUFFER,
    var shaderStages: Int = 0,
    var set: Int = 0,
    var binding: Int = 0,
    var count: Int = 1,
    var resource: ResourceHandle? = null,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 28 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushInt(type.value)
        buffer.pushInt(shaderStages)
        buffer.pushInt(set)
        buffer.pushInt(binding)
        buffer.pushInt(count)
        resource.pack(buffer)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        type = IntEnum.from(buffer.nextInt())
        shaderStages = buffer.nextInt()
        set = buffer.nextInt()
        binding = buffer.nextInt()
        count = buffer.nextInt()
        resource.unpack(buffer)
        return this
    }
}

/* ============================================================
 * BINDING INFO
 * ============================================================ */

data class BindingInfo(
    var name: NativeString = NativeString(),
    val bindings: NativeDataList<Binding> = NativeDataList(),
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 24 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(name.address)
        bindings.pack(buffer)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        buffer.nextLong()
        bindings.unpack(buffer)
        return this
    }
}

/* ============================================================
 * SHADER INFO
 * ============================================================ */

data class ShaderInfo(
    var name: NativeString = NativeString(),
    var entryPoint: NativeString = NativeString(),

    var spirvCode: NativeBuffer? = null,
    var spirvCodeSize: Long = 0,

    var textCode: String = "", // not part of packing

    var bindingLayouts: NativeDataList<BindingLayout> = NativeDataList(),

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object {
        const val SIZEOF = 40
    }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(name.address)
        buffer.pushLong(entryPoint.address)

        buffer.pushLong(spirvCode?.address ?: 0)
        buffer.pushLong(spirvCodeSize)

        buffer.pushLong(bindingLayouts.address)
        buffer.pushLong(bindingLayouts.count)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        buffer.nextLong() // name
        buffer.nextLong() // entryPoint

        spirvCode = NativeBuffer(buffer.nextLong(), buffer.nextLong().toInt())
        spirvCodeSize = spirvCode?.capacity?.toLong() ?: 0L

        bindingLayouts = NativeDataList(buffer = NativeBuffer(buffer.nextLong(), buffer.nextLong().toInt()))

        return this
    }
}

/* ============================================================
 * PIPELINE INFO
 * ============================================================ */

data class PipelineInfo(
    var name: NativeString = NativeString(),

    val vertexAttributes: NativeDataList<Attribute> = NativeDataList(),

    var instanced: Boolean = false,

    var primitiveTopology: PrimitiveTopology = PrimitiveTopology.TRIANGLE_LIST,

    var vertexShader: Shader? = null,
    var fragmentShader: Shader? = null,
    var geometryShader: Shader? = null,

    var vertexBuffer: VertexBuffer? = null,
    var indexBuffer: IndexBuffer? = null,

    var viewportX: Float = 0f,
    var viewportY: Float = 0f,
    var viewportWidth: Float = 0f,
    var viewportHeight: Float = 0f,
    var viewportMinDepth: Float = 0f,
    var viewportMaxDepth: Float = 1f,

    var scissorX: Int = 0,
    var scissorY: Int = 0,
    var scissorWidth: Int = 0,
    var scissorHeight: Int = 0,

    var polygonMode: PolygonMode = PolygonMode.FILL,
    var lineWidth: Float = 1f,

    var cullMode: CullMode = CullMode.BACK,
    var frontFace: FrontFace = FrontFace.CLOCKWISE,

    var sampleCount: Int = 1,

    var renderTarget: RenderTarget? = null,

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 96 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(name.address)
        vertexAttributes.pack(buffer)

        buffer.pushInt(vertexBuffer?.slot ?: 0)
        buffer.pushBoolean(instanced)

        buffer.pushInt(primitiveTopology.value)

        vertexShader?.handle.pack(buffer)
        fragmentShader?.handle.pack(buffer)
        geometryShader?.handle.pack(buffer)

        vertexBuffer?.handle.pack(buffer)
        indexBuffer?.handle.pack(buffer)

        buffer.pushFloat(viewportX)
        buffer.pushFloat(viewportY)
        buffer.pushFloat(viewportWidth)
        buffer.pushFloat(viewportHeight)
        buffer.pushFloat(viewportMinDepth)
        buffer.pushFloat(viewportMaxDepth)

        buffer.pushInt(scissorX)
        buffer.pushInt(scissorY)
        buffer.pushInt(scissorWidth)
        buffer.pushInt(scissorHeight)

        buffer.pushInt(polygonMode.value)
        buffer.pushFloat(lineWidth)

        buffer.pushInt(cullMode.value)
        buffer.pushInt(frontFace.value)

        buffer.pushInt(sampleCount)

        renderTarget?.handle.pack(buffer)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        buffer.nextLong()
        vertexAttributes.unpack(buffer)

        val vertexBufferSlot = buffer.nextInt()
        vertexBuffer?.slot = vertexBufferSlot
        instanced = buffer.nextBoolean()

        primitiveTopology = IntEnum.from(buffer.nextInt())

        vertexShader?.handle.unpack(buffer)
        fragmentShader?.handle.unpack(buffer)
        geometryShader?.handle.unpack(buffer)

        vertexBuffer?.handle.unpack(buffer)
        indexBuffer?.handle.unpack(buffer)

        viewportX = buffer.nextFloat()
        viewportY = buffer.nextFloat()
        viewportWidth = buffer.nextFloat()
        viewportHeight = buffer.nextFloat()
        viewportMinDepth = buffer.nextFloat()
        viewportMaxDepth = buffer.nextFloat()

        scissorX = buffer.nextInt()
        scissorY = buffer.nextInt()
        scissorWidth = buffer.nextInt()
        scissorHeight = buffer.nextInt()

        polygonMode = IntEnum.from(buffer.nextInt())
        lineWidth = buffer.nextFloat()

        cullMode = IntEnum.from(buffer.nextInt())
        frontFace = IntEnum.from(buffer.nextInt())

        sampleCount = buffer.nextInt()

        renderTarget?.handle.unpack(buffer)

        return this
    }
}

/* ============================================================
 * COMPUTE PIPELINE INFO
 * ============================================================ */

data class ComputePipelineInfo(
    var name: NativeString = NativeString(),
    var computeShader: Shader? = null,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 16 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(name.address)
        computeShader?.handle.pack(buffer)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        buffer.nextLong()
        computeShader?.handle.unpack(buffer)
        return this
    }
}

/* ============================================================
 * COLOR ATTACHMENT
 * ============================================================ */

data class ColorAttachment(
    var texture: Texture? = null,
    var clearColor: Vec4 = Vec4(0f, 0f, 0f, 1f),
    var blend: Blend = Blend(),

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 48 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        texture?.handle.pack(buffer)
        clearColor.pack(buffer)
        blend.pack(buffer)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        texture?.handle.unpack(buffer)
        clearColor.unpack(buffer)
        blend.unpack(buffer)
        return this
    }
}

/* ============================================================
 * DEPTH / STENCIL
 * ============================================================ */

data class DepthAttachment(
    var texture: Texture? = null,
    var enabled: Boolean = false,
    var depthClearValue: Float = 1f,
    var depthCompareOp: CompareOp = CompareOp.LESS,
    var depthReadOnly: Boolean = false,
    var depthWriteEnabled: Boolean = true,

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 28 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        texture?.handle.pack(buffer)
        buffer.pushBoolean(enabled)
        buffer.pushFloat(depthClearValue)
        buffer.pushInt(depthCompareOp.value)
        buffer.pushBoolean(depthReadOnly)
        buffer.pushBoolean(depthWriteEnabled)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        texture?.handle.unpack(buffer)
        enabled = buffer.nextBoolean()
        depthClearValue = buffer.nextFloat()
        depthCompareOp = IntEnum.from(buffer.nextInt())
        depthReadOnly = buffer.nextBoolean()
        depthWriteEnabled = buffer.nextBoolean()
        return this
    }
}

data class StencilAttachment(
    var texture: Texture? = null,
    var enabled: Boolean = false,
    var stencilClearValue: Int = 0,
    var stencilReadOnly: Boolean = false,

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 24 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        texture?.handle.pack(buffer)
        buffer.pushBoolean(enabled)
        buffer.pushInt(stencilClearValue)
        buffer.pushBoolean(stencilReadOnly)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        texture?.handle.unpack(buffer)
        enabled = buffer.nextBoolean()
        stencilClearValue = buffer.nextInt()
        stencilReadOnly = buffer.nextBoolean()
        return this
    }
}

/* ============================================================
 * RENDER TARGET
 * ============================================================ */

data class RenderTargetInfo(
    var name: NativeString = NativeString(),

    var x: Int = 0,
    var y: Int = 0,
    var width: Int = 0,
    var height: Int = 0,
    var depth: Int = 0,

    val colorAttachments: NativeDataList<ColorAttachment> = NativeDataList(),
    val depthAttachment: DepthAttachment = DepthAttachment(),
    val stencilAttachment: StencilAttachment = StencilAttachment(),

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object { const val SIZEOF = 48 }

    override fun sizeBytes(layout: MemoryLayout) = SIZEOF

    override fun pack(buffer: NativeBuffer) {
        buffer.pushLong(name.address)

        buffer.pushInt(x)
        buffer.pushInt(y)
        buffer.pushInt(width)
        buffer.pushInt(height)
        buffer.pushInt(depth)

        colorAttachments.pack(buffer)
        depthAttachment.pack(buffer)
        stencilAttachment.pack(buffer)
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        buffer.nextLong()

        x = buffer.nextInt()
        y = buffer.nextInt()
        width = buffer.nextInt()
        height = buffer.nextInt()
        depth = buffer.nextInt()

        colorAttachments.unpack(buffer)
        depthAttachment.unpack(buffer)
        stencilAttachment.unpack(buffer)

        return this
    }
}
