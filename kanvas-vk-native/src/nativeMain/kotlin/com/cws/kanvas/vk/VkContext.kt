@file:OptIn(ExperimentalNativeApi::class, ExperimentalForeignApi::class)

package com.cws.kanvas.vk

import kotlinx.cinterop.*
import vulkan.*
import kotlin.experimental.ExperimentalNativeApi

@CName("VkContext_create")
fun VkContext_create(
    appName: CPointer<ByteVar>,
    appVersion: VkVersion = VK_API_VERSION_1_0,
    engineName: CPointer<ByteVar>,
    engineVersion: VkVersion = VK_API_VERSION_1_0,
    enableSurface: Boolean = false,
    enableValidation: Boolean = false,
): Int {
    return VkResources.create(VkContext(
        appName = appName,
        appVersion = appVersion,
        engineName = engineName,
        engineVersion = engineVersion,
        enableSurface = enableSurface,
        enableValidation = enableValidation,
    ))
}

@CName("VkContext_release")
fun VkContext_release(handle: Int) {
    VkResources.get<VkContext>(handle)?.release()
}

@OptIn(ExperimentalForeignApi::class)
class VkContext(
    appName: CPointer<ByteVar>,
    appVersion: VkVersion = VK_API_VERSION_1_0,
    engineName: CPointer<ByteVar>,
    engineVersion: VkVersion = VK_API_VERSION_1_0,
    enableSurface: Boolean = true,
    enableValidation: Boolean = false,
) : VkResource {

    private var instance: VkInstance? = null

//    private val debugCallback =
//        staticCFunction<VkDebugUtilsMessengerCallbackDataEXT> { severity, type, data, _ ->
//            if (severity == VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT ||
//                severity == VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
//            ) {
//                val message = data?.pointed?.pMessage?.toKString()
//                println("Vulkan: type=$type message=$message")
//            }
//            VK_FALSE
//        }

    private var debugMessenger: VkDebugUtilsMessengerEXT? = null

    private val layers = mutableListOf<VkLayerProperties>()
    private val extensions = mutableListOf<VkExtensionProperties>()

    fun release() {
        instance?.let { instance ->
            debugMessenger?.let {
                vkDestroyDebugUtilsMessengerEXT(instance, it, null)
            }
            debugMessenger = null
            vkDestroyInstance(instance, null)
        }
        instance = null
    }

    init {
        memScoped {
            val appInfo = alloc<VkApplicationInfo>().apply {
                sType = VK_STRUCTURE_TYPE_APPLICATION_INFO
                pApplicationName = appName
                applicationVersion = appVersion
                pEngineName = engineName
                this.engineVersion = engineVersion
                apiVersion = VK_API_VERSION_1_3
            }

            val extensionNames = mutableListOf<String>()

            if (enableSurface) {
                extensionNames += VK_KHR_SURFACE_EXTENSION_NAME
            }

            if (enableValidation) {
                extensionNames += VK_EXT_DEBUG_UTILS_EXTENSION_NAME
                extensionNames += VK_KHR_GET_PHYSICAL_DEVICE_PROPERTIES_2_EXTENSION_NAME
            }

            val ppExtensions = allocArray<CPointerVar<ByteVar>>(extensionNames.size)
            extensionNames.forEachIndexed { i, ext ->
                ppExtensions[i] = ext.cstr.ptr
            }

            val instanceCreateInfo = alloc<VkInstanceCreateInfo>().apply {
                sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
                pApplicationInfo = appInfo.ptr
                enabledExtensionCount = extensionNames.size.toUInt()
                ppEnabledExtensionNames = ppExtensions
            }

            enumerateLayers()
            enumerateExtensions()

            if (enableValidation) {
                val validationLayers = listOf("VK_LAYER_KHRONOS_validation")

                if (checkLayers(validationLayers)) {
                    val ppLayers = allocArray<CPointerVar<ByteVar>>(validationLayers.size)
                    validationLayers.forEachIndexed { i, l ->
                        ppLayers[i] = l.cstr.ptr
                    }
                    instanceCreateInfo.enabledLayerCount = validationLayers.size.toUInt()
                    instanceCreateInfo.ppEnabledLayerNames = ppLayers
                }

                val debugInfo = alloc<VkDebugUtilsMessengerCreateInfoEXT>().apply {
                    sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT
                    messageSeverity =
                        VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT or
                                VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
                    messageType =
                        VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT or
                                VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT or
                                VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT
//                    pfnUserCallback = debugCallback
                }

                instanceCreateInfo.pNext = debugInfo.ptr
            }

            val pInstance = alloc<VkInstanceVar>()
            vkCreateInstance(instanceCreateInfo.ptr, null, pInstance.ptr)
                .assert()
            instance = pInstance.value

            findDevice()
        }
    }

    private fun enumerateLayers() = memScoped {
        val count = alloc<UIntVar>()
        vkEnumerateInstanceLayerProperties(count.ptr, null)
        val props = allocArray<VkLayerProperties>(count.value.toInt())
        vkEnumerateInstanceLayerProperties(count.ptr, props)
        for (i in 0 until count.value.toInt()) layers += props[i]
    }

    private fun enumerateExtensions() = memScoped {
        val count = alloc<UIntVar>()
        vkEnumerateInstanceExtensionProperties(null, count.ptr, null)
        val props = allocArray<VkExtensionProperties>(count.value.toInt())
        vkEnumerateInstanceExtensionProperties(null, count.ptr, props)
        for (i in 0 until count.value.toInt()) extensions += props[i]
    }

    private fun checkLayers(names: List<String>): Boolean =
        names.all { name -> layers.any { it.layerName.toKString() == name } }

    private fun findDevice() = memScoped {
        val instance = instance ?: return@memScoped

        val count = alloc<UIntVar>()
        vkEnumeratePhysicalDevices(instance, count.ptr, null)
        require(count.value > 0u) { "No GPU found" }

        val devices = allocArray<VkPhysicalDeviceVar>(count.value.toInt())
        vkEnumeratePhysicalDevices(instance, count.ptr, devices)

        for (i in 0 until count.value.toInt()) {
            val physical = devices[i]

            val queueCount = alloc<UIntVar>()
            vkGetPhysicalDeviceQueueFamilyProperties(physical, queueCount.ptr, null)

            val queues = allocArray<VkQueueFamilyProperties>(queueCount.value.toInt())
            vkGetPhysicalDeviceQueueFamilyProperties(physical, queueCount.ptr, queues)

            for (j in 0 until queueCount.value.toInt()) {
                if (queues[j].queueFlags and VK_QUEUE_GRAPHICS_BIT != 0u) {
//                    device = Device(config.deviceConfig).apply {
//                        physicalDevice = physical
//                        create()
//                    }
                    return
                }
            }
        }

        error("No suitable GPU found")
    }
}