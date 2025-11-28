package com.cws.kanvas.event

import com.cws.kanvas.math.Vec3

data class SensorVector(
    var acceleration: Vec3 = Vec3(),
    val direction: Vec3 = Vec3()
)