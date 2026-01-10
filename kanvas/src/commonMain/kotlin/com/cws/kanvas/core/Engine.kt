package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioPlayer
import com.cws.kanvas.audio.AudioRecorder
import com.cws.std.async.JobsManager
import com.cws.kanvas.event.SensorManager

class Engine(context: Context) {

    val jobsManager: JobsManager = JobsManager()

    val audioPlayer: AudioPlayer = AudioPlayer()

    val audioRecorder: AudioRecorder = AudioRecorder()

    val sensorManager: SensorManager = SensorManager(context)

    fun init() {
        sensorManager.init()
        audioPlayer.init()
        audioRecorder.init()
//        RenderBridge.init(gameConfig.renderConfig)
    }

    fun release() {
        sensorManager.release()
//        RenderBridge.free()
    }

}