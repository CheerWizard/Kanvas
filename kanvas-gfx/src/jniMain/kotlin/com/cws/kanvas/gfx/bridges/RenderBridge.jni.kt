package com.cws.kanvas.gfx.bridges

import java.nio.ByteBuffer

actual object RenderBridge {

    actual fun init(config: RenderConfig) = nativeInit(config)

    private external fun nativeInit(config: RenderConfig)

    actual fun free() = nativeFree()

    private external fun nativeFree()

    actual fun resize(width: Int, height: Int) = nativeResize(width, height)

    private external fun nativeResize(width: Int, height: Int)

    actual fun beginFrame() = nativeBeginFrame()

    private external fun nativeBeginFrame()

    actual fun endFrame() = nativeEndFrame()

    private external fun nativeEndFrame()

    actual fun updateViewport(x: Int, y: Int, width: Int, height: Int) =
        nativeUpdateViewport(x, y, width, height)

    private external fun nativeUpdateViewport(x: Int, y: Int, width: Int, height: Int)

    external fun nativeRegisterPixels(pixels: ByteBuffer)

    external fun nativeUnregisterPixels()

}