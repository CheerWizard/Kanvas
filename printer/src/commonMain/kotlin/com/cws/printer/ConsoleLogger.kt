package com.cws.printer

expect class ConsoleLogger() : ILogger {
    override fun open()
    override fun close()
    override fun log(
        logLevel: LogLevel,
        tag: String,
        message: String,
        exception: Throwable?,
    )
}