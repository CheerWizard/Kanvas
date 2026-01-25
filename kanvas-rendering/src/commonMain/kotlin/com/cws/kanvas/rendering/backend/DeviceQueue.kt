package com.cws.kanvas.rendering.backend

data class DeviceQueueInfo(
    val familyIndex: Int = 0,
)

expect class DeviceQueueHandle

expect class DeviceQueue(context: RenderContext, config: DeviceQueueInfo) : Resource<DeviceQueueHandle, DeviceQueueInfo> {
    override fun onCreate()
    override fun onRelease()
    fun reset()
}