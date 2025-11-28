package com.cws.kanvas.core.async

expect open class PlatformThread(
    start: Boolean = false,
    name: String,
    priority: Int,
    task: Task,
) {
    val name: String
    val priority: Int

    fun start()
    fun join()
}