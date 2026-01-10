package com.cws.kanvas.rendering.backend

expect class FenceHandle

expect class Fence(device: Device) : Resource<FenceHandle, Unit> {
    override fun onCreate()
    override fun onRelease()

    fun wait(timeout: ULong = ULong.MAX_VALUE)
    fun reset()
}
