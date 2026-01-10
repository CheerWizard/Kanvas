package com.cws.print

import java.io.File

actual fun GlobalExceptionHandler(context: Context, block: (Throwable) -> Unit) {
    val previous = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
        block(throwable)
        previous?.uncaughtException(thread, throwable)
    }

    val androidContext = context as? android.content.Context ?: error("Invalid Context type, must be android.content.Context!")
    val filepath = File(androidContext.filesDir, "PrintCrash.log").absolutePath
    NativeExceptionHandler.install(filepath)
}