package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VkBindingType

actual enum class BindingType(val value: VkBindingType) {
    UNIFORM_BUFFER(VkBindingType.VK_BINDING_TYPE_UNIFORM_BUFFER),
    STORAGE_BUFFER(VkBindingType.VK_BINDING_TYPE_STORAGE_BUFFER),
    TEXTURE(VkBindingType.VK_BINDING_TYPE_TEXTURE),
    SAMPLER(VkBindingType.VK_BINDING_TYPE_SAMPLER),
}