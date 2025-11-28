package com.cws.kanvas.core.async

class ThreadPool(
    name: String,
    priority: Int,
    size: Int,
    private var start: Boolean = false,
) {

    private var running = false
    private val tasks = ConcurrentQueue<Task>(100)
    private val threads: Array<PlatformThread> = Array(size) {
        PlatformThread(
            name = "$name-1",
            priority = priority,
            task = ::runLoop,
            start = start,
        )
    }

    fun start() {
        if (!start) {
            start = true
            threads.forEach { thread ->
                thread.start()
            }
        }
    }

    fun release() {
        start = false
        running = false
    }

    fun submit(task: Task) {
        tasks.push(task)
    }

    private fun runLoop() {
        running = true
        while (running) {
            val task = tasks.pop()
            task?.invoke()
        }
    }

}