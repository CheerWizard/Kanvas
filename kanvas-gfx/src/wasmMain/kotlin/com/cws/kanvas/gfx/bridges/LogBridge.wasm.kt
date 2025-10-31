package com.cws.kanvas.gfx.bridges

import com.cws.printer.Printer

@JsName("LogBridge_log")
fun LogBridge_log(level: Int, tag: String, message: String, exceptionMessage: String?) {
    Printer.log(level, tag, message, exceptionMessage)
}

@JsName("initLogBridge")
fun initLogBridge() {
    js("globalThis.LogBridge_log = LogBridge_log;")
}

actual object LogBridge {

    actual fun init() {
        initLogBridge()
    }

}