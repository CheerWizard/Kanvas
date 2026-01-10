package com.cws.kanvas.rendering.backend

import com.cws.print.Print
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.util.vma.Vma
import org.lwjgl.util.vma.VmaAllocatorCreateInfo
import org.lwjgl.vulkan.VkAllocationCallbacks

object VulkanAllocator : Resource<Long, Unit>(Unit) {

    private const val TAG = "VulkanAllocator"

    var context: RenderContext? = null

    val callbacks: VkAllocationCallbacks =
        VkAllocationCallbacks.create()
            .pUserData(0)
            .pfnAllocation(::vkAllocate)
            .pfnFree(::vkFree)
            .pfnReallocation(::vkReallocate)
            .pfnInternalAllocation(::vkAllocateNotification)
            .pfnInternalFree(::vkFreeNotification)

    init {
        callbacks.pUserData(thisPtr())
    }

    override fun onCreate() {
        val instance = context?.handle ?: return
        val physicalDevice = context?.device?.physicalDevice ?: return
        val device = context?.device?.handle ?: return

        handle = stack { stack ->
            val createInfo = VmaAllocatorCreateInfo.calloc()
                .instance(instance)
                .physicalDevice(physicalDevice)
                .device(device)
                .pAllocationCallbacks(callbacks)
            val pAllocator = stack.mallocPointer(1)
            Vma.vmaCreateAllocator(createInfo, pAllocator)
            pAllocator.get()
        }
    }

    override fun onRelease() {
        handle?.let { handle ->
            Vma.vmaDestroyAllocator(handle)
        }
        handle = null
    }

    private fun allocate(
        size: Long,
        alignment: Long,
        scope: Int
    ): Long {
        Print.d(TAG, "allocate: size=$size alignment=$alignment scope=$scope")

        val align = maxOf(alignment, Long.SIZE_BYTES.toLong())
        val totalSize = size + align - 1 + Long.SIZE_BYTES

        val original = nmemAlloc(totalSize)
        if (original == 0L) return 0L

        val raw = original + Long.SIZE_BYTES
        val aligned = (raw + align - 1) and (align - 1).inv()

        memPutAddress(aligned - Long.SIZE_BYTES, original)

        return aligned
    }

    private fun free(address: Long) {
        Print.d(TAG, "free: ${address.toString(16)}")
        if (address != 0L) {
            val original = memGetAddress(address - Long.SIZE_BYTES)
            nmemFree(original)
        }
    }

    private fun reallocate(
        oldAddress: Long,
        size: Long,
        alignment: Long,
        scope: Int
    ): Long {
        Print.d(TAG, "reallocate: size=$size alignment=$alignment scope=$scope")

        val newAddress = allocate(size, alignment, scope)
        if (oldAddress != 0L && newAddress != 0L) {
            memCopy(oldAddress, newAddress, size)
            free(oldAddress)
        }
        return newAddress
    }

    private fun allocateNotification(
        size: Long,
        type: Int,
        scope: Int
    ) {
        Print.d(TAG, "allocateNotification: size=$size type=$type scope=$scope")
    }

    private fun freeNotification(
        size: Long,
        type: Int,
        scope: Int
    ) {
        Print.d(TAG, "freeNotification: size=$size type=$type scope=$scope")
    }

    private fun thisPtr(): Long = System.identityHashCode(this).toLong()

    private fun vkAllocate(
        userData: Long,
        size: Long,
        alignment: Long,
        scope: Int
    ): Long {
        return allocate(size, alignment, scope)
    }

    private fun vkFree(
        userData: Long,
        memory: Long
    ) {
        free(memory)
    }

    private fun vkReallocate(
        userData: Long,
        oldMemory: Long,
        size: Long,
        alignment: Long,
        scope: Int
    ): Long {
        return reallocate(oldMemory, size, alignment, scope)
    }

    private fun vkAllocateNotification(
        userData: Long,
        size: Long,
        type: Int,
        scope: Int
    ) {
        allocateNotification(size, type, scope)
    }

    private fun vkFreeNotification(
        userData: Long,
        size: Long,
        type: Int,
        scope: Int
    ) {
        freeNotification(size, type, scope)
    }
}