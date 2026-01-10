package com.cws.print

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class BasePrint {

    var logLevel: LogLevel = LogLevel.NONE

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        reportCrash(throwable)
    }

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val mutex = Mutex()
    private val loggers = mutableSetOf<Logger>()

    fun install(
        context: Context,
        logLevel: LogLevel = LogLevel.VERBOSE,
        loggers: Set<Logger> = setOf(ConsoleLogger()),
        block: () -> Unit,
    ) {
        launch {
            this.logLevel = logLevel
            loggers.forEach { this.loggers.add(it) }
            this.loggers.forEach { it.open() }
        }

        try {
            block()
        } catch (e: Throwable) {
            reportCrash(e)
        }

        GlobalExceptionHandler(context) { throwable ->
            reportCrash(throwable)
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

    fun addLogger(logger: Logger) {
        loggers.add(logger)
    }

    fun removeLogger(logger: Logger) {
        loggers.remove(logger)
    }

    fun v(tag: String, message: String) = log(LogLevel.VERBOSE, tag, message)
    fun i(tag: String, message: String) = log(LogLevel.INFO, tag, message)
    fun d(tag: String, message: String) = log(LogLevel.DEBUG, tag, message)
    fun w(tag: String, message: String, exception: Throwable? = null) = log(LogLevel.WARNING, tag, message, exception)
    fun e(tag: String, message: String, exception: Throwable? = null) = log(LogLevel.ERROR, tag, message, exception)

    fun log(logLevel: LogLevel, tag: String, message: String, exception: Throwable? = null) {
        if (this.logLevel <= logLevel && this.logLevel != LogLevel.NONE) {
            launch {
                loggers.forEach { logger ->
                    logger.log(logLevel, tag, message, exception)
                }
            }
        }
    }

    fun log(logLevel: Int, tag: String, message: String, exceptionMessage: String? = null) {
        log(
            LogLevel.entries[logLevel],
            tag,
            message,
            exceptionMessage?.let { RuntimeException(it) }
        )
    }

    private fun reportCrash(e: Throwable) {
        log(LogLevel.FATAL, "Print", "Crash reported!", e)
    }

    private inline fun launch(crossinline block: () -> Unit) {
        scope.launch {
            mutex.withLock {
                block()
            }
        }
    }

}