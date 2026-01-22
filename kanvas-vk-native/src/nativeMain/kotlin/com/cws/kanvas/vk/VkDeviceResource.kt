@file:OptIn(ExperimentalNativeApi::class, ExperimentalForeignApi::class)

package com.cws.kanvas.vk

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cstr
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.set
import kotlinx.cinterop.value
import vulkan.*
import kotlin.experimental.ExperimentalNativeApi

data class VkQueueFamilyIndices(
    var graphics: Int = -1,
    var present: Int = -1,
    var compute: Int = -1,
    var transfer: Int = -1,
)

@CName("vk_device_create")
fun vk_device_create(): Int {
    return VkResources.create(VkDeviceResource().apply {
        create()
    })
}

class VkDeviceResource : VkResource {

    private var device: VkDevice? = null
    var physicalDevice: VkPhysicalDevice? = null

//    var queue: DeviceQueue? = null
//        private set

    var properties: VkPhysicalDeviceProperties? = null
    val queueFamilies = mutableListOf<VkQueueFamilyProperties>()
    val layers = mutableListOf<VkLayerProperties>()
    val extensions = mutableListOf<VkExtensionProperties>()
    val queueFamilyIndices = VkQueueFamilyIndices()

    fun create() {
        val physical = physicalDevice ?: return

        memScoped {

            run {
                val count = alloc<UIntVar>()
                vkEnumerateDeviceLayerProperties(physical, count.ptr, null)

                if (count.value > 0u) {
                    val props = allocArray<VkLayerProperties>(count.value.toInt())
                    vkEnumerateDeviceLayerProperties(physical, count.ptr, props)
                    for (i in 0 until count.value.toInt()) {
                        layers += props[i]
                    }
                }
            }

            run {
                val count = alloc<UIntVar>()
                vkEnumerateDeviceExtensionProperties(physical, null, count.ptr, null)

                if (count.value > 0u) {
                    val props = allocArray<VkExtensionProperties>(count.value.toInt())
                    vkEnumerateDeviceExtensionProperties(physical, null, count.ptr, props)
                    for (i in 0 until count.value.toInt()) {
                        extensions += props[i]
                    }
                }
            }

            vkGetPhysicalDeviceProperties(physical, properties?.ptr)

            run {
                val count = alloc<UIntVar>()
                vkGetPhysicalDeviceQueueFamilyProperties(physical, count.ptr, null)

                val qProps = allocArray<VkQueueFamilyProperties>(count.value.toInt())
                vkGetPhysicalDeviceQueueFamilyProperties(physical, count.ptr, qProps)

                for (i in 0 until count.value.toInt()) {
                    val flags = qProps[i].queueFlags

                    when {
                        flags and VK_QUEUE_GRAPHICS_BIT != 0u -> {
                            queueFamilyIndices.graphics = i
                            queueFamilyIndices.present = i
                        }
                        flags and VK_QUEUE_COMPUTE_BIT != 0u -> queueFamilyIndices.compute = i
                        flags and VK_QUEUE_TRANSFER_BIT != 0u -> queueFamilyIndices.transfer = i
                    }

                    queueFamilies += qProps[i]
                }
            }

            require(queueFamilyIndices.graphics >= 0) {
                "No graphics queue found"
            }

            val priority = allocArray<FloatVar>(1)
            priority[0] = 1.0f

            val queueCreateInfo = alloc<VkDeviceQueueCreateInfo>().apply {
                sType = VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO
                queueFamilyIndex = queueFamilyIndices.graphics.toUInt()
                queueCount = 1u
                pQueuePriorities = priority
            }

            val requiredExtensions = listOf(
                VK_KHR_SWAPCHAIN_EXTENSION_NAME
            )

//            check(checkExtensions(requiredExtensions)) {
//                "Missing required device extensions"
//            }

            val ppExtensions = allocArray<CPointerVar<ByteVar>>(requiredExtensions.size)

            requiredExtensions.forEachIndexed { i, ext ->
                ppExtensions[i] = ext.cstr.ptr
            }

            val createInfo = alloc<VkDeviceCreateInfo>().apply {
                sType = VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
                pQueueCreateInfos = queueCreateInfo.ptr
                queueCreateInfoCount = 1u
                enabledExtensionCount = requiredExtensions.size.toUInt()
                ppEnabledExtensionNames = ppExtensions
            }

            val pDevice = alloc<VkDeviceVar>()
            vkCreateDevice(
                physical,
                createInfo.ptr,
                null,
                pDevice.ptr
            ).assert()
            device = pDevice.value
        }

//        resolveFeatures(
//            requested = config.enabledFeatures,
//            required = config.requiredFeatures,
//            supported = getSupportedFeatures(),
//        )

        VkPhysicalDeviceFeatures2KHR

//        queue = DeviceQueue(this, DeviceQueueConfig(queueFamilyIndices.graphics))
    }

}