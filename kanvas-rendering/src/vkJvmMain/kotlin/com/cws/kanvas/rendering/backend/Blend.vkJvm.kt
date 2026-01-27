package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VkBlendFactor
import com.cws.kanvas.vk.VkBlendOp

actual enum class BlendOp(val value: VkBlendOp) {
    ADD(VkBlendOp.VK_BLEND_OP_ADD),
    SUBTRACT(VkBlendOp.VK_BLEND_OP_SUBTRACT),
    REVERSE_SUBTRACT(VkBlendOp.VK_BLEND_OP_REVERSE_SUBTRACT),
    MIN(VkBlendOp.VK_BLEND_OP_MIN),
    MAX(VkBlendOp.VK_BLEND_OP_MAX)
}

actual enum class BlendFactor(val value: VkBlendFactor) {
    ZERO(VkBlendFactor.VK_BLEND_FACTOR_ZERO),
    ONE(VkBlendFactor.VK_BLEND_FACTOR_ONE),
    SRC_COLOR(VkBlendFactor.VK_BLEND_FACTOR_SRC_COLOR),
    ONE_MINUS_SRC_COLOR(VkBlendFactor.VK_BLEND_FACTOR_ONE_MINUS_SRC_COLOR),
    DST_COLOR(VkBlendFactor.VK_BLEND_FACTOR_DST_COLOR),
    ONE_MINUS_DST_COLOR(VkBlendFactor.VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR),
    SRC_ALPHA(VkBlendFactor.VK_BLEND_FACTOR_SRC_ALPHA),
    ONE_MINUS_SRC_ALPHA(VkBlendFactor.VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA),
    DST_ALPHA(VkBlendFactor.VK_BLEND_FACTOR_DST_ALPHA),
    ONE_MINUS_DST_ALPHA(VkBlendFactor.VK_BLEND_FACTOR_ONE_MINUS_DST_ALPHA)
}