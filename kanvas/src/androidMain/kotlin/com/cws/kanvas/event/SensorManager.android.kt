package com.cws.kanvas.event

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.cws.kanvas.sensor.SensorManager
import com.cws.kanvas.sensor.SensorState
import com.cws.std.math.Vec3
import kotlin.math.abs

class AndroidSensorManager(context: Context) : SensorManager, SensorEventListener {

    override val sensorState: SensorState = SensorState()

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as android.hardware.SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    override fun init() {
        sensorManager.registerListener(this, accelerometer, android.hardware.SensorManager.SENSOR_DELAY_GAME)
    }

    override fun release() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        val x = event.values[0] * 10f
        val y = event.values[1] * 10f
        val z = event.values[2] * 10f

        sensorState.acceleration = Vec3(abs(x), abs(y), abs(z))

        sensorState.direction.x = if (x > 3f) -1f else 1f
        sensorState.direction.y = if (y > 3f) 1f else -1f
        sensorState.direction.z = if (z > 3f) -1f else 1f
    }

}