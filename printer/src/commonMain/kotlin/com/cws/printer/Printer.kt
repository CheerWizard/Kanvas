package com.cws.printer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object Printer {

    var logLevel: LogLevel = LogLevel.NONE

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val mutex = Mutex()
    private val loggers = mutableSetOf<ILogger>()

    fun init(
        logLevel: LogLevel = LogLevel.VERBOSE,
        loggers: Set<ILogger> = emptySet()
    ) {
        launch {
            this.logLevel = logLevel
            this.loggers.add(ConsoleLogger())
            loggers.forEach { this.loggers.add(it) }
            this.loggers.forEach { it.open() }
        }
    }

    // optional to call, not really required to call from client side
    fun close() {
        launch {
            loggers.forEach { logger ->
                logger.close()
            }
        }
    }

    fun addLogger(logger: ILogger) {
        loggers.add(logger)
    }

    fun removeLogger(logger: ILogger) {
        loggers.remove(logger)
    }

    fun v(tag: String, message: String) = log(LogLevel.VERBOSE, tag, message)
    fun i(tag: String, message: String) = log(LogLevel.INFO, tag, message)
    fun d(tag: String, message: String) = log(LogLevel.DEBUG, tag, message)
    fun w(tag: String, message: String) = log(LogLevel.WARNING, tag, message)
    fun e(tag: String, message: String) = log(LogLevel.ERROR, tag, message)
    fun e(tag: String, message: String, exception: Throwable) = log(LogLevel.FATAL, tag, message, exception)

    private fun log(logLevel: LogLevel, tag: String, message: String, exception: Throwable? = null) {
        if (this.logLevel <= logLevel && this.logLevel != LogLevel.NONE) {
            launch {
                loggers.forEach { logger ->
                    logger.log(logLevel, tag, message, exception)
                }
            }
        }
    }

    private inline fun launch(crossinline block: () -> Unit) {
        scope.launch {
            mutex.withLock {
                block()
            }
        }
    }

}

