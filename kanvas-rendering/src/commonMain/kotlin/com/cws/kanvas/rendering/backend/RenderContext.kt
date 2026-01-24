package com.cws.kanvas.rendering.backend

import com.cws.kanvas.rendering.Version

data class RenderContextInfo(
    val appName: String = "",
    val appVersion: Version = Version(1, 0, 0),
    val engineName: String = "",
    val engineVersion: Version = Version(1, 0, 0),
    val surface: Any? = null,
    val enableValidation: Boolean = false,
)

expect class RenderContextHandle
expect class DeviceHandle

expect class RenderContext(config: RenderContextInfo) : Resource<RenderContextHandle, RenderContextInfo> {
    override fun onCreate()
    override fun onRelease()
}
