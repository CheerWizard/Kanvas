package com.cws.kanvas.math

fun dot(v1: Vec2, v2: Vec2): Float {
    return v1.x * v2.x + v1.y * v2.y
}

fun dot(v1: Vec3, v2: Vec3): Float {
    return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
}

fun dot(v1: Vec4, v2: Vec4): Float {
    return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z + v1.w * v2.w
}

fun dot(q1: Quaternion, q2: Quaternion): Float {
    return q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w
}