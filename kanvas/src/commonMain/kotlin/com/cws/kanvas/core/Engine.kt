package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioPlayer
import com.cws.kanvas.audio.AudioRecorder
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.core.concurrency.JobsManager
import com.cws.kanvas.sensor.InputSensorManager

class Engine(
    val inputSensorManager: InputSensorManager,
) {

    val jobsManager: JobsManager = JobsManager()

    val audioPlayer: AudioPlayer = AudioPlayer()

    val audioRecorder: AudioRecorder = AudioRecorder()

    fun init(gameConfig: GameConfig) {
        inputSensorManager.init()
        audioPlayer.init()
        audioRecorder.init()
//        RenderBridge.init(gameConfig.renderConfig)
    }

    fun release() {
        inputSensorManager.release()
//        RenderBridge.free()
    }

}