package com.cws.kanvas.gamepad

abstract class GamepadManager {

    protected val gamepadStates = mutableMapOf<String, GamepadState>()

    open fun init() {
        getAvailableDevices().forEach { id -> gamepadStates[id] = GamepadState() }
    }

    fun getGamepadState(deviceId: String) = gamepadStates[deviceId]

    abstract fun release()
    abstract fun update()
    abstract fun getAvailableDevices(): List<String>
}
