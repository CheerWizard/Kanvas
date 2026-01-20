package com.cws.kanvas.rendering.backend

import com.cws.std.memory.NativeBuffer

actual enum class BufferUsage(actual val value: Int) {
    TRANSFER_SRC(VK_BUFFER_USAGE_TRANSFER_SRC_BIT),
    TRANSFER_DST(VK_BUFFER_USAGE_TRANSFER_DST_BIT),
    UNIFORM_TEXEL(VK_BUFFER_USAGE_UNIFORM_TEXEL_BUFFER_BIT),
    STORAGE_TEXEL(VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT),
    UNIFORM_BUFFER(VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT),
    STORAGE_BUFFER(VK_BUFFER_USAGE_STORAGE_BUFFER_BIT),
    INDEX_BUFFER(VK_BUFFER_USAGE_INDEX_BUFFER_BIT),
    VERTEX_BUFFER(VK_BUFFER_USAGE_VERTEX_BUFFER_BIT),
    INDIRECT_BUFFER(VK_BUFFER_USAGE_INDIRECT_BUFFER_BIT);
}

actual typealias BufferHandle = Long

actual open class Buffer actual constructor(
    device: Device,
    config: BufferConfig
) : Resource<BufferHandle, BufferConfig>(config) {

    private var allocation: Long = VK_NULL_HANDLE
    private var mapped: NativeBuffer? = null

    actual override fun onCreate() {
        val allocator = VulkanAllocator.handle ?: return

        stack { stack ->
            val bufferInfo = VkBufferCreateInfo.calloc(stack).apply {
                sType(VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO)
                size(config.size)
                usage(config.usages)
                sharingMode(VK_SHARING_MODE_EXCLUSIVE)
            }

            val memoryUsage = when (config.memoryType) {
                MemoryType.HOST -> VMA_MEMORY_USAGE_AUTO_PREFER_HOST
                MemoryType.DEVICE_LOCAL -> VMA_MEMORY_USAGE_AUTO_PREFER_DEVICE
                else -> VMA_MEMORY_USAGE_AUTO
            }

            val allocInfo = VmaAllocationCreateInfo.calloc(stack).apply {
                usage(memoryUsage)
            }

            val pBuffer = stack.mallocLong(1)
            val pAllocation = stack.mallocPointer(1)

            vmaCreateBuffer(
                allocator,
                bufferInfo,
                allocInfo,
                pBuffer,
                pAllocation,
                null
            )

            handle = pBuffer[0]
            allocation = pAllocation[0]
        }
    }

    actual override fun onRelease() {
        val handle = handle ?: return
        val allocator = VulkanAllocator.handle ?: return
        unmap()
        vmaDestroyBuffer(allocator, handle, allocation)
    }

    actual fun write(data: NativeBuffer, srcOffset: Int, destOffset: Int, size: Int) {
        if (mapped == null) map()
        data.copyTo(mapped!!, srcOffset, destOffset, size)
    }

    actual fun read(data: NativeBuffer, srcOffset: Int, destOffset: Int, size: Int) {
        if (mapped == null) map()
        mapped?.copyTo(data, srcOffset, destOffset, size)
    }

    private fun map() {
        require(VulkanAllocator.handle != null) {
            "VulkanAllocator must be created!"
        }

        val pData = MemoryStack.stackGet().mallocPointer(1)

        vmaMapMemory(VulkanAllocator.handle!!, allocation, pData)

        mapped = NativeBuffer(MemoryUtil.memByteBuffer(
            pData[0],
            config.size.toInt()
        ))
    }

    private fun unmap() {
        val allocator = VulkanAllocator.handle ?: return
        if (mapped != null) {
            vmaUnmapMemory(allocator, allocation)
        }
        mapped = null
    }

}