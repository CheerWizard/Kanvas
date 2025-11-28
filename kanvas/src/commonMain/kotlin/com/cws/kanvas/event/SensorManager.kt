package com.cws.kanvas.event

expect class SensorManager {

    val sensor: SensorVector

    fun init()
    fun release()

}