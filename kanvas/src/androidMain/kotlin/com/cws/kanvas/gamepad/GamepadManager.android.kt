package com.cws.kanvas.gamepad

import android.content.Context
import android.hardware.input.InputManager
import android.os.Handler
import android.os.Looper
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent
import com.cws.print.Print

class AndroidGamepadManager(context: Context) : GamepadManager() {

    companion object {
        private const val TAG = "AndroidGamepadManager"
    }

    private val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    private val handler = Handler(Looper.getMainLooper())
    private val inputListener = object : InputManager.InputDeviceListener {
        override fun onInputDeviceAdded(deviceId: Int) {
            Print.d(TAG, "onInputDeviceAdded: $deviceId")
            gamepadStates[deviceId.toString()] = GamepadState()
        }

        override fun onInputDeviceChanged(deviceId: Int) {
            Print.d(TAG, "onInputDeviceChanged: $deviceId")
        }

        override fun onInputDeviceRemoved(deviceId: Int) {
            Print.d(TAG, "onInputDeviceRemoved: $deviceId")
            gamepadStates.remove(deviceId.toString())
        }
    }

    fun onKeyEvent(event: KeyEvent, down: Boolean) {
       getGamepadState(event.deviceId.toString())?.let { state ->
            when (event.keyCode) {
                KeyEvent.KEYCODE_BUTTON_A -> state.A = down
                KeyEvent.KEYCODE_BUTTON_B -> state.B = down
                KeyEvent.KEYCODE_BUTTON_X -> state.X = down
                KeyEvent.KEYCODE_BUTTON_Y -> state.Y = down
                KeyEvent.KEYCODE_BUTTON_SELECT -> state.select = down
                KeyEvent.KEYCODE_BUTTON_START -> state.start = down
                KeyEvent.KEYCODE_BUTTON_MODE -> state.home = down
                KeyEvent.KEYCODE_BUTTON_L1 -> state.leftBump = down
                KeyEvent.KEYCODE_BUTTON_R1 -> state.rightBump = down
                KeyEvent.KEYCODE_BUTTON_L2 -> state.leftAxis.z = if (down) 1f else 0f
                KeyEvent.KEYCODE_BUTTON_R2 -> state.rightAxis.z = if (down) 1f else 0f
                KeyEvent.KEYCODE_BUTTON_THUMBL -> state.leftStick = down
                KeyEvent.KEYCODE_BUTTON_THUMBR -> state.rightStick = down
                KeyEvent.KEYCODE_DPAD_UP -> state.up = down
                KeyEvent.KEYCODE_DPAD_DOWN -> state.down = down
                KeyEvent.KEYCODE_DPAD_LEFT -> state.left = down
                KeyEvent.KEYCODE_DPAD_RIGHT -> state.right = down
            }
        }
    }

    fun onMotionEvent(event: MotionEvent) {
        getGamepadState(event.deviceId.toString())?.let { state ->
            state.leftAxis.x = event.getAxisValue(MotionEvent.AXIS_X)
            state.leftAxis.y = -event.getAxisValue(MotionEvent.AXIS_Y)
            state.rightAxis.x = event.getAxisValue(MotionEvent.AXIS_Z)
            state.rightAxis.y = -event.getAxisValue(MotionEvent.AXIS_RZ)
        }
    }

    override fun init() {
        super.init()
        inputManager.registerInputDeviceListener(inputListener, handler)
    }

    override fun release() {
        inputManager.unregisterInputDeviceListener(inputListener)
    }

    override fun getAvailableDevices(): List<String> {
        return InputDevice.getDeviceIds()
            .filter { id -> InputDevice.getDevice(id)?.isGamepad() ?: false }
            .map { it.toString() }
    }

    override fun update() {
        // no-op
    }

    private fun InputDevice.isGamepad(): Boolean {
        return  (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD) ||
                (sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK)
    }

}