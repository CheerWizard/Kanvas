package com.cws.kanvas.event

import com.cws.kanvas.sensor.SensorManager
import com.cws.print.Print
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSOperationQueue
import com.cws.kanvas.sensor.SensorState
import com.cws.std.math.normalize

@OptIn(ExperimentalForeignApi::class)
class IOSSensorManager : SensorManager {

    companion object {
        private const val TAG = "IOSSensorManager"
    }

    override val sensorState: SensorState = SensorState()

    private val motionManager = CMMotionManager()

    override fun init() {
        if (!motionManager.isAccelerometerAvailable()) {
            Print.e(TAG, "Accelerometer is not available on IOS platform")
            return
        }

        motionManager.accelerometerUpdateInterval = 0.01

        motionManager.startAccelerometerUpdatesToQueue(NSOperationQueue.mainQueue) { data, error ->
            data?.let {
                it.acceleration.useContents {
                    sensorState.acceleration.x = x.toFloat()
                    sensorState.acceleration.y = y.toFloat()
                    sensorState.acceleration.z = z.toFloat()
                    sensorState.acceleration = sensorState.acceleration.normalize()
                }
            }
        }
    }

    override fun release() {
        motionManager.stopAccelerometerUpdates()
    }

}