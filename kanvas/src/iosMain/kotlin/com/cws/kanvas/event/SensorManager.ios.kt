package com.cws.kanvas.event

import com.cws.kanvas.core.Context
import com.cws.print.Print
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSOperationQueue
import com.cws.kanvas.math.*

@OptIn(ExperimentalForeignApi::class)
actual class SensorManager actual constructor(context: Context) {

    companion object {
        private const val TAG = "SensorManager"
    }

    actual val sensor: SensorVector = SensorVector()

    private val motionManager = CMMotionManager()

    actual fun init() {
        if (!motionManager.isAccelerometerAvailable()) {
            Print.e(TAG, "Accelerometer is not available on Native platform")
            return
        }

        motionManager.accelerometerUpdateInterval = 0.01

        motionManager.startAccelerometerUpdatesToQueue(NSOperationQueue.mainQueue) { data, error ->
            data?.let {
                it.acceleration.useContents {
                    sensor.acceleration.x = x.toFloat()
                    sensor.acceleration.y = y.toFloat()
                    sensor.acceleration.z = z.toFloat()
                    sensor.acceleration = sensor.acceleration.normalize()
                }
            }
        }
    }

    actual fun release() {
        motionManager.stopAccelerometerUpdates()
    }

}