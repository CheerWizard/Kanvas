package com.cws.kanvas.core

import android.app.Application
import com.cws.printer.Printer

open class GameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Printer.init()
    }

}