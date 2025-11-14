package com.cws.kanvas.core

import com.cws.kanvas.core.concurrency.Task
import kotlin.concurrent.thread

actual open class PlatformThread actual constructor(
    start: Boolean,
    actual val name: String,
    actual val priority: Int,
    task: Task,
) {

    private val thread = thread(
        start = start,
        name = name,
        priority = priority,
        block = task,
    )

    actual fun start() {
        thread.start()
    }

    actual fun join() {
        thread.join()
    }

}