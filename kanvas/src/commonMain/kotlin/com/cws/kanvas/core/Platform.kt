package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioManager
import com.cws.kanvas.sensor.SensorManager

interface Platform {
    val sensorManager: SensorManager
    val audioManager: AudioManager
}