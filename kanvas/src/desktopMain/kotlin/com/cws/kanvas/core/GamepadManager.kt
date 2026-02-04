package com.cws.kanvas.core

import com.cws.kanvas.event.EventListener
import com.cws.kanvas.event.GamepadAxis
import com.cws.kanvas.event.GamepadCode
import net.java.games.input.Component
import net.java.games.input.Controller
import net.java.games.input.ControllerEnvironment
import kotlin.math.abs
import kotlin.math.sign

class GamepadManager(
    private val eventListeners: Set<EventListener>,
) {

    private val controllerEnvironment = ControllerEnvironment.getDefaultEnvironment()
    private val components = mutableMapOf<Component.Identifier, Float>()

    fun poll() {
        val controllers = controllerEnvironment.controllers
        val gamepads = controllers.filter { it.type == Controller.Type.GAMEPAD || it.type == Controller.Type.STICK }
        gamepads.forEach { gamepad ->
            gamepad.poll()
            gamepad.components.forEach { component ->
                val id = component.identifier
                val oldValue = components.getOrDefault(id, 0f)
                val newValue = component.pollData
                if (component.isAnalog) {
                    id.toGamepadAxis()?.let { gamepadAxis ->
                        val dt = newValue - oldValue
                        val magnitude = abs(dt)
                        val direction = sign(dt)
                        eventListeners.forEach { it.onGamepadAxisChanged(gamepadAxis, direction, magnitude) }
                    }
                } else {
                    val newPressed = newValue >= 0.5f
                    val oldPressed = oldValue >= 0.5f
                    id.toGamepadCode()?.let { gamepadCode ->
                        when {
                            newPressed && oldPressed -> {
                                eventListeners.forEach { it.onGamepadPressed(gamepadCode, true) }
                            }
                            newPressed && !oldPressed -> {
                                eventListeners.forEach { it.onGamepadPressed(gamepadCode, false) }
                            }
                            !newPressed && oldPressed -> {
                                eventListeners.forEach { it.onGamepadReleased(gamepadCode) }
                            }
                        }
                    }
                }
                components[id] = newValue
            }
        }
    }

    private fun Component.Identifier.Button.toGamepadCode(): GamepadCode? {
        return when (this) {
            Component.Identifier.Button.A -> GamepadCode.A
            Component.Identifier.Button.B -> GamepadCode.B
            Component.Identifier.Button.X -> GamepadCode.X
            Component.Identifier.Button.Y -> GamepadCode.Y
            Component.Identifier.Button.START -> GamepadCode.Start
            Component.Identifier.Button.BACK -> GamepadCode.Back
            Component.Identifier.Button.LEFT_THUMB -> GamepadCode.LeftThumb
            Component.Identifier.Button.RIGHT_THUMB -> GamepadCode.RightThumb
            Component.Identifier.Button.RIGHT -> GamepadCode.Right
            Component.Identifier.Button.LEFT -> GamepadCode.Left
            Component.Identifier.Button.FORWARD -> GamepadCode.Up
            else -> null
        }
    }

    private fun Component.Identifier.Axis.toGamepadAxis(): GamepadAxis? {
        return when (this) {
            Component.Identifier.Axis.X -> GamepadAxis.LeftX
            Component.Identifier.Axis.Y -> GamepadAxis.LeftY
            Component.Identifier.Axis.Z -> GamepadAxis.LeftTrigger
            Component.Identifier.Axis.RX -> GamepadAxis.RightX
            Component.Identifier.Axis.RY -> GamepadAxis.RightY
            Component.Identifier.Axis.RZ -> GamepadAxis.RightTrigger
            else -> null
        }
    }

}