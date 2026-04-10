package com.cws.kanvas.editor.logger

import com.cws.print.LogLevel

data class UiLog(
    val logLevel: LogLevel,
    val tag: String,
    val message: String,
    val stackTrace: String?,
    val timestamp: Long,
)