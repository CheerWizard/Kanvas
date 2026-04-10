package com.cws.print

fun getCurrentTimestamp(): String {
    return getCurrentTimeMillis().formatDateTime("dd.MM.YYYY HH:mm:ss")
}

fun formatLog(logLevel: LogLevel, tag: String, message: String, exception: Throwable? = null): String {
    return if (exception == null) {
        "${getCurrentTimestamp()} $logLevel $tag: $message"
    } else {
        "${getCurrentTimestamp()} $logLevel $tag: $message\n${exception.stackTraceToString()}"
    }
}