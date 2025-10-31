package com.cws.kanvas.core

import com.cws.kanvas.loaders.ShaderLoader
import com.cws.kanvas.sensor.InputSensorManager

class Engine(
    shaderLoader: ShaderLoader,
    inputSensorManager: InputSensorManager,
) {

    val jobsManager: JobsManager = JobsManager()

    val shaderManager: ShaderManager = ShaderManager(
        jobsManager = jobsManager,
        shaderLoader = shaderLoader,
    )

    val inputSensorManager: InputSensorManager = inputSensorManager

    fun init() {
        inputSensorManager.init()
    }

    fun release() {
        inputSensorManager.release()
    }

}