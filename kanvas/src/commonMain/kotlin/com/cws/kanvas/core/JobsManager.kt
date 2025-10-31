package com.cws.kanvas.core

import com.cws.printer.Printer
import kotlinx.atomicfu.locks.ReentrantLock
import kotlinx.atomicfu.locks.withLock
import kotlinx.coroutines.Runnable

class JobsManager {

    companion object {
        private const val TAG = "JobsManager"
    }

    private val jobs = ArrayDeque<Runnable>()
    private val lock = ReentrantLock()

    fun push(runnable: Runnable) {
        lock.withLock {
            jobs.addLast(runnable)
        }
    }

    fun pop(): Runnable {
        lock.withLock {
            return jobs.removeFirst()
        }
    }

    fun execute() {
        lock.withLock {
            while (jobs.isNotEmpty()) {
                val job = jobs.removeFirst()
                try {
                    job.run()
                } catch (e: Throwable) {
                    Printer.e(TAG, "Failed to execute job $job", e)
                }
            }
        }
    }

}