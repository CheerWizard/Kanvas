package com.cws.kanvas.vk

import com.cws.std.memory.CString
import java.nio.ByteBuffer
import java.nio.ByteOrder

private fun alloc(size: Int) =
    ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())

/* ===================== CONTEXT ===================== */

data class VkContextInfo(
    var applicationName: CString,
    var engineName: CString,
    var nativeWindow: Long,
    var width: Int,
    var height: Int,
    var frameCount: Int,
    private val buffer: ByteBuffer = alloc(SIZEOF)
) {
    companion object {
        const val SIZEOF = 40
    }

    fun pack(): ByteBuffer = buffer.apply {
        putLong(0, applicationName.address)
        putLong(8, engineName.address)
        putLong(16, nativeWindow)
        putInt(24, width)
        putInt(28, height)
        putInt(32, frameCount)
    }
}

/* ===================== BUFFER ===================== */

data class VkBufferInfo(
    var memoryType: VkMemoryPropertyFlagBits = VkMemoryPropertyFlagBits.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT,
    var usages: Int = 0,
    var size: Long = 0,
    var mapOnCreate: Boolean = false,
    private val buffer: ByteBuffer = alloc(SIZEOF)
) {
    companion object { const val SIZEOF = 24 }

    fun pack(): ByteBuffer = buffer.apply {
        putInt(0, memoryType.value)
        putInt(4, usages)
        putLong(8, size)
        putInt(16, if (mapOnCreate) 1 else 0)
    }
}

/* ===================== SAMPLER ===================== */

data class VkSamplerInfo(
    var magFilter: VkFilter = VkFilter.VK_FILTER_LINEAR,
    var minFilter: VkFilter = VkFilter.VK_FILTER_LINEAR,
    var addressModeU: VkSamplerAddressMode = VkSamplerAddressMode.VK_SAMPLER_ADDRESS_MODE_REPEAT,
    var addressModeV: VkSamplerAddressMode = VkSamplerAddressMode.VK_SAMPLER_ADDRESS_MODE_REPEAT,
    var addressModeW: VkSamplerAddressMode = VkSamplerAddressMode.VK_SAMPLER_ADDRESS_MODE_REPEAT,
    var enableAnisotropy: Boolean = true,
    var maxAnisotropy: Float = 1f,
    var unnormalizedCoordinates: Boolean = false,
    var enableCompare: Boolean = false,
    var compareOp: VkCompareOp = VkCompareOp.VK_COMPARE_OP_ALWAYS,
    var mipmapMode: VkSamplerMipmapMode = VkSamplerMipmapMode.VK_SAMPLER_MIPMAP_MODE_LINEAR,
    var borderColor: VkBorderColor = VkBorderColor.VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK,
    var mipLodBias: Float = 0f,
    var minLod: Float = 0f,
    var maxLod: Float = 0f,
    private val buffer: ByteBuffer = alloc(SIZEOF)
) {
    companion object { const val SIZEOF = 64 }

    fun pack(): ByteBuffer = buffer.apply {
        putInt(0, magFilter.value)
        putInt(4, minFilter.value)
        putInt(8, addressModeU.value)
        putInt(12, addressModeV.value)
        putInt(16, addressModeW.value)
        putInt(20, if (enableAnisotropy) 1 else 0)
        putFloat(24, maxAnisotropy)
        putInt(28, if (unnormalizedCoordinates) 1 else 0)
        putInt(32, if (enableCompare) 1 else 0)
        putInt(36, compareOp.value)
        putInt(40, mipmapMode.value)
        putInt(44, borderColor.value)
        putFloat(48, mipLodBias)
        putFloat(52, minLod)
        putFloat(56, maxLod)
    }
}

/* ===================== TEXTURE ===================== */

data class VkTextureInfo(
    var type: VkImageViewType = VkImageViewType.VK_IMAGE_VIEW_TYPE_2D,
    var memoryType: VkMemoryPropertyFlagBits = VkMemoryPropertyFlagBits.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT,
    var width: Int = 0,
    var height: Int = 0,
    var depth: Int = 1,
    var format: VkFormat = VkFormat.VK_FORMAT_R8G8B8A8_UNORM,
    var mips: Int = 1,
    var baseMip: Int = 1,
    private val buffer: ByteBuffer = alloc(SIZEOF)
) {
    companion object { const val SIZEOF = 32 }

    fun pack(): ByteBuffer = buffer.apply {
        putInt(0, type.value)
        putInt(4, memoryType.value)
        putInt(8, width)
        putInt(12, height)
        putInt(16, depth)
        putInt(20, format.value)
        putInt(24, mips)
        putInt(28, baseMip)
    }
}

/* ===================== BLEND ===================== */

