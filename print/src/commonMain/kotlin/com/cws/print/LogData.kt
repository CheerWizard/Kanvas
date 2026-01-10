package com.cws.print

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

data class LogData(
    var timestamp: Duration = 0.milliseconds,
    var level: LogLevel = LogLevel.NONE,
    var tag: String = "",
    var message: String = "",
    var exception: Throwable? = null
)
