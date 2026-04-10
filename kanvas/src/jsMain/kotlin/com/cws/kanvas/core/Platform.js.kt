package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioManager
import com.cws.kanvas.audio.JSAudioManager
import com.cws.kanvas.event.JsSensorManager
import com.cws.kanvas.sensor.SensorManager

class JsPlatform : Platform {
    override val audioManager: AudioManager = JSAudioManager()
    override val sensorManager: SensorManager = JsSensorManager()
}