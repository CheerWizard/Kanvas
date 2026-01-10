package com.cws.kanvas.rendering.backend

expect class SemaphoreHandle

expect class Semaphore(device: Device) : Resource<SemaphoreHandle, Unit> {
    override fun onCreate()
    override fun onRelease()
}
