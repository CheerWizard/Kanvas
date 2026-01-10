package com.cws.kanvas.rendering.backend

import org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkCommandPoolCreateInfo
import org.lwjgl.vulkan.VkDevice
import org.lwjgl.vulkan.VkDeviceCreateInfo
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo
import org.lwjgl.vulkan.VkExtensionProperties
import org.lwjgl.vulkan.VkLayerProperties
import org.lwjgl.vulkan.VkPhysicalDevice
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures
import org.lwjgl.vulkan.VkPhysicalDeviceProperties
import org.lwjgl.vulkan.VkQueue
import org.lwjgl.vulkan.VkQueueFamilyProperties
import java.nio.ByteBuffer

data class QueueFamilyIndices(
    var graphics: Int = -1,
    var present: Int = -1,
    var compute: Int = -1,
    var transfer: Int = -1,
)

actual typealias DeviceHandle = VkDevice

actual class Device actual constructor(config: DeviceConfig) : Resource<DeviceHandle, DeviceConfig>(config) {

    var physicalDevice: VkPhysicalDevice? = null

    actual var queue: DeviceQueue? = null
        private set

    val properties = VkPhysicalDeviceProperties.calloc()
    val queueFamilies = ArrayList<VkQueueFamilyProperties>()
    val layers = ArrayList<VkLayerProperties>()
    val extensions = ArrayList<VkExtensionProperties>()
    val queueFamilyIndices = QueueFamilyIndices()

    actual override fun onCreate() {
        val physicalDevice = physicalDevice ?: return

        handle = stack { stack ->

            // Get layers
            val pCount = stack.mallocInt(1)
            vkEnumerateDeviceLayerProperties(physicalDevice, pCount, null)
            val layerCount = pCount[0]
            if (layerCount > 0) {
                val layerBuf = VkLayerProperties.calloc(layerCount, stack)
                vkEnumerateDeviceLayerProperties(physicalDevice, pCount, layerBuf)
                for (i in 0 until layerCount) {
                    layers += layerBuf[i]
                }
            }

            // Get extensions
            val layerName: ByteBuffer? = null
            vkEnumerateDeviceExtensionProperties(physicalDevice, layerName, pCount, null)
            val extCount = pCount[0]
            if (extCount > 0) {
                val extBuf = VkExtensionProperties.calloc(extCount, stack)
                vkEnumerateDeviceExtensionProperties(physicalDevice, layerName, pCount, extBuf)
                for (i in 0 until extCount) {
                    extensions += extBuf[i]
                }
            }

            // Get properties
            vkGetPhysicalDeviceProperties(physicalDevice, properties)

            // Get queue families
            vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pCount, null)
            val qCount = pCount[0]
            val qBuf = VkQueueFamilyProperties.calloc(qCount, stack)
            vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pCount, qBuf)
            for (i in 0 until qCount) {
                val flags = qBuf[i].queueFlags()

                when {
                    flags and VK_QUEUE_GRAPHICS_BIT != 0 -> {
                        queueFamilyIndices.graphics = i
                        queueFamilyIndices.present = i
                    }
                    flags and VK_QUEUE_COMPUTE_BIT != 0 ->
                        queueFamilyIndices.compute = i
                    flags and VK_QUEUE_TRANSFER_BIT != 0 ->
                        queueFamilyIndices.transfer = i
                }

                queueFamilies += qBuf[i]
            }

            val queuePriority = stack.floats(1.0f)

            val queueCreateInfos = VkDeviceQueueCreateInfo.calloc(1, stack)
            queueCreateInfos.get(0).apply {
                sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                queueFamilyIndex(queueFamilyIndices.graphics)
                pQueuePriorities(queuePriority)
            }

            val requiredExtensions = arrayOf(
                VK_KHR_SWAPCHAIN_EXTENSION_NAME
            )

            check(
                checkExtensions(requiredExtensions)
            ) { "Failed to find required extensions for device" }

            val ppExtensions = stack.mallocPointer(requiredExtensions.size)
            for (ext in requiredExtensions) {
                ppExtensions.put(stack.UTF8(ext))
            }
            ppExtensions.flip()

            // create device
            val deviceCreateInfo = VkDeviceCreateInfo.calloc(stack).apply {
                sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
                pQueueCreateInfos(queueCreateInfos)
                ppEnabledExtensionNames(ppExtensions)
            }

            val pDevice = stack.mallocPointer(1)
            vkCreateDevice(
                physicalDevice,
                deviceCreateInfo,
                VulkanAllocator.callbacks,
                pDevice
            )
            VkDevice(pDevice[0], physicalDevice, deviceCreateInfo)
        }

        resolveFeatures(
            requested = config.enabledFeatures,
            required = config.requiredFeatures,
            supported = getSupportedFeatures(),
        )

        // TODO this only creates graphics queue
        queue = DeviceQueue(this, DeviceQueueConfig(queueFamilyIndices.graphics))
    }

    actual override fun onRelease() {
        handle?.let { device ->
            vkDeviceWaitIdle(device)
            vkDestroyDevice(device, VulkanAllocator.callbacks)
        }
    }

    actual fun getSupportedFeatures(): Set<Feature> {
        return physicalDevice?.let { physicalDevice ->
            stack { stack ->
                val vkFeatures = VkPhysicalDeviceFeatures.calloc(stack)
                vkGetPhysicalDeviceFeatures(physicalDevice, vkFeatures)
                vkFeatures.toFeatures()
            }
        }.orEmpty()
    }

    private fun VkPhysicalDeviceFeatures.toFeatures(): Set<Feature> {
        return emptySet()
    }

    private fun checkExtension(name: String): Boolean = extensions.any { it.extensionNameString() == name }

    private fun checkExtensions(required: Array<String>): Boolean = required.all { checkExtension(it) }

    fun waitIdle() {
        handle?.let { handle ->
            vkDeviceWaitIdle(handle)
        }
    }

}

actual typealias DeviceQueueHandle = VkQueue

actual class DeviceQueue actual constructor(
    private val device: Device,
    config: DeviceQueueConfig
) : Resource<DeviceQueueHandle, DeviceQueueConfig>(config) {

    private var pool: Long = VK_NULL_HANDLE

    actual override fun onCreate() {
        val device = device.handle ?: return

        stack { stack ->
            val pQueue = stack.mallocPointer(1)
            vkGetDeviceQueue(device, config.familyIndex, 0, pQueue)
            handle = VkQueue(pQueue[0], device)

            val poolInfo = VkCommandPoolCreateInfo.calloc(stack).apply {
                sType(VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO)
                flags(VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT)
                queueFamilyIndex(config.familyIndex)
            }

            val pPool = stack.mallocLong(1)
            vkCreateCommandPool(
                device,
                poolInfo,
                VulkanAllocator.callbacks,
                pPool
            )
            pool = pPool[0]
        }
    }

    actual override fun onRelease() {
        device.handle?.let { device ->
            vkDestroyCommandPool(device, pool, VulkanAllocator.callbacks)
        }
    }

    actual fun reset() {
        device.handle?.let { device ->
            vkResetCommandPool(
                device,
                pool,
                0
            )
        }
    }

}