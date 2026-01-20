package com.cws.kanvas.vk

typealias VkVersion = UInt

fun VK_MAKE_VERSION(major: Int, minor: Int, patch: Int): VkVersion =
    ((major shl 22) or (minor shl 12) or patch).toUInt()

val VK_API_VERSION_1_0: VkVersion = VK_MAKE_VERSION(1, 0, 0)
val VK_API_VERSION_1_1: VkVersion = VK_MAKE_VERSION(1, 1, 0)
val VK_API_VERSION_1_2: VkVersion = VK_MAKE_VERSION(1, 2, 0)
val VK_API_VERSION_1_3: VkVersion = VK_MAKE_VERSION(1, 3, 0)