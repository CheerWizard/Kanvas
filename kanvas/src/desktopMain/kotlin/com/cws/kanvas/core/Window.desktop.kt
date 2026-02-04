package com.cws.kanvas.core

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.utf16CodePoint
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.window.WindowState
import com.cws.kanvas.event.GamepadAxis
import com.cws.kanvas.event.GamepadCode
import com.cws.kanvas.event.KeyCode
import com.cws.kanvas.event.MouseCode
import com.cws.kanvas.gfx.texture.Pixels
import net.java.games.input.Component
import java.nio.ByteBuffer
import kotlin.math.roundToInt

class WindowPixels(width: Int, height: Int) {

    var pixels = Pixels(ByteBuffer.allocateDirect(width * height * 4))
    var array = ByteArray(width * height * 4)

    fun sync() {
        pixels.buffer.rewind()
        for (i in 0..<array.size) {
            array[i] = pixels.buffer[i]
        }
    }

}

actual class Window : BaseWindow {

    lateinit var onBitmapChanged: (ImageBitmap?) -> Unit

    @Volatile
    var windowState: WindowState? = null

    private var closed = false
    private val bitmap = ImageBitmap(config.width, config.height, ImageBitmapConfig.Argb8888)
    private var windowPixels: WindowPixels? = null
    private val gamepadManager = GamepadManager(eventListeners)

    actual constructor(config: WindowConfig) : super(config) {
        createPixels(config.width, config.height)
        windowPixels?.let { windowPixels ->
            RenderBridge.nativeSetOffscreenCallback(this, windowPixels.pixels.buffer)
        }
    }

    private fun createPixels(width: Int, height: Int) {
        windowPixels = WindowPixels(width, height)
    }

    actual fun isClosed(): Boolean = closed

    override fun onPixelsReady() {
        checkComposeState()
        windowPixels?.let { windowPixels ->
            windowPixels.sync()
            bitmap.asSkiaBitmap().installPixels(windowPixels.array)
            if (::onBitmapChanged.isInitialized) {
                onBitmapChanged(bitmap)
            }
        }
    }

    private fun checkComposeState() {
        windowState?.let { state ->
            val newX = state.position.x.value.roundToInt()
            val newY = state.position.y.value.roundToInt()
            val newWidth = state.size.width.value.roundToInt()
            val newHeight = state.size.height.value.roundToInt()
            val posChanged = config.x != newX || config.y != newY
            val sizeChanged = config.width != newWidth || config.height != newHeight

            if (posChanged) {
                onWindowMove(newX, newY)
            }

            if (sizeChanged) {
                onWindowResized(newWidth, newHeight)
            }

            if (posChanged || sizeChanged) {
                RenderBridge.updateViewport(newX, newY, newWidth, newHeight)
            }

            config.x = newX
            config.y = newY
            config.width = newWidth
            config.height = newHeight
        }
    }

    fun onWindowClose() {
        closed = true
        eventListeners.forEach { it.onWindowClosed() }
    }

    fun onWindowMove(x: Int, y: Int) {
        eventListeners.forEach { it.onWindowMoved(x, y) }
    }

    fun onWindowResized(width: Int, height: Int) {
        createPixels(width, height)
        eventListeners.forEach { it.onWindowResized(width, height) }
    }

    fun onKeyEvent(keyEvent: KeyEvent): Boolean {
        when (keyEvent.type) {
            KeyEventType.KeyDown -> {
                val code = keyEvent.utf16CodePoint
                if (code != 0) {
                    eventListeners.forEach { it.onKeyTyped(code.toChar()) }
                } else {
                    eventListeners.forEach { it.onKeyPressed(keyEvent.key.toKeyCode(), false) }
                }
            }
            KeyEventType.KeyUp -> {
                eventListeners.forEach { it.onKeyReleased(keyEvent.key.toKeyCode()) }
            }
        }
        return true
    }

    fun onMousePress(button: PointerButton) {
        eventListeners.forEach { it.onMousePressed(button.toMouseCode(), false) }
    }

    fun onMouseRelease(button: PointerButton) {
        eventListeners.forEach { it.onMouseReleased(button.toMouseCode()) }
    }

    fun onMouseMove(x: Float, y: Float) {
        eventListeners.forEach { it.onMouseMove(x.toDouble(), y.toDouble()) }
    }

    fun onMouseScroll(x: Float, y: Float) {
        eventListeners.forEach { it.onMouseScroll(x.toDouble(), y.toDouble()) }
    }

    override fun pollEvents() {
        super.pollEvents()
        gamepadManager.poll()
    }

    private fun Key.toKeyCode(): KeyCode {
        return when (this) {
            Key.Spacebar -> KeyCode.Space
            Key.Escape -> KeyCode.Esc
            Key.Enter -> KeyCode.Enter
            Key.Tab -> KeyCode.Tab
            Key.Backspace -> KeyCode.Backspace
            Key.Insert -> KeyCode.Insert
            Key.Delete -> KeyCode.Delete
            Key.DirectionRight -> KeyCode.Right
            Key.DirectionLeft -> KeyCode.Left
            Key.DirectionDown -> KeyCode.Down
            Key.DirectionUp -> KeyCode.Up
            Key.PageUp -> KeyCode.PageUp
            Key.PageDown -> KeyCode.PageDown
            Key.Home -> KeyCode.Home
            Key.EndCall -> KeyCode.End
            Key.CapsLock -> KeyCode.CapsLock
            Key.ScrollLock -> KeyCode.ScrollLock
            Key.NumLock -> KeyCode.NumLock
            Key.PrintScreen -> KeyCode.PrintScreen
            Key.MediaPause -> KeyCode.Pause
            Key.F1 -> KeyCode.F1
            Key.F2 -> KeyCode.F2
            Key.F3 -> KeyCode.F3
            Key.F4 -> KeyCode.F4
            Key.F5 -> KeyCode.F5
            Key.F6 -> KeyCode.F6
            Key.F7 -> KeyCode.F7
            Key.F8 -> KeyCode.F8
            Key.F9 -> KeyCode.F9
            Key.F10 -> KeyCode.F10
            Key.F11 -> KeyCode.F11
            Key.F12 -> KeyCode.F12
            Key.NumPad0 -> KeyCode.KP0
            Key.NumPad1 -> KeyCode.KP1
            Key.NumPad2 -> KeyCode.KP2
            Key.NumPad3 -> KeyCode.KP3
            Key.NumPad4 -> KeyCode.KP4
            Key.NumPad5 -> KeyCode.KP5
            Key.NumPad6 -> KeyCode.KP6
            Key.NumPad7 -> KeyCode.KP7
            Key.NumPad8 -> KeyCode.KP8
            Key.NumPad9 -> KeyCode.KP9
            Key.NumPadDivide -> KeyCode.KPDivide
            Key.NumPadMultiply -> KeyCode.KPMultiply
            Key.NumPadSubtract -> KeyCode.KPSubtract
            Key.NumPadAdd -> KeyCode.KPAdd
            Key.NumPadEnter -> KeyCode.KPEnter
            Key.NumPadEquals -> KeyCode.KPEqual
            Key.ShiftLeft -> KeyCode.LeftShift
            Key.CtrlLeft -> KeyCode.LeftControl
            Key.AltLeft -> KeyCode.LeftAlt
            Key.ShiftRight -> KeyCode.RightShift
            Key.CtrlRight -> KeyCode.RightControl
            Key.AltRight -> KeyCode.RightAlt
            Key.Menu -> KeyCode.Menu
            else -> KeyCode.Null
        }
    }

    private fun PointerButton.toMouseCode(): MouseCode {
        return when (this) {
            PointerButton.Tertiary -> MouseCode.Middle
            PointerButton.Primary -> MouseCode.Left
            PointerButton.Secondary -> MouseCode.Right
            else -> MouseCode.None
        }
    }

}