package com.cws.kanvas.math

data class Transform(
    var position: Vec3 = Vec3(),
    var rotation: Vec3 = Vec3(),
    var scale: Vec3 = Vec3(),
) {
    fun toMat4(): Mat4 = ModelMatrix(position, rotation.x, rotation.y, rotation.z, scale)
}