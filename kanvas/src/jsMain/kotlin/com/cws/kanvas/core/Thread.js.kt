package com.cws.kanvas.core

import com.cws.kanvas.core.concurrency.Task
import kotlinx.coroutines.DelicateCoroutinesApi
import org.w3c.dom.Worker

actual open class PlatformThread actual constructor(
    start: Boolean,
    actual val name: String,
    actual val priority: Int,
    private val task: Task,
) {

    private val worker = Worker("worker.js")

    init {
        worker.onmessage = { msg ->
            when (msg.data) {
                "start" -> task()
            }
        }

        if (start) {
            start()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    actual fun start() {
        worker.postMessage("start")
    }

    actual fun join() {
        worker.terminate()
    }

}
