package com.cws.kanvas.core

import com.cws.kanvas.core.WindowConfig
import kotlinx.cinterop.ExperimentalForeignApi

actual class Window : BaseWindow {

    @OptIn(ExperimentalForeignApi::class)
    actual constructor(config: WindowConfig) : super(config)

    actual fun isClosed(): Boolean = false

    override fun dispatchEvent(event: Any) {
        super.dispatchEvent(event)
    }

}