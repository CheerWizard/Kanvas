package com.cws.kanvas.gfx.bridges

actual object LogBridge {

    actual fun init() {}

    external fun nativeInit(className: String, methodName: String, methodSignature: String)

}