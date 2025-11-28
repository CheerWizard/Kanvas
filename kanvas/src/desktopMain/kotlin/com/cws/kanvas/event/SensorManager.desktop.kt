package com.cws.kanvas.event

actual class SensorManager {

    actual val sensor: SensorVector = SensorVector()

    actual fun init() = Unit
    actual fun release() = Unit

}