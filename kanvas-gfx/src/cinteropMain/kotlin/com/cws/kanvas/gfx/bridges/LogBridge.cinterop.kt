@file:OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)

package com.cws.kanvas.gfx.bridges

import com.cws.printer.LogLevel
import com.cws.printer.Printer
import kotlinx.cinterop.*
import kotlin.experimental.ExperimentalNativeApi

typealias CLogBridge = CPointer<CFunction<(
    Int,
    CPointer<ByteVar>?,
    CPointer<ByteVar>?,
    CPointer<ByteVar>?
) -> Unit>>

@CName("LogBridge_nativeInit")
fun setLogBridge(bridge: CLogBridge?) {
    logBridge = bridge
}

var logBridge: CLogBridge? = null

fun initLogBridge() {
    val logBridge = staticCFunction {
        level: Int,
        tag: CPointer<ByteVar>?,
        message: CPointer<ByteVar>?,
        exception: CPointer<ByteVar>? ->

        val tagStr = tag?.toKString() ?: ""
        val msgStr = message?.toKString() ?: ""
        val excStr = exception?.toKString()

        Printer.log(
            LogLevel.entries[level],
            tagStr,
            msgStr,
            excStr?.let { RuntimeException(it) }
        )
    }
    setLogBridge(logBridge)
}

actual object LogBridge {

    actual fun init() {
        initLogBridge()
    }

}