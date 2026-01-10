package com.cws.kanvas.rendering.backend

import com.cws.kanvas.core.Version

data class RenderContextConfig(
    val appName: String = "",
    val appVersion: Version = Version(1, 0, 0),
    val engineName: String = "",
    val engineVersion: Version = Version(1, 0, 0),
    val enableValidation: Boolean = false,
    val deviceConfig: DeviceConfig = DeviceConfig(),
)

expect class RenderContextHandle

expect class RenderContext(config: RenderContextConfig) : Resource<RenderContextHandle, RenderContextConfig> {
    var device: Device?
        private set

    override fun onCreate()
    override fun onRelease()
}
