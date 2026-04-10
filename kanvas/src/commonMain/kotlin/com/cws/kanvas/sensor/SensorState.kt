package com.cws.kanvas.sensor

import com.cws.std.math.Vec3

data class SensorState(
    var acceleration: Vec3 = Vec3(),
    val direction: Vec3 = Vec3()
)