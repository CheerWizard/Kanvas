package com.cws.kanvas.core

import android.view.MotionEvent

actual class Window : BaseWindow {

    actual constructor(config: WindowConfig) : super(config)

    actual fun isClosed(): Boolean = false

    override fun dispatchEvent(event: Any) {
        if (event is MotionEvent) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> eventListeners.forEach { it.onTapPressed(event.x, event.y) }
                MotionEvent.ACTION_UP -> eventListeners.forEach { it.onTapReleased(event.x, event.y) }
            }
        }
    }

}