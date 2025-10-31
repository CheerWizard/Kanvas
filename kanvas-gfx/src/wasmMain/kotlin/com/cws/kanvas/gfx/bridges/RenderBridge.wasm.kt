@file:OptIn(ExperimentalNativeApi::class)

package com.cws.kanvas.gfx.bridges

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.CName

@CName("RenderBridge_nativeInit")
external fun nativeInit(nativeWindow: COpaquePointer?, width: Int, height: Int)

@CName("RenderBridge_nativeFree")
external fun nativeFree()

@CName("RenderBridge_nativeResize")
external fun nativeResize(width: Int, height: Int)

@CName("RenderBridge_nativeBeginFrame")
external fun nativeBeginFrame()

@CName("RenderBridge_nativeEndFrame")
external fun nativeEndFrame()

actual object RenderBridge {

    actual fun init(config: RenderConfig) = nativeInit(
        config.nativeWindow,
        config.width,
        config.height,
    )

    actual fun free() = nativeFree()

    actual fun resize(width: Int, height: Int) = nativeResize(width, height)

    actual fun beginFrame() = nativeBeginFrame()

    actual fun endFrame() = nativeEndFrame()

}