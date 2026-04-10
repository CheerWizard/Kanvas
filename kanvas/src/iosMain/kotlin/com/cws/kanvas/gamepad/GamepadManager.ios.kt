package com.cws.kanvas.gamepad

import com.cws.std.math.Vec3
import platform.Foundation.NSNotificationCenter
import platform.GameController.GCController
import platform.GameController.GCControllerDidConnectNotification
import platform.GameController.GCControllerDidDisconnectNotification

class IOSGamepadManager : GamepadManager() {

    override fun init() {
        super.init()
        NSNotificationCenter.defaultCenter.addObserverForName(
            name = GCControllerDidConnectNotification,
            `object` = null,
            queue = null,
        ) {
            val controller = it?.`object` as? GCController ?: return@addObserverForName
            val id = controller.vendorName ?: return@addObserverForName
            gamepadStates[id] = GamepadState()
        }
    }

    override fun release() {
        NSNotificationCenter.defaultCenter.addObserverForName(
            name = GCControllerDidDisconnectNotification,
            `object` = null,
            queue = null,
        ) {
            val controller = it?.`object` as? GCController ?: return@addObserverForName
            val id = controller.vendorName ?: return@addObserverForName
            gamepadStates.remove(id)
        }
    }

    override fun update() {

    }

    override fun getAvailableDevices(): List<String> {
        return (GCController.controllers() as List<GCController>).map { it.vendorName.orEmpty() }
    }

    private fun GCController.updateGamepadState(gamepadState: GamepadState): GamepadState {
        val controller = extendedGamepad ?: return gamepadState

        gamepadState.A = controller.buttonA.isPressed()
        gamepadState.B = controller.buttonB.isPressed()
        gamepadState.X = controller.buttonX.isPressed()
        gamepadState.Y = controller.buttonY.isPressed()

        gamepadState.leftBump = controller.leftShoulder.isPressed()
        gamepadState.rightBump = controller.rightShoulder.isPressed()

        gamepadState.leftAxis = Vec3(
            controller.leftThumbstick.xAxis.value,
            controller.leftThumbstick.yAxis.value,
            controller.leftTrigger.value,
        )
        gamepadState.rightAxis = Vec3(
            controller.rightThumbstick.xAxis.value,
            controller.rightThumbstick.yAxis.value,
            controller.rightTrigger.value,
        )

        gamepadState.up = controller.dpad.up.isPressed()
        gamepadState.down = controller.dpad.down.isPressed()
        gamepadState.left = controller.dpad.left.isPressed()
        gamepadState.right = controller.dpad.right.isPressed()

        return gamepadState
    }

}