data class VkBlend(
    var enable: Boolean = false,
    var srcFactorColor: VkBlendFactor = VkBlendFactor.VK_BLEND_FACTOR_ONE,
    var dstFactorColor: VkBlendFactor = VkBlendFactor.VK_BLEND_FACTOR_ZERO,
    var blendOpColor: VkBlendOp = VkBlendOp.VK_BLEND_OP_ADD,
    var srcFactorAlpha: VkBlendFactor = VkBlendFactor.VK_BLEND_FACTOR_ONE,
    var dstFactorAlpha: VkBlendFactor = VkBlendFactor.VK_BLEND_FACTOR_ZERO,
    var blendOpAlpha: VkBlendOp = VkBlendOp.VK_BLEND_OP_ADD,
    private val buffer: ByteBuffer = alloc(SIZEOF)
) {
    companion object { const val SIZEOF = 28 }

    fun pack(): ByteBuffer = buffer.apply {
        putInt(0, if (enable) 1 else 0)
        putInt(4, srcFactorColor.value)
        putInt(8, dstFactorColor.value)
        putInt(12, blendOpColor.value)
        putInt(16, srcFactorAlpha.value)
        putInt(20, dstFactorAlpha.value)
        putInt(24, blendOpAlpha.value)
    }
}

/* ===================== ATTRIBUTE ===================== */

data class VkAttribute(
    var location: Int = 0,
    var type: VkAttributeType = VkAttributeType.VK_ATTRIBUTE_TYPE_VEC3,
    var format: VkAttributeFormat = VkAttributeFormat.VK_ATTRIBUTE_FORMAT_FLOAT,
    private val buffer: ByteBuffer = alloc(SIZEOF)
) {
    companion object { const val SIZEOF = 12 }

    fun pack(): ByteBuffer = buffer.apply {
        putInt(0, location)
        putInt(4, type.value)
        putInt(8, format.value)
    }
}

/* ===================== BINDING ===================== */

data class VkBinding(
    var type: VkBindingType = VkBindingType.VK_BINDING_TYPE_UNIFORM_BUFFER,
    var shaderStages: Int = 0,
    var set: Int = 0,
    var binding: Int = 0,
    var count: Int = 1,
    private val buffer: ByteBuffer = alloc(SIZEOF)
) {
    companion object { const val SIZEOF = 20 }

    fun pack(): ByteBuffer = buffer.apply {
        putInt(0, type.value)
        putInt(4, shaderStages)
        putInt(8, set)
        putInt(12, binding)
        putInt(16, count)
    }
}

/* ===================== BINDING INFO ===================== */

data class VkBindingInfo(
    var bindings: Long = 0L,
    var bindingsCount: Long = 0,
    private val buffer: ByteBuffer = alloc(SIZEOF)
) {
    companion object { const val SIZEOF = 16 }

    fun pack(): ByteBuffer = buffer.apply {
        putLong(0, bindings)
        putLong(8, bindingsCount)
    }
}

// ==================== VkOffset2D ====================

data class VkOffset2D(
    var x: Int = 0,
    var y: Int = 0,

    private val buffer: ByteBuffer = alloc(SIZEOF)
) {

    companion object {
        const val SIZEOF = 8
    }

    constructor(buffer: ByteBuffer?) : this(buffer = buffer!!) {
        unpack()
    }

    private fun unpack() {
        buffer.clear()
        x = buffer.int
        y = buffer.int
    }

    fun pack(): ByteBuffer {
        buffer.clear()
        buffer.putInt(x)
        buffer.putInt(y)
        buffer.flip()
        return buffer
    }
}


// ==================== VkExtent2D ====================

data class VkExtent2D(
    var width: Int = 0,
    var height: Int = 0,

    private val buffer: ByteBuffer = alloc(SIZEOF)
) {

    companion object {
        const val SIZEOF = 8
    }

    constructor(buffer: ByteBuffer?) : this(buffer = buffer!!) {
        unpack()
    }

    private fun unpack() {
        buffer.clear()
        width = buffer.int
        height = buffer.int
    }

    fun pack(): ByteBuffer {
        buffer.clear()
        buffer.putInt(width)
        buffer.putInt(height)
        buffer.flip()
        return buffer
    }
}


// ==================== VkRect2D ====================

data class VkRect2D(
    var offset: VkOffset2D = VkOffset2D(),
    var extent: VkExtent2D = VkExtent2D(),

    private val buffer: ByteBuffer = alloc(SIZEOF)
) {

    companion object {
        const val SIZEOF =
            VkOffset2D.SIZEOF +
                    VkExtent2D.SIZEOF
    }

    constructor(buffer: ByteBuffer?) : this(buffer = buffer!!) {
        unpack()
    }

    private fun unpack() {
        buffer.clear()

        offset = VkOffset2D(buffer.slice().limit(VkOffset2D.SIZEOF))
        buffer.position(buffer.position() + VkOffset2D.SIZEOF)

        extent = VkExtent2D(buffer.slice().limit(VkExtent2D.SIZEOF))
    }

    fun pack(): ByteBuffer {
        buffer.clear()
        buffer.put(offset.pack())
        buffer.put(extent.pack())
        buffer.flip()
        return buffer
    }
}


