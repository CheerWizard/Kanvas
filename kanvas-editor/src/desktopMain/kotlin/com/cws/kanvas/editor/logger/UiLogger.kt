package com.cws.kanvas.editor.logger

import androidx.compose.runtime.mutableIntStateOf
import com.cws.print.LogLevel
import com.cws.print.Logger
import com.cws.print.formatLog
import com.cws.print.getCurrentTimeMillis
import com.cws.std.async.RingBuffer
import com.cws.std.io.File
import com.cws.std.io.write

class UiLogger(
    private val filepath: String,
    private val filename: String,
) : Logger {

    // recomposition trigger
    val revision = mutableIntStateOf(0)
    val logs = RingBuffer<UiLog>(1000)

    private var file: File? = null

    override fun open() {
        file = File("${filepath}/${filename}_${getCurrentTimeMillis()}.logs")
        file?.open()
    }

    override fun close() {
        file?.close()
        file = null
    }

    override fun log(logLevel: LogLevel, tag: String, message: String, exception: Throwable?) {
        logs.push(UiLog(
            logLevel = logLevel,
            tag = tag,
            message = message,
            stackTrace = exception?.stackTraceToString(),
            timestamp = getCurrentTimeMillis(),
        ))
        file?.write(formatLog(logLevel, tag, message, exception))
        if (logs.isFull) {
            repeat(100) {
                logs.removeFirst()
            }
        }
        revision.intValue += 1
    }

}