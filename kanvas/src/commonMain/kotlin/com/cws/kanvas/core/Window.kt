package com.cws.kanvas.core

import com.cws.kanvas.event.EventListener
import kotlinx.atomicfu.locks.ReentrantLock
import kotlinx.atomicfu.locks.withLock
import kotlinx.serialization.Serializable

@Serializable
data class WindowConfig(
    var title: String = "Kanvas",
    var x: Int = 0,
    var y: Int = 0,
    var width: Int = 800,
    var height: Int = 600,
)

open class BaseWindow(
    val config: WindowConfig,
) {

    var title: String = config.title
        set(value) {
            config.title = value
            setTitle(value)
        }

    var x: Int = config.x
        set(value) {
            config.x = value
            setX(value)
        }

    var y: Int = config.y
        set(value) {
            config.y = value
            setY(value)
        }

    var width: Int = config.width
        set(value) {
            config.width = value
            setWidth(value)
        }

    var height: Int = config.height
        set(value) {
            config.height = value
            setHeight(value)
        }

    protected val eventListeners = mutableSetOf<EventListener>()
    private val events = ArrayDeque<Any>()
    private val lock = ReentrantLock()

    protected open fun setTitle(title: String) {}
    protected open fun setX(x: Int) {}
    protected open fun setY(y: Int) {}
    protected open fun setWidth(width: Int) {}
    protected open fun setHeight(height: Int) {}

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

    open fun pollEvents() {
        lock.withLock {
            while (events.isNotEmpty()) {
                dispatchEvent(events.removeFirst())
            }
        }
    }

    protected open fun dispatchEvent(event: Any) {}

}

expect class Window : BaseWindow {

    constructor(config: WindowConfig = WindowConfig())

    fun isClosed(): Boolean

}