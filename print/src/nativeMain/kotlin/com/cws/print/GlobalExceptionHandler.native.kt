package com.cws.print

actual fun GlobalExceptionHandler(context: Context, block: (Throwable) -> Unit) {
    NativeExceptionHandler.install("./PrintCrash.log")
}