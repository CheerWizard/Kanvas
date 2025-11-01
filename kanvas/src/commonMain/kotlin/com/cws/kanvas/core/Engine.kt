package com.cws.kanvas.core

import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.gfx.bridges.RenderBridge
import com.cws.kanvas.sensor.InputSensorManager

class Engine(
    inputSensorManager: InputSensorManager,
) {

    val jobsManager: JobsManager = JobsManager()

    val shaderManager: ShaderManager = ShaderManager(
        jobsManager = jobsManager,
    )

    val inputSensorManager: InputSensorManager = inputSensorManager

    fun init(gameConfig: GameConfig) {
        inputSensorManager.init()
        RenderBridge.init(gameConfig.renderConfig)
    }

    fun release() {
        inputSensorManager.release()
        RenderBridge.free()
    }

}