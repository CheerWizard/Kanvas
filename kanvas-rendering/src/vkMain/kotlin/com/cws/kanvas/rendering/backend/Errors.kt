package com.cws.kanvas.rendering.backend

import com.cws.print.Print
import org.lwjgl.vulkan.VK10.VK_SUCCESS

class VulkanException(errorCode: Int): RuntimeException()

inline fun Int.onFailure(block: (Int) -> Unit) {
    if (this != VK_SUCCESS) {
        val e = VulkanException(this)
        Print.e("Vulkan", "Failed to execute Vulkan command!", e)
    }
}
