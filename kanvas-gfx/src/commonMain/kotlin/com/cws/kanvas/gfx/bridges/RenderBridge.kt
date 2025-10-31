package com.cws.kanvas.gfx.bridges

expect object RenderBridge {

    fun init(config: RenderConfig)
    fun free()

    fun resize(width: Int, height: Int)

    fun beginFrame()
    fun endFrame()

    fun updateViewport(x: Int, y: Int, width: Int, height: Int)

}
