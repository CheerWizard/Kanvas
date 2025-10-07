package com.cws.printer.sandbox

import android.app.Application
import com.cws.printer.Printer

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Printer.init()
    }

}