package com.cws.kanvas.vk

/* ===================== MEMORY ===================== */

enum class VkMemoryPropertyFlagBits(val value: Int) {
    VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT(0x00000001),
    VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT(0x00000002),
    VK_MEMORY_PROPERTY_HOST_COHERENT_BIT(0x00000004),
    VK_MEMORY_PROPERTY_HOST_CACHED_BIT(0x00000008),
    VK_MEMORY_PROPERTY_LAZILY_ALLOCATED_BIT(0x00000010),
    VK_MEMORY_PROPERTY_PROTECTED_BIT(0x00000020),
    VK_MEMORY_PROPERTY_DEVICE_COHERENT_BIT_AMD(0x00000040),
    VK_MEMORY_PROPERTY_DEVICE_UNCACHED_BIT_AMD(0x00000080),
    VK_MEMORY_PROPERTY_RDMA_CAPABLE_BIT_NV(0x00000100),
    VK_MEMORY_PROPERTY_FLAG_BITS_MAX_ENUM(0x7FFFFFFF);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

/* ===================== DESCRIPTORS ===================== */

enum class VkDescriptorType(val value: Int) {
    VK_DESCRIPTOR_TYPE_SAMPLER(0),
    VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER(1),
    VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE(2),
    VK_DESCRIPTOR_TYPE_STORAGE_IMAGE(3),
    VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER(4),
    VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER(5),
    VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER(6),
    VK_DESCRIPTOR_TYPE_STORAGE_BUFFER(7),
    VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC(8),
    VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC(9),
    VK_DESCRIPTOR_TYPE_INPUT_ATTACHMENT(10);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}


/* ===================== SAMPLER ===================== */

enum class VkFilter(val value: Int) {
    VK_FILTER_NEAREST(0),
    VK_FILTER_LINEAR(1);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

enum class VkSamplerAddressMode(val value: Int) {
    VK_SAMPLER_ADDRESS_MODE_REPEAT(0),
    VK_SAMPLER_ADDRESS_MODE_MIRRORED_REPEAT(1),
    VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_EDGE(2),
    VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_BORDER(3);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

enum class VkCompareOp(val value: Int) {
    VK_COMPARE_OP_NEVER(0),
    VK_COMPARE_OP_LESS(1),
    VK_COMPARE_OP_EQUAL(2),
    VK_COMPARE_OP_LESS_OR_EQUAL(3),
    VK_COMPARE_OP_GREATER(4),
    VK_COMPARE_OP_NOT_EQUAL(5),
    VK_COMPARE_OP_GREATER_OR_EQUAL(6),
    VK_COMPARE_OP_ALWAYS(7);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

enum class VkSamplerMipmapMode(val value: Int) {
    VK_SAMPLER_MIPMAP_MODE_NEAREST(0),
    VK_SAMPLER_MIPMAP_MODE_LINEAR(1);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

enum class VkBorderColor(val value: Int) {
    VK_BORDER_COLOR_FLOAT_TRANSPARENT_BLACK(0),
    VK_BORDER_COLOR_INT_TRANSPARENT_BLACK(1),
    VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK(2),
    VK_BORDER_COLOR_INT_OPAQUE_BLACK(3),
    VK_BORDER_COLOR_FLOAT_OPAQUE_WHITE(4),
    VK_BORDER_COLOR_INT_OPAQUE_WHITE(5);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

/* ===================== IMAGE ===================== */

enum class VkImageViewType(val value: Int) {
    VK_IMAGE_VIEW_TYPE_1D(0),
    VK_IMAGE_VIEW_TYPE_2D(1),
    VK_IMAGE_VIEW_TYPE_3D(2),
    VK_IMAGE_VIEW_TYPE_CUBE(3),
    VK_IMAGE_VIEW_TYPE_1D_ARRAY(4),
    VK_IMAGE_VIEW_TYPE_2D_ARRAY(5),
    VK_IMAGE_VIEW_TYPE_CUBE_ARRAY(6);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

/* ===================== FORMAT ===================== */

enum class VkFormat(val value: Int) {
    VK_FORMAT_UNDEFINED(0),
    VK_FORMAT_R8G8B8A8_UNORM(37),
    VK_FORMAT_B8G8R8A8_UNORM(44),
    VK_FORMAT_D32_SFLOAT(126),
    VK_FORMAT_D24_UNORM_S8_UINT(129);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

/* ===================== BLENDING ===================== */

enum class VkBlendFactor(val value: Int) {
    VK_BLEND_FACTOR_ZERO(0),
    VK_BLEND_FACTOR_ONE(1),
    VK_BLEND_FACTOR_SRC_COLOR(2),
    VK_BLEND_FACTOR_ONE_MINUS_SRC_COLOR(3),
    VK_BLEND_FACTOR_DST_COLOR(4),
    VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR(5),
    VK_BLEND_FACTOR_SRC_ALPHA(6),
    VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA(7),
    VK_BLEND_FACTOR_DST_ALPHA(8),
    VK_BLEND_FACTOR_ONE_MINUS_DST_ALPHA(9);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

enum class VkBlendOp(val value: Int) {
    VK_BLEND_OP_ADD(0),
    VK_BLEND_OP_SUBTRACT(1),
    VK_BLEND_OP_REVERSE_SUBTRACT(2),
    VK_BLEND_OP_MIN(3),
    VK_BLEND_OP_MAX(4);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

/* ===================== PIPELINE ===================== */

enum class VkPrimitiveTopology(val value: Int) {
    VK_PRIMITIVE_TOPOLOGY_POINT_LIST(0),
    VK_PRIMITIVE_TOPOLOGY_LINE_LIST(1),
    VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST(3),
    VK_PRIMITIVE_TOPOLOGY_TRIANGLE_STRIP(4);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

enum class VkPolygonMode(val value: Int) {
    VK_POLYGON_MODE_FILL(0),
    VK_POLYGON_MODE_LINE(1),
    VK_POLYGON_MODE_POINT(2);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

enum class VkCullModeFlagBits(val value: Int) {
    VK_CULL_MODE_NONE(0),
    VK_CULL_MODE_FRONT_BIT(0x1),
    VK_CULL_MODE_BACK_BIT(0x2),
    VK_CULL_MODE_FRONT_AND_BACK(0x3);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

enum class VkFrontFace(val value: Int) {
    VK_FRONT_FACE_COUNTER_CLOCKWISE(0),
    VK_FRONT_FACE_CLOCKWISE(1);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

/* ===================== SHADER ATTRIBUTES ===================== */

enum class VkAttributeFormat(val value: Int) {
    VK_ATTRIBUTE_FORMAT_FLOAT(1),
    VK_ATTRIBUTE_FORMAT_FLOAT2(2),
    VK_ATTRIBUTE_FORMAT_FLOAT3(3),
    VK_ATTRIBUTE_FORMAT_FLOAT4(4);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

enum class VkAttributeType(val value: Int) {
    VK_ATTRIBUTE_TYPE_PRIMITIVE(1),
    VK_ATTRIBUTE_TYPE_VEC2(2),
    VK_ATTRIBUTE_TYPE_VEC3(3),
    VK_ATTRIBUTE_TYPE_VEC4(4),
    VK_ATTRIBUTE_TYPE_MAT2(8),
    VK_ATTRIBUTE_TYPE_MAT3(12),
    VK_ATTRIBUTE_TYPE_MAT4(16);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}

/* ===================== BINDINGS ===================== */

enum class VkBindingType(val value: Int) {
    VK_BINDING_TYPE_UNIFORM_BUFFER(8),
    VK_BINDING_TYPE_STORAGE_BUFFER(9),
    VK_BINDING_TYPE_TEXTURE(2),
    VK_BINDING_TYPE_SAMPLER(0),
    VK_BINDING_TYPE_TEXTURE_SAMPLER(1);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}
