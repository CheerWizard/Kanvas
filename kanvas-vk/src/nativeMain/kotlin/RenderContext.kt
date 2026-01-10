import kotlinx.cinterop.*
import vulkan.*

fun VK_MAKE_VERSION(major: Int, minor: Int, patch: Int): UInt =
    ((major shl 22) or (minor shl 12) or patch).toUInt()

val VK_API_VERSION_1_0: UInt = VK_MAKE_VERSION(1, 0, 0)
val VK_API_VERSION_1_1: UInt = VK_MAKE_VERSION(1, 1, 0)
val VK_API_VERSION_1_2: UInt = VK_MAKE_VERSION(1, 2, 0)
val VK_API_VERSION_1_3: UInt = VK_MAKE_VERSION(1, 3, 0)

@OptIn(ExperimentalForeignApi::class)
class RenderContext() {

    private var instance: VkInstance? = null

    fun createVulkanInstance(): VkInstance? = memScoped {
        val appInfo = alloc<VkApplicationInfo>().apply {
            sType = VK_STRUCTURE_TYPE_APPLICATION_INFO
            pNext = null
            pApplicationName = "MyApp".cstr.getPointer(memScope)
            applicationVersion = VK_MAKE_VERSION(1, 0, 0)
            pEngineName = "MyEngine".cstr.getPointer(memScope)
            engineVersion = VK_MAKE_VERSION(1, 0, 0)
            apiVersion = VK_API_VERSION_1_3
        }

        val createInfo = alloc<VkInstanceCreateInfo>().apply {
            sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
            pNext = null
            pApplicationInfo = appInfo.ptr
            flags = 0u
            enabledLayerCount = 0u
            ppEnabledLayerNames = null
            enabledExtensionCount = 0u
            ppEnabledExtensionNames = null
        }

        val pInstance = alloc<VkInstanceVar>()
        val result = vkCreateInstance(createInfo.ptr, null, pInstance.ptr)
        if (result != VK_SUCCESS) throw RuntimeException("Failed to create Vulkan instance: $result")
        pInstance.value
    }

    fun destroyInstance() {
        instance?.let { vkDestroyInstance(it, null) }
    }
}