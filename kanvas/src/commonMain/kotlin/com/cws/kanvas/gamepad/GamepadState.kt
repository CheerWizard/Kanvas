package com.cws.kanvas.gamepad

import com.cws.std.math.Vec3

data class GamepadState(
    var id: String = "",

    var A: Boolean = false,
    var B: Boolean = false,
    var X: Boolean = false,
    var Y: Boolean = false,

    var back: Boolean = false,
    var select: Boolean = false,
    var start: Boolean = false,
    var home: Boolean = false,

    var up: Boolean = false,
    var down: Boolean = false,
    var left: Boolean = false,
    var right: Boolean = false,

    var leftThumb: Boolean = false,
    var rightThumb: Boolean = false,

    var leftBump: Boolean = false,
    var rightBump: Boolean = false,

    var leftStick: Boolean = false,
    var rightStick: Boolean = false,

    var leftAxis: Vec3 = Vec3(),
    var rightAxis: Vec3 = Vec3(),
)