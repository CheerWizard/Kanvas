package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioPlayer
import com.cws.kanvas.audio.AudioRecorder
import com.cws.std.async.JobsManager
import com.cws.kanvas.event.SensorManager
import com.cws.kanvas.rendering.backend.RenderContextInfo
import com.cws.kanvas.rendering.backend.RenderThread

class Engine(context: Context) {

    val jobsManager: JobsManager = JobsManager()

    val audioPlayer: AudioPlayer = AudioPlayer()

    val audioRecorder: AudioRecorder = AudioRecorder()

    val sensorManager: SensorManager = SensorManager(context)

    private val renderThread = RenderThread(RenderContextInfo())

    fun init() {
        sensorManager.init()
        audioPlayer.init()
        audioRecorder.init()
        renderThread.start()
    }

    fun release() {
        sensorManager.release()
        renderThread.stop()
    }

}