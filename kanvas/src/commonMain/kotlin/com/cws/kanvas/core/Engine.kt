package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioPlayer
import com.cws.kanvas.audio.AudioRecorder
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.core.async.JobsManager
import com.cws.kanvas.event.SensorManager

class Engine(
    val sensorManager: SensorManager,
) {

    val jobsManager: JobsManager = JobsManager()

    val audioPlayer: AudioPlayer = AudioPlayer()

    val audioRecorder: AudioRecorder = AudioRecorder()

    fun init(gameConfig: GameConfig) {
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