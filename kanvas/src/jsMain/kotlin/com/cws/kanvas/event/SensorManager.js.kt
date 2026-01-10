package com.cws.kanvas.event

import com.cws.kanvas.core.Context

actual class SensorManager actual constructor(context: Context) {

    actual val sensor: SensorVector = SensorVector()

    actual fun init() = Unit
    actual fun release() = Unit

}