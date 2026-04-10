package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioManager
import com.cws.kanvas.audio.IOSAudioManager
import com.cws.kanvas.event.IOSSensorManager
import com.cws.kanvas.sensor.SensorManager

class IOSPlatform : Platform {
    override val audioManager: AudioManager = IOSAudioManager()
    override val sensorManager: SensorManager = IOSSensorManager()
}