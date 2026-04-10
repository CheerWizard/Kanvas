package com.cws.kanvas.editor

import com.cws.kanvas.editor.logger.UiLogger
import com.cws.print.ConsoleLogger
import com.cws.print.JVMPrintContext
import com.cws.print.Print

fun main() {
    Print.install(
        context = JVMPrintContext(),
        loggers = setOf(
            ConsoleLogger(),
//            UiLogger(),
        ),
    ) {
    }
}