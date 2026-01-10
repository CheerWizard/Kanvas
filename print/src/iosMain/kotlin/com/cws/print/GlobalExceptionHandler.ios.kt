package com.cws.print

import platform.Foundation.NSHomeDirectory

actual fun GlobalExceptionHandler(context: Context, block: (Throwable) -> Unit) {
    NativeExceptionHandler.install(NSHomeDirectory() + "/Library/Caches/PrintCrash.log")
}