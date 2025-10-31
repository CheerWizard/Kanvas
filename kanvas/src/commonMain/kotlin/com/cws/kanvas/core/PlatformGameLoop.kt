package com.cws.kanvas.core

expect open class PlatformGameLoop(name: String, priority: Int) {
    protected var running: Boolean
    fun startLoop()
    fun stopLoop()
    protected open fun onCreate()
    protected open fun onDestroy()
    protected open fun onUpdate(dtMillis: Float)
}