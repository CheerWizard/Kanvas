package com.cws.kanvas.gamepad

import com.cws.print.Print
import kotlinx.browser.window
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

external interface Gamepad {
    val id: String
    val index: Int
    val connected: Boolean
    val buttons: Array<GamepadButton>
    val axes: Array<Double>
    val timestamp: Double
}

fun Gamepad.updateGamepadState(gamepadState: GamepadState = GamepadState()) = gamepadState.apply {
    this.id = id

    this.leftAxis.x = axes[0].toFloat()
    this.leftAxis.y = axes[1].toFloat()
    this.leftAxis.z = axes[2].toFloat()

    this.rightAxis.x = axes[3].toFloat()
    this.rightAxis.y = axes[4].toFloat()
    this.rightAxis.z = axes[5].toFloat()
}

external interface GamepadButton {
    val pressed: Boolean
    val value: Double
}

external interface Navigator {
    fun getGamepads(): Array<Gamepad?>
}

external val navigator: Navigator

external class GamepadEvent : Event {
    val gamepad: Gamepad
}

class JsGamepadManager : GamepadManager() {

    companion object {
        private const val TAG = "JsGamepadManager"
    }

    private val connectedCallback = object : EventListener {
        override fun handleEvent(event: Event) {
            val gamepad = (event as GamepadEvent).gamepad
            Print.d(TAG, "Gamepad connnected ${gamepad.id}")
            gamepadStates[gamepad.id] = gamepad.updateGamepadState(gamepadStates[gamepad.id] ?: GamepadState())
        }
    }

    private val disconnectedCallback = object : EventListener {
        override fun handleEvent(event: Event) {
            val gamepad = (event as GamepadEvent).gamepad
            Print.d(TAG, "Gamepad connnected ${gamepad.id}")
            gamepadStates.remove(gamepad.id)
        }
    }

    override fun init() {
        window.addEventListener("gamepadconnected", connectedCallback)
        window.addEventListener("gamepaddisconnected", disconnectedCallback)
        super.init()
    }

    override fun release() {
        window.removeEventListener("gamepadconnected", connectedCallback)
        window.removeEventListener("gamepaddisconnected", disconnectedCallback)
    }

    override fun getAvailableDevices(): List<String> {
        return getConnectedGamepads().map { it.id }
    }

    private fun getConnectedGamepads(): List<Gamepad> {
        return navigator.getGamepads()
            .filterNotNull()
            .filter { it.connected }
    }

    override fun update() {
        getConnectedGamepads().forEach { gamepad ->
            gamepadStates[gamepad.id] = gamepad.updateGamepadState(gamepadStates[gamepad.id] ?: GamepadState())
        }
    }

}