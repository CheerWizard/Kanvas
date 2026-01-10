package com.cws.kanvas.rendering.backend

data class DeviceQueueConfig(
    val familyIndex: Int = 0,
)

expect class DeviceQueueHandle

expect class DeviceQueue(device: Device, config: DeviceQueueConfig) : Resource<DeviceQueueHandle, DeviceQueueConfig> {
    override fun onCreate()
    override fun onRelease()
    fun reset()
}

data class DeviceConfig(
    val requiredFeatures: Set<Feature> = emptySet(),
    val enabledFeatures: Set<Feature> = emptySet(),
)

expect class DeviceHandle

expect class Device(config: DeviceConfig) : Resource<DeviceHandle, DeviceConfig> {
    var queue: DeviceQueue?
        private set

    override fun onCreate()
    override fun onRelease()

    fun getSupportedFeatures(): Set<Feature>
}

internal fun Device.resolveFeatures(
    requested: Set<Feature>,
    required: Set<Feature>,
    supported: Set<Feature>,
): Set<Feature> {
    val missingRequired = required - supported
    require(missingRequired.isEmpty()) {
        "Missing required GPU features: $missingRequired"
    }
    return requested intersect supported
}