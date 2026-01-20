package com.cws.kanvas.vk

interface VkResource

object VkResources {

    private val resources = ArrayList<VkResource?>()
    private val handles = IntList()

    fun <T : VkResource> create(resource: T): Int {
        return if (handles.isEmpty()) {
            resources.add(resource)
            resources.lastIndex
        } else {
            val handle = handles.pop()
            resources[handle] = resource
            handle
        }
    }

    fun release(handle: Int) {
        resources[handle] = null
        handles.push(handle)
    }

    operator fun <T : VkResource> get(handle: Int): T? = resources[handle] as T?

}