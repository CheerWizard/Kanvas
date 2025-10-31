package com.cws.kanvas.core

import com.cws.kanvas.config.WindowConfig
import com.cws.kanvas.event.EventListener
import kotlinx.atomicfu.locks.ReentrantLock
import kotlinx.atomicfu.locks.withLock

open class BaseWindow(
    val config: WindowConfig
) {

    val eventListeners = mutableSetOf<EventListener>()
    val events = ArrayDeque<Any>()
    val lock = ReentrantLock()

    fun addEventListener(eventListener: EventListener) {
        lock.withLock {
            eventListeners.add(eventListener)
        }
    }

    fun removeEventListener(eventListener: EventListener) {
        lock.withLock {
            eventListeners.remove(eventListener)
        }
    }

    fun addEvent(event: Any) {
        lock.withLock {
            events.addLast(event)
        }
    }

    fun pollEvents() {
        lock.withLock {
            while (events.isNotEmpty()) {
                dispatchEvent(events.removeFirst())
            }
        }
    }

    open fun dispatchEvent(event: Any) {}

}

expect class Window : BaseWindow {

    constructor(config: WindowConfig)

    fun isClosed(): Boolean

}