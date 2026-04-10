package com.cws.kanvas.sensor

interface SensorManager {

    val sensorState: SensorState

    fun init()
    fun release()

}