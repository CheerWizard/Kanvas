package com.cws.kanvas.rendering.backend

import org.lwjgl.vulkan.KHRAndroidSurface.VK_KHR_ANDROID_SURFACE_EXTENSION_NAME
import org.lwjgl.vulkan.KHRAndroidSurface.VK_STRUCTURE_TYPE_ANDROID_SURFACE_CREATE_INFO_KHR
import org.lwjgl.vulkan.KHRAndroidSurface.vkCreateAndroidSurfaceKHR
import org.lwjgl.vulkan.VkAndroidSurfaceCreateInfoKHR

actual fun RenderContext.provideNativeWindow(): Long {
    val instance = handle ?: return 0L

    return stack { stack ->
        val createInfo = VkAndroidSurfaceCreateInfoKHR.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_ANDROID_SURFACE_CREATE_INFO_KHR)
            // TODO: add JNI bridge to ANativeWindow
            window()
        }

        val pSurface = stack.mallocLong(1)

        vkCreateAndroidSurfaceKHR(
            instance,
            createInfo,
            null,
            pSurface
        )

        pSurface[0]
    }
}

actual val SURFACE_EXTENSION_NAME: String? = VK_KHR_ANDROID_SURFACE_EXTENSION_NAME