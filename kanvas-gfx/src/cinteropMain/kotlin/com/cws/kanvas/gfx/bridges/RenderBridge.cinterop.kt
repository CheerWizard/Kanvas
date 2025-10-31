package com.cws.kanvas.gfx.bridges

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.CName

@OptIn(ExperimentalNativeApi::class)
actual object RenderBridge {

    actual fun init(config: RenderConfig) = nativeInit(
        config.nativeWindow,
        config.width,
        config.height,
    )

    @CName("RenderBridge_nativeInit")
    private external fun nativeInit(nativeWindow: OpaquePointer?, width: Int, height: Int)

    actual fun free() = nativeFree()

    @CName("RenderBridge_nativeFree")
    private external fun nativeFree()

    actual fun resize(width: Int, height: Int) = nativeResize(width, height)

    @CName("RenderBridge_nativeResize")
    private external fun nativeResize(width: Int, height: Int)

    actual fun beginFrame() = nativeBeginFrame()

    @CName("RenderBridge_nativeBeginFrame")
    private external fun nativeBeginFrame()

    actual fun endFrame() = nativeEndFrame()

    @CName("RenderBridge_nativeEndFrame")
    private external fun nativeEndFrame()

    actual fun updateViewport(x: Int, y: Int, width: Int, height: Int) = nativeUpdateViewport(x, y, width, height)

    @CName("RenderBridge_nativeEndFrame")
    private external fun nativeUpdateViewport(x: Int, y: Int, width: Int, height: Int)

}