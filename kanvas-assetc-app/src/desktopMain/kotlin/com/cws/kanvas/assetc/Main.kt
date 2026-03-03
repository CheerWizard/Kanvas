package com.cws.kanvas.assetc

import androidx.compose.ui.window.application
import com.cws.kanvas.assetc.core.Application
import com.cws.print.Print

fun main() {
    Print.install(Unit) {
        application(exitProcessOnExit = true) {
            Application {
                exitApplication()
            }
        }
    }
}
