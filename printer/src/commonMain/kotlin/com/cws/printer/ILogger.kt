package com.cws.printer

interface ILogger {
    fun open()
    fun close()
    fun log(logLevel: LogLevel, tag: String, message: String, exception: Throwable? = null)
}