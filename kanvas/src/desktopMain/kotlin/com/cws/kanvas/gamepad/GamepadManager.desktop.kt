package com.cws.kanvas.gamepad

import com.cws.print.Print
import net.java.games.input.Component
import net.java.games.input.Controller
import net.java.games.input.ControllerEnvironment
import net.java.games.input.ControllerEvent
import net.java.games.input.ControllerListener

class DesktopGamepadManager() : GamepadManager() {

    companion object {
        private const val TAG = "DesktopGamepadManager"
    }

    private val controllerEnvironment = ControllerEnvironment.getDefaultEnvironment()
    private val components = mutableMapOf<Component.Identifier, Float>()
    private val controllers = mutableMapOf<String, Controller>()
    private val controllerListener = object : ControllerListener {
        override fun controllerAdded(event: ControllerEvent?) {
            val deviceId = event?.controller?.name ?: return
            Print.d(TAG, "controllerAdded: $deviceId")
            gamepadStates[deviceId] = GamepadState()
            controllers[deviceId] = event.controller
        }

        override fun controllerRemoved(event: ControllerEvent?) {
            val deviceId = event?.controller?.name ?: return
            Print.d(TAG, "controllerRemoved: $deviceId")
            gamepadStates.remove(deviceId)
            controllers.remove(deviceId)
        }
    }

    override fun init() {
        super.init()
        controllerEnvironment.addControllerListener(controllerListener)
    }

    override fun release() {
        controllerEnvironment.removeControllerListener(controllerListener)
    }

    override fun getAvailableDevices(): List<String> {
        return controllerEnvironment.controllers
            .filter { it.isGamepad() }
            .map { it.name }
    }

    override fun update() {
        gamepadStates.forEach { (id, state) ->
            controllers[id]?.let { controller ->
                controller.poll()
                controller.components.forEach { component ->
                    val id = component.identifier
                    val newValue = component.pollData
                    if (component.isAnalog) {
                        id?.let { state.setGamepadAxis(it, newValue) }
                    } else {
                        val newPressed = newValue >= 0.5f
                        id?.let { state.setGamepadButton(it, newPressed) }
                    }
                    components[id] = newValue
                }
            }
        }
    }

    private fun GamepadState.setGamepadButton(button: Component.Identifier, pressed: Boolean) {
        return when (button) {
            Component.Identifier.Button.A -> A = pressed
            Component.Identifier.Button.B -> B = pressed
            Component.Identifier.Button.X -> X = pressed
            Component.Identifier.Button.Y -> Y = pressed
            Component.Identifier.Button.START -> start = pressed
            Component.Identifier.Button.BACK -> back = pressed
            Component.Identifier.Button.LEFT_THUMB -> leftThumb = pressed
            Component.Identifier.Button.RIGHT_THUMB -> rightThumb = pressed
            Component.Identifier.Button.RIGHT -> right = pressed
            Component.Identifier.Button.LEFT -> left = pressed
            Component.Identifier.Button.FORWARD -> up = pressed
            else -> {}
        }
    }

    private fun GamepadState.setGamepadAxis(axis: Component.Identifier, value: Float) {
        return when (axis) {
            Component.Identifier.Axis.X -> leftAxis.x = value
            Component.Identifier.Axis.Y -> leftAxis.y = value
            Component.Identifier.Axis.Z -> leftAxis.z = value
            Component.Identifier.Axis.RX -> rightAxis.x = value
            Component.Identifier.Axis.RY -> rightAxis.y = value
            Component.Identifier.Axis.RZ -> rightAxis.z = value
            else -> {}
        }
    }

    private fun Controller.isGamepad(): Boolean {
        return type == Controller.Type.GAMEPAD || type == Controller.Type.STICK
    }

}