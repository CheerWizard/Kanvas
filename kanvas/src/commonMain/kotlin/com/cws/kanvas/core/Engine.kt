package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioPlayer
import com.cws.kanvas.audio.AudioRecorder
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.sensor.InputSensorManager

class Engine(
    val inputSensorManager: InputSensorManager,
    val audioPlayer: AudioPlayer,
    val audioRecorder: AudioRecorder,
) {

    val jobsManager: JobsManager = JobsManager()

    val shaderManager: ShaderManager = ShaderManager(
        jobsManager = jobsManager,
    )

    fun init(gameConfig: GameConfig) {
        inputSensorManager.init()
//        RenderBridge.init(gameConfig.renderConfig)
    }

    fun release() {
        inputSensorManager.release()
//        RenderBridge.free()
    }

}