// ==================== VkViewport ====================

data class VkViewport(
    var x: Float = 0f,
    var y: Float = 0f,
    var width: Float = 0f,
    var height: Float = 0f,
    var minDepth: Float = 0f,
    var maxDepth: Float = 1f,

    private val buffer: ByteBuffer = alloc(SIZEOF)
) {

    companion object {
        const val SIZEOF = 24
    }

    constructor(buffer: ByteBuffer?) : this(buffer = buffer!!) {
        unpack()
    }

    private fun unpack() {
        buffer.clear()

        x = buffer.float
        y = buffer.float
        width = buffer.float
        height = buffer.float
        minDepth = buffer.float
        maxDepth = buffer.float
    }

    fun pack(): ByteBuffer {
        buffer.clear()

        buffer.putFloat(x)
        buffer.putFloat(y)
        buffer.putFloat(width)
        buffer.putFloat(height)
        buffer.putFloat(minDepth)
        buffer.putFloat(maxDepth)

        buffer.flip()
        return buffer
    }
}


// ==================== VkPipelineInfo ====================

data class VkPipelineInfo(

    var vertexAttributes: Long = 0L,
    var vertexAttributesCount: Long = 0,

    var vertexBufferSlot: Int = 0,
    var instanced: Boolean = false,

    var primitiveTopology: VkPrimitiveTopology =
        VkPrimitiveTopology.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST,

    var vertexShader: Long = 0L,
    var fragmentShader: Long = 0L,
    var geometryShader: Long = 0L,

    var vertexBuffer: Long = 0L,
    var indexBuffer: Long = 0L,

    var viewport: VkViewport = VkViewport(),
    var scissor: VkRect2D = VkRect2D(),

    var polygonMode: VkPolygonMode = VkPolygonMode.VK_POLYGON_MODE_FILL,
    var lineWidth: Float = 1.0f,

    var cullMode: VkCullModeFlagBits = VkCullModeFlagBits.VK_CULL_MODE_BACK_BIT,
    var frontFace: VkFrontFace = VkFrontFace.VK_FRONT_FACE_CLOCKWISE,

    var sampleCount: Int = 1,

    var renderTarget: Long = 0L,

    private val buffer: ByteBuffer = alloc(SIZEOF)
) {

    companion object {

        const val SIZEOF =
            8 + // vertexAttributes*
                    8 + // count
                    4 + // slot
                    4 + // instanced
                    4 + // topology
                    8 + // vs*
                    8 + // fs*
                    8 + // gs*
                    8 + // vb
                    8 + // ib
                    VkViewport.SIZEOF +
                    VkRect2D.SIZEOF +
                    4 + // polygon
                    4 + // lineWidth
                    4 + // cull
                    4 + // front
                    4 + // sample
                    8   // renderTarget*
    }

    constructor(buffer: ByteBuffer?) : this(buffer = buffer!!) {
        unpack()
    }

    private fun unpack() {
        buffer.clear()

        vertexAttributes = buffer.long
        vertexAttributesCount = buffer.long

        vertexBufferSlot = buffer.int
        instanced = buffer.int > 0

        primitiveTopology = VkPrimitiveTopology.from(buffer.int)

        vertexShader = buffer.long
        fragmentShader = buffer.long
        geometryShader = buffer.long

        vertexBuffer = buffer.long
        indexBuffer = buffer.long

        viewport = VkViewport(buffer.slice().limit(VkViewport.SIZEOF))
        buffer.position(buffer.position() + VkViewport.SIZEOF)

        scissor = VkRect2D(buffer.slice().limit(VkRect2D.SIZEOF))
        buffer.position(buffer.position() + VkRect2D.SIZEOF)

        polygonMode = VkPolygonMode.from(buffer.int)
        lineWidth = buffer.float

        cullMode = VkCullModeFlagBits.from(buffer.int)
        frontFace = VkFrontFace.from(buffer.int)

        sampleCount = buffer.int

        renderTarget = buffer.long
    }

    fun pack(): ByteBuffer {
        buffer.clear()

        buffer.putLong(vertexAttributes)
        buffer.putLong(vertexAttributesCount)

        buffer.putInt(vertexBufferSlot)
        buffer.putInt(if (instanced) 1 else 0)

        buffer.putInt(primitiveTopology.value)

        buffer.putLong(vertexShader)
        buffer.putLong(fragmentShader)
        buffer.putLong(geometryShader)

        buffer.putLong(vertexBuffer)
        buffer.putLong(indexBuffer)

        buffer.put(viewport.pack())
        buffer.put(scissor.pack())

        buffer.putInt(polygonMode.value)
        buffer.putFloat(lineWidth)

        buffer.putInt(cullMode.value)
        buffer.putInt(frontFace.value)

        buffer.putInt(sampleCount)

        buffer.putLong(renderTarget)

        buffer.flip()
        return buffer
    }
}
