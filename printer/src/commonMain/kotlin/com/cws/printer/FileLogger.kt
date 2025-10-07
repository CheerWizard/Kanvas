package com.cws.printer

expect class FileLogger : ILogger {
    override fun open()
    override fun close()
    override fun log(
        logLevel: LogLevel,
        tag: String,
        message: String,
        exception: Throwable?,
    )
}