package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioManager
import com.cws.kanvas.audio.DesktopAudioManager
import com.cws.kanvas.event.DesktopSensorManager
import com.cws.kanvas.sensor.SensorManager

class DesktopPlatform : Platform {
    override val audioManager: AudioManager = DesktopAudioManager()
    override val sensorManager: SensorManager = DesktopSensorManager()
}