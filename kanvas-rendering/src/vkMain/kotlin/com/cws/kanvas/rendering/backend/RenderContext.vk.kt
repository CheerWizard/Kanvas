package com.cws.kanvas.rendering.backend

import com.cws.kanvas.core.Version
import com.cws.print.Print
import org.lwjgl.vulkan.EXTDebugUtils.*
import org.lwjgl.vulkan.KHRGetPhysicalDeviceProperties2.VK_KHR_GET_PHYSICAL_DEVICE_PROPERTIES_2_EXTENSION_NAME
import org.lwjgl.vulkan.KHRSurface.VK_KHR_SURFACE_EXTENSION_NAME
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VK13.VK_API_VERSION_1_3
import org.lwjgl.vulkan.VkApplicationInfo
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackDataEXT
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackEXT
import org.lwjgl.vulkan.VkDebugUtilsMessengerCreateInfoEXT
import org.lwjgl.vulkan.VkExtensionProperties
import org.lwjgl.vulkan.VkInstance
import org.lwjgl.vulkan.VkInstanceCreateInfo
import org.lwjgl.vulkan.VkLayerProperties
import org.lwjgl.vulkan.VkPhysicalDevice
import org.lwjgl.vulkan.VkQueueFamilyProperties

actual typealias RenderContextHandle = VkInstance

actual class RenderContext actual constructor(
    config: RenderContextConfig
) : Resource<RenderContextHandle, RenderContextConfig>(config) {

    companion object {
        private const val TAG = "RenderContext"
    }

    actual var device: Device? = null
        private set

    private var debugMessenger: Long = VK_NULL_HANDLE
    private val layers = mutableListOf<VkLayerProperties>()
    private val extensions = mutableListOf<VkExtensionProperties>()

    private val debugCallback =
        VkDebugUtilsMessengerCallbackEXT.create { severity, type, dataAddress, _ ->
            val data = VkDebugUtilsMessengerCallbackDataEXT.create(dataAddress)

            when (severity) {
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT,
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT -> {
                    Print.e(TAG, "type=$type message=${data.pMessageString()}")
                }
            }

            VK_FALSE
        }

    actual override fun onCreate() {
        createInstance()
        findDevice()
        createSurface()
    }

    private fun createInstance() {
        handle = stack { stack ->

            val appInfo = VkApplicationInfo.calloc(stack).apply {
                sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
                pApplicationName(stack.UTF8(config.appName))
                applicationVersion(config.appVersion.toVkVersion())
                pEngineName(stack.UTF8(config.engineName))
                engineVersion(config.engineVersion.toVkVersion())
                apiVersion(VK_API_VERSION_1_3)
            }

            val extensionNames = mutableListOf<String>()

            SURFACE_EXTENSION_NAME?.let {
                extensionNames += VK_KHR_SURFACE_EXTENSION_NAME
                extensionNames += it
            }

            if (config.enableValidation) {
                extensionNames += VK_EXT_DEBUG_UTILS_EXTENSION_NAME
                extensionNames += VK_KHR_GET_PHYSICAL_DEVICE_PROPERTIES_2_EXTENSION_NAME
            }

            val ppExtensions = stack.pointers(
                *extensionNames.map { stack.UTF8(it) }.toTypedArray()
            )

            val instanceCreateInfo = VkInstanceCreateInfo.calloc(stack).apply {
                sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
                pApplicationInfo(appInfo)
                ppEnabledExtensionNames(ppExtensions)
            }

            run {
                val count = stack.mallocInt(1)
                vkEnumerateInstanceLayerProperties(count, null)
                val props = VkLayerProperties.calloc(count[0], stack)
                vkEnumerateInstanceLayerProperties(count, props)
                for (i in 0 until count[0]) layers += props[i]
            }

            run {
                val count = stack.mallocInt(1)
                vkEnumerateInstanceExtensionProperties(null as String?, count, null)
                val props = VkExtensionProperties.calloc(count[0], stack)
                vkEnumerateInstanceExtensionProperties(null as String?, count, props)
                for (i in 0 until count[0]) extensions += props[i]
            }

            if (config.enableValidation) {
                val validationLayers = arrayOf("VK_LAYER_KHRONOS_validation")

                if (checkLayers(validationLayers)) {
                    instanceCreateInfo.ppEnabledLayerNames(
                        stack.pointers(*validationLayers.map { stack.UTF8(it) }.toTypedArray())
                    )
                } else {
                    Print.w(TAG, "Validation layers not found!")
                }

                val debugInfo = VkDebugUtilsMessengerCreateInfoEXT.calloc(stack).apply {
                    sType(VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT)
                    messageSeverity(
                        VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT or
                                VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
                    )
                    messageType(
                        VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT or
                                VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT or
                                VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT
                    )
                    pfnUserCallback(debugCallback)
                }

                instanceCreateInfo.pNext(debugInfo.address())
            }

            val pInstance = stack.mallocPointer(1)
            vkCreateInstance(instanceCreateInfo, VulkanAllocator.callbacks, pInstance)
            VkInstance(pInstance[0], instanceCreateInfo)
        }
    }

    private fun Version.toVkVersion(): Int = VK_MAKE_VERSION(major, minor, patch)

    private fun findDevice() {
        val handle = this.handle ?: return

        stack { stack ->
            val count = stack.mallocInt(1)
            vkEnumeratePhysicalDevices(handle, count, null)
            require(count[0] > 0) { "No GPU found" }
            val devices = stack.mallocPointer(count[0])
            vkEnumeratePhysicalDevices(handle, count, devices)

            for (i in 0 until count[0]) {
                val physical = VkPhysicalDevice(devices[i], handle)
                vkGetPhysicalDeviceQueueFamilyProperties(physical, count, null)
                val queues = VkQueueFamilyProperties.calloc(count[0], stack)
                vkGetPhysicalDeviceQueueFamilyProperties(physical, count, queues)

                for (j in 0 until count[0]) {
                    if (queues[j].queueFlags() and VK_QUEUE_GRAPHICS_BIT != 0) {
                        device = Device(config.deviceConfig).apply {
                            physicalDevice = physical
                            create()
                        }
                        return
                    }
                }
            }

            Print.e(TAG, "Failed to find suitable GPU with graphics queue")
        }
    }

    private fun checkExtensions(extensions: Array<String>): Boolean = extensions.all { checkExtension(it) }

    private fun checkExtension(name: String): Boolean = extensions.any { it.extensionNameString() == name }

    private fun checkLayers(layers: Array<String>): Boolean = layers.all { checkLayer(it) }

    private fun checkLayer(layer: String): Boolean = layers.any { it.layerNameString() == layer }

    private fun createSurface() {
        val nativeWindow = provideNativeWindow()
    }

    actual override fun onRelease() {
        device?.release()
        handle?.let { handle ->
            if (debugMessenger != VK_NULL_HANDLE) {
                vkDestroyDebugUtilsMessengerEXT(handle, debugMessenger, VulkanAllocator.callbacks)
            }
            debugCallback.free()
            vkDestroyInstance(handle, VulkanAllocator.callbacks)
        }
    }

}