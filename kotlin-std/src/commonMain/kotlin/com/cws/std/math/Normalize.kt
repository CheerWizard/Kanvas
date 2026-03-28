package com.cws.std.math

fun normalize(v: Vec2, out: Vec2): Vec2 {
    val l = v.length
    if (l == 0f) return out
    out.x = v.x / l
    out.y = v.y / l
    return out
}

fun normalize(v: Vec3, out: Vec3): Vec3 {
    val l = v.length
    if (l == 0f) return out
    out.x = v.x / l
    out.y = v.y / l
    out.z = v.z / l
    return out
}

fun normalize(v: Vec4, out: Vec4): Vec4 {
    val l = v.length
    if (l == 0f) return out
    out.x = v.x / l
    out.y = v.y / l
    out.z = v.z / l
    out.w = v.w / l
    return out
}

fun normalize(v: Quaternion, out: Quaternion): Quaternion {
    val l = v.length
    if (l == 0f) return out
    out.x = v.x / l
    out.y = v.y / l
    out.z = v.z / l
    return out
}
