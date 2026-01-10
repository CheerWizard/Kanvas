package com.cws.kanvas.core

import android.app.Application
import com.cws.print.Print

open class GameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Print.install(applicationContext) {

        }
    }

}