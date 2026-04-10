package com.cws.kanvas.event

import com.cws.kanvas.sensor.SensorManager
import com.cws.kanvas.sensor.SensorState

class JsSensorManager : SensorManager {
    override val sensorState: SensorState = SensorState()
    override fun init() = Unit
    override fun release() = Unit
}
