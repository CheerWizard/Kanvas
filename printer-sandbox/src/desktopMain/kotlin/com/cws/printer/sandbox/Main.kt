package com.cws.printer.sandbox

import com.cws.printer.FileLogger
import com.cws.printer.Printer
import com.cws.printer.SignozLogger
import kotlinx.coroutines.runBlocking

fun main() {
    Printer.init(loggers = setOf(
        FileLogger("logs/PrinterSandbox.log"),
        SignozLogger(host = "") // TODO need to find a host for SigNoz Cloud
    ))
    runBlocking {
        PrinterTests.run().join()
    }
    Printer.close()
}