@file:OptIn(ExperimentalForeignApi::class)

package com.cws.kanvas.core.concurrency

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.alloc
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.value
import platform.posix.pthread_create
import platform.posix.pthread_join
import platform.posix.pthread_setname_np
import platform.posix.pthread_tVar

actual open class PlatformThread actual constructor(
    start: Boolean,
    actual val name: String,
    actual val priority: Int,
    private val task: Task,
) {

    private val thread = nativeHeap.alloc<pthread_tVar>()

    init {
        if (start) {
            start()
        }
    }

    actual fun start() {
        // create reference to Kotlin task function
        val stableRef = StableRef.create(task)
        // provide it to C function to be called from C
        pthread_create(
            thread.ptr,
            null,
            staticCFunction { arg ->
                pthread_setname_np(name)
                val ref = arg!!.asStableRef<Task>()
                ref.get().invoke()
                ref.dispose()
                null
            },
            stableRef.asCPointer()
        )
    }

    actual fun join() {
        pthread_join(thread.value, null)
    }

}