package com.cws.kanvas.core

import android.content.Context
import com.cws.kanvas.audio.AndroidAudioManager
import com.cws.kanvas.audio.AudioManager
import com.cws.kanvas.event.AndroidSensorManager
import com.cws.kanvas.sensor.SensorManager

class AndroidPlatform(context: Context) : Platform {
    override val audioManager: AudioManager = AndroidAudioManager()
    override val sensorManager: SensorManager = AndroidSensorManager(context)
}