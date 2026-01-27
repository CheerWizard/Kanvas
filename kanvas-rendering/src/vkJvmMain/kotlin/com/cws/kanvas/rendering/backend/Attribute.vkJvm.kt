package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VkAttributeFormat

actual enum class AttributeFormat(val value: VkAttributeFormat) {
    FLOAT(VkAttributeFormat.VK_ATTRIBUTE_FORMAT_FLOAT),
    FLOAT2(VkAttributeFormat.VK_ATTRIBUTE_FORMAT_FLOAT2),
    FLOAT3(VkAttributeFormat.VK_ATTRIBUTE_FORMAT_FLOAT3),
    FLOAT4(VkAttributeFormat.VK_ATTRIBUTE_FORMAT_FLOAT4)
}