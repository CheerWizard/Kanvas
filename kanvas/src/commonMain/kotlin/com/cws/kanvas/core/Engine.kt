package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioManager
import com.cws.std.async.JobsManager
import com.cws.kanvas.sensor.SensorManager
import com.cws.kanvas.rendering.backend.ContextInfo
import com.cws.kanvas.rendering.backend.RenderThread

class Engine(platform: Platform) {

    val jobsManager: JobsManager = JobsManager()
    val sensorManager: SensorManager = platform.sensorManager
    val audioManager: AudioManager = platform.audioManager

    private val renderThread = RenderThread(ContextInfo())

    fun init() {
        sensorManager.init()
        renderThread.start()
    }

    fun release() {
        sensorManager.release()
        renderThread.stop()
    }

    internal fun setSurface(surface: Any?) {
        renderThread.setSurface(surface)
    }

    internal fun resize(width: Int, height: Int) {
        renderThread.resize(width, height)
    }

}