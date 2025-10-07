package com.cws.printer

import platform.Foundation.NSLog

actual class ConsoleLogger actual constructor() : ILogger {

    actual override fun open() {
        // do nothing
    }

    actual override fun close() {
        // do nothing
    }

    actual override fun log(logLevel: LogLevel, tag: String, message: String, exception: Throwable?) {
        val formattedMessage = "${logLevel.toColorCode()}$logLevel $tag: $message"
        NSLog(formattedMessage)
        exception?.printStackTrace()
    }

}