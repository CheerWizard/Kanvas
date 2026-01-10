package com.cws.kanvas.event

import com.cws.kanvas.core.Context

expect class SensorManager(context: Context) {

    val sensor: SensorVector

    fun init()
    fun release()

}