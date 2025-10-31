package com.cws.kanvas.core

import kotlinx.cinterop.ExperimentalForeignApi
import platform.QuartzCore.CAEAGLLayer
import platform.UIKit.UIEvent
import platform.UIKit.UITouch
import platform.UIKit.UIView

class GameView(
    private val gameLoop: GameLoop
) : UIView() {

    init {
        val layer = layer as CAEAGLLayer
        layer.opaque = false
        userInteractionEnabled = true
        multipleTouchEnabled = true
        gameLoop.setSurface(layer)
        gameLoop.startLoop()
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun touchesBegan(touches: Set<*>, withEvent: UIEvent?) {
        val touch = touches.firstOrNull() ?: return
        touch as UITouch
        val point = touch.locationInView(this)
        gameLoop.onMotionEvent(point)
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun touchesMoved(touches: Set<*>, withEvent: UIEvent?) {
        val touch = touches.firstOrNull() ?: return
        touch as UITouch
        val point = touch.locationInView(this)
        gameLoop.onMotionEvent(point)
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun touchesEnded(touches: Set<*>, withEvent: UIEvent?) {
        val touch = touches.firstOrNull() ?: return
        touch as UITouch
        val point = touch.locationInView(this)
        gameLoop.onMotionEvent(point)
    }

}