package com.cws.kanvas.core

import com.cws.kanvas.sensor.InputSensorManager

class Engine(
    inputSensorManager: InputSensorManager,
) {

    val jobsManager: JobsManager = JobsManager()

    val shaderManager: ShaderManager = ShaderManager(
        jobsManager = jobsManager,
    )

    val inputSensorManager: InputSensorManager = inputSensorManager

    fun init() {
        inputSensorManager.init()
    }

    fun release() {
        inputSensorManager.release()
    }

}