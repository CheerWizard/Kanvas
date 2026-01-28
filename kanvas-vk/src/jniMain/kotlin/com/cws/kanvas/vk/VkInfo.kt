package com.cws.kanvas.vk

import com.cws.std.memory.CString
import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.NativeDataList
import com.cws.std.memory.float
import com.cws.std.memory.int
import com.cws.std.memory.long
import com.cws.std.memory.popInt
import com.cws.std.memory.pushBoolean
import com.cws.std.memory.pushFloat
import com.cws.std.memory.pushInt
import com.cws.std.memory.pushLong

/* ===================== CONTEXT ===================== */

data class VkContextInfo(
    var applicationName: CString,
    var engineName: CString,
    var width: Int,
    var height: Int,
    var frameCount: Int,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
): INativeData {
    companion object { const val SIZEOF = 32 }

    override fun sizeBytes(layout: MemoryLayout): Int {
        return 32
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {
            pushLong(applicationName.address)
            pushLong(engineName.address)
            pushInt(width)
            pushInt(height)
            pushInt(frameCount)
        }
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        TODO("Not yet implemented")
    }
}

/* ===================== BUFFER ===================== */

data class VkBufferInfo(
    var name: CString,
    var memoryType: VkMemoryPropertyFlagBits =
        VkMemoryPropertyFlagBits.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT,
    var usages: Int = 0,
    var size: Long = 0,
    var mapOnCreate: Boolean = false,
    var isStatic: Boolean = false,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
): INativeData {
    companion object { const val SIZEOF = 28 }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {
            pushLong(name.address)
            pushInt(memoryType.value)
            pushInt(usages)
            pushLong(this@VkBufferInfo.size)
            pushInt(if (mapOnCreate) 1 else 0)
            pushBoolean(isStatic)
        }
    }
}

/* ===================== SAMPLER ===================== */

data class VkSamplerInfo(
    var name: CString,
    var magFilter: VkFilter = VkFilter.VK_FILTER_LINEAR,
    var minFilter: VkFilter = VkFilter.VK_FILTER_LINEAR,
    var addressModeU: VkSamplerAddressMode =
        VkSamplerAddressMode.VK_SAMPLER_ADDRESS_MODE_REPEAT,
    var addressModeV: VkSamplerAddressMode =
        VkSamplerAddressMode.VK_SAMPLER_ADDRESS_MODE_REPEAT,
    var addressModeW: VkSamplerAddressMode =
        VkSamplerAddressMode.VK_SAMPLER_ADDRESS_MODE_REPEAT,
    var enableAnisotropy: Boolean = true,
    var maxAnisotropy: Float = 1f,
    var unnormalizedCoordinates: Boolean = false,
    var enableCompare: Boolean = false,
    var compareOp: VkCompareOp = VkCompareOp.VK_COMPARE_OP_ALWAYS,
    var mipmapMode: VkSamplerMipmapMode =
        VkSamplerMipmapMode.VK_SAMPLER_MIPMAP_MODE_LINEAR,
    var borderColor: VkBorderColor =
        VkBorderColor.VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK,
    var mipLodBias: Float = 0f,
    var minLod: Float = 0f,
    var maxLod: Float = 0f,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
): INativeData {
    companion object { const val SIZEOF = 68 }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {
            pushLong(name.address)

            pushInt(magFilter.value)
            pushInt(minFilter.value)

            pushInt(addressModeU.value)
            pushInt(addressModeV.value)
            pushInt(addressModeW.value)

            pushInt(if (enableAnisotropy) 1 else 0)
            pushFloat(maxAnisotropy)

            pushInt(if (unnormalizedCoordinates) 1 else 0)
            pushInt(if (enableCompare) 1 else 0)

            pushInt(compareOp.value)
            pushInt(mipmapMode.value)
            pushInt(borderColor.value)

            pushFloat(mipLodBias)
            pushFloat(minLod)
            pushFloat(maxLod)
        }
    }
}

/* ===================== TEXTURE ===================== */

data class VkTextureInfo(
    var name: CString,
    var type: VkImageViewType =
        VkImageViewType.VK_IMAGE_VIEW_TYPE_2D,
    var memoryType: VkMemoryPropertyFlagBits =
        VkMemoryPropertyFlagBits.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT,
    var width: Int = 0,
    var height: Int = 0,
    var depth: Int = 1,
    var format: VkFormat =
        VkFormat.VK_FORMAT_R8G8B8A8_UNORM,
    var mips: Int = 1,
    var baseMip: Int = 1,
    var isStatic: Boolean = true,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
): INativeData {
    companion object { const val SIZEOF = 40 }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {
            pushLong(name.address)

            pushInt(type.value)
            pushInt(memoryType.value)

            pushInt(width)
            pushInt(height)
            pushInt(depth)

            pushInt(format.value)
            pushInt(mips)
            pushInt(baseMip)

            pushBoolean(isStatic)
        }
    }
}

/* ===================== BLEND ===================== */

data class VkBlend(
    var enable: Boolean = false,
    var srcFactorColor: VkBlendFactor =
        VkBlendFactor.VK_BLEND_FACTOR_ONE,
    var dstFactorColor: VkBlendFactor =
        VkBlendFactor.VK_BLEND_FACTOR_ZERO,
    var blendOpColor: VkBlendOp =
        VkBlendOp.VK_BLEND_OP_ADD,
    var srcFactorAlpha: VkBlendFactor =
        VkBlendFactor.VK_BLEND_FACTOR_ONE,
    var dstFactorAlpha: VkBlendFactor =
        VkBlendFactor.VK_BLEND_FACTOR_ZERO,
    var blendOpAlpha: VkBlendOp =
        VkBlendOp.VK_BLEND_OP_ADD,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
): INativeData {
    companion object { const val SIZEOF = 28 }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {
            pushInt(if (enable) 1 else 0)

            pushInt(srcFactorColor.value)
            pushInt(dstFactorColor.value)
            pushInt(blendOpColor.value)

            pushInt(srcFactorAlpha.value)
            pushInt(dstFactorAlpha.value)
            pushInt(blendOpAlpha.value)
        }
    }
}

data class VkShaderInfo(
    var name: CString,
    var entryPoint: CString,

    var spirvCode: NativeBuffer? = null,
    var spirvCodeSize: Long = 0,

    val bindingLayouts: LongArray = LongArray(0),
    var bindingLayoutsCount: Long = 0,

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
) : INativeData {

    companion object {
        const val SIZEOF =
            8 + // name*
                    8 + // entryPoint*
                    8 + // spirvCode*
                    8 + // spirvCodeSize
                    8 + // bindingLayouts**
                    8   // bindingLayoutsCount
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {
            pushLong(name.address)
            pushLong(entryPoint.address)

            pushLong(spirvCode?.address ?: 0)
            pushLong(spirvCodeSize)

            repeat(bindingLayoutsCount.toInt()) { i ->
                pushLong(bindingLayouts[i])
            }
            pushLong(bindingLayoutsCount)
        }
    }
}


/* ===================== ATTRIBUTE ===================== */

data class VkAttribute(
    var location: Int = 0,
    var type: VkAttributeType =
        VkAttributeType.VK_ATTRIBUTE_TYPE_VEC3,
    var format: VkAttributeFormat =
        VkAttributeFormat.VK_ATTRIBUTE_FORMAT_FLOAT,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
): INativeData {
    companion object { const val SIZEOF = 12 }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {
            pushInt(location)
            pushInt(type.value)
            pushInt(format.value)
        }
    }
}

/* ===================== BINDING ===================== */

data class VkBinding(
    var type: VkBindingType =
        VkBindingType.VK_BINDING_TYPE_UNIFORM_BUFFER,
    var shaderStages: Int = 0,
    var set: Int = 0,
    var binding: Int = 0,
    var count: Int = 1,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
): INativeData {
    companion object { const val SIZEOF = 20 }

    override fun sizeBytes(layout: MemoryLayout): Int {
        return SIZEOF
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {
            pushInt(type.value)
            pushInt(shaderStages)
            pushInt(set)
            pushInt(binding)
            pushInt(count)
        }
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        type = VkBindingType.from(buffer.popInt())
        shaderStages = buffer.popInt()
        set = buffer.popInt()
        binding = buffer.popInt()
        count = buffer.popInt()
        return this
    }
}

/* ===================== BINDING INFO ===================== */

data class VkBindingInfo(
    var name: CString,
    val bindings: NativeDataList<VkBinding>,
    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
): INativeData {
    companion object { const val SIZEOF = 24 }

    override fun sizeBytes(layout: MemoryLayout): Int {
        return SIZEOF
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {
            pushLong(name.address)
            pushLong(bindings.address)
            pushLong(bindings.count)
        }
    }

    override fun unpack(buffer: NativeBuffer): INativeData {
        TODO("Not yet implemented")
    }
}

/* ==================== VkPipelineInfo ==================== */

data class VkPipelineInfo(
    var name: CString = CString(null),

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

    var polygonMode: VkPolygonMode =
        VkPolygonMode.VK_POLYGON_MODE_FILL,
    var lineWidth: Float = 1.0f,

    var cullMode: VkCullModeFlagBits =
        VkCullModeFlagBits.VK_CULL_MODE_BACK_BIT,
    var frontFace: VkFrontFace =
        VkFrontFace.VK_FRONT_FACE_CLOCKWISE,

    var sampleCount: Int = 1,

    var renderTarget: Long = 0L,

    override val buffer: NativeBuffer? = NativeBuffer(SIZEOF)
): INativeData {
    companion object {
        const val SIZEOF =
            8 +
                    8 +
                    4 +
                    4 +
                    4 +
                    8 +
                    8 +
                    8 +
                    8 +
                    8 +
                    4 +
                    4 +
                    4 +
                    4 +
                    4 +
                    8
    }

    fun unpack(buffer: NativeBuffer?) = buffer?.let {
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

        viewportX = buffer.float
        viewportY = buffer.float
        viewportWidth = buffer.float
        viewportHeight = buffer.float
        viewportMinDepth = buffer.float
        viewportMaxDepth = buffer.float

        scissorX = buffer.int
        scissorY = buffer.int
        scissorWidth = buffer.int
        scissorHeight = buffer.int

        polygonMode = VkPolygonMode.from(buffer.int)
        lineWidth = buffer.float

        cullMode = VkCullModeFlagBits.from(buffer.int)
        frontFace = VkFrontFace.from(buffer.int)

        sampleCount = buffer.int

        renderTarget = buffer.long
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.apply {

            pushLong(name.address)

            pushLong(vertexAttributes)
            pushLong(vertexAttributesCount)

            pushInt(vertexBufferSlot)
            pushInt(if (instanced) 1 else 0)

            pushInt(primitiveTopology.value)

            pushLong(vertexShader)
            pushLong(fragmentShader)
            pushLong(geometryShader)

            pushLong(vertexBuffer)
            pushLong(indexBuffer)

            pushFloat(viewportX)
            pushFloat(viewportY)
            pushFloat(viewportWidth)
            pushFloat(viewportHeight)
            pushFloat(viewportMinDepth)
            pushFloat(viewportMaxDepth)

            pushInt(scissorX)
            pushInt(scissorY)
            pushInt(scissorWidth)
            pushInt(scissorHeight)

            pushInt(polygonMode.value)
            pushFloat(lineWidth)

            pushInt(cullMode.value)
            pushInt(frontFace.value)

            pushInt(sampleCount)

            pushLong(renderTarget)
        }
    }
}
