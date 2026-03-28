package com.cws.std.math

fun cross(v1: Vec2, v2: Vec2): Float {
    return v1.x * v2.y - v1.y * v2.x
}

fun cross(v1: Vec3, v2: Vec3): Vec3 {
    return Vec3(
        v1.y * v2.z - v1.z * v2.y,
        v1.z * v2.x - v1.x * v2.z,
        v1.x * v2.y - v1.y * v2.x
    )
}

fun cross(v1: Vec4, v2: Vec4): Vec4 {
    val x = v1.y * v2.z - v1.z * v2.y
    val y = v1.z * v2.x - v1.x * v2.z
    val z = v1.x * v2.y - v1.y * v2.x
    return Vec4(x, y, z, 0f)
}
