package com.cws.kanvas.rendering.backend

import org.lwjgl.vulkan.KHRWin32Surface.VK_KHR_WIN32_SURFACE_EXTENSION_NAME
import org.lwjgl.vulkan.KHRXlibSurface.VK_KHR_XLIB_SURFACE_EXTENSION_NAME

actual val SURFACE_EXTENSION_NAME: String? = when {
    System.getProperty("os.name").contains("Linux", true) ->
        VK_KHR_XLIB_SURFACE_EXTENSION_NAME
    System.getProperty("os.name").contains("Windows", true) ->
        VK_KHR_WIN32_SURFACE_EXTENSION_NAME
    else -> null
}
