package com.cws.kanvas.math

import com.cws.std.memory.INativeData
import com.cws.std.memory.MemoryLayout
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.Stack
import com.cws.std.memory.next
import com.cws.std.memory.push
import com.cws.std.memory.stackPush
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

interface Mat4 : INativeData {

    companion object {
        val SIZE_BYTES = Vec4.SIZE_BYTES * 4
        val STD140_SIZE_BYTES = Vec4.STD140_SIZE_BYTES * 4
        val STD430_SIZE_BYTES = Vec4.STD430_SIZE_BYTES * 4
    }

    override fun sizeBytes(layout: MemoryLayout): Int {
        return when (layout) {
            MemoryLayout.KOTLIN -> SIZE_BYTES
            MemoryLayout.STD140 -> STD140_SIZE_BYTES
            MemoryLayout.STD430 -> STD430_SIZE_BYTES
        }
    }

    override fun pack(buffer: NativeBuffer) {
        buffer.push(v1)
        buffer.push(v2)
        buffer.push(v3)
        buffer.push(v4)
    }

    override fun unpack(buffer: NativeBuffer) = Mat4(
        buffer.next(v1),
        buffer.next(v2),
        buffer.next(v3),
        buffer.next(v4),
    )

}

expect fun Mat4(): Mat4
expect fun Mat4(
    m00: Float,
    m01: Float,
    m02: Float,
    m03: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m13: Float,
    m20: Float,
    m21: Float,
    m22: Float,
    m23: Float,
    m30: Float,
    m31: Float,
    m32: Float,
    m33: Float,
): Mat4
expect fun Mat4(
    v1: Vec4,
    v2: Vec4,
    v3: Vec4,
    v4: Vec4
): Mat4

expect fun Stack.Mat4(): Mat4
expect fun Stack.Mat4(
    m00: Float,
    m01: Float,
    m02: Float,
    m03: Float,
    m10: Float,
    m11: Float,
    m12: Float,
    m13: Float,
    m20: Float,
    m21: Float,
    m22: Float,
    m23: Float,
    m30: Float,
    m31: Float,
    m32: Float,
    m33: Float,
): Mat4
expect fun Stack.Mat4(
    v1: Vec4,
    v2: Vec4,
    v3: Vec4,
    v4: Vec4
): Mat4

expect fun Mat4.reset()

expect fun Mat4.clone(stackScope: Boolean): Mat4

expect var Mat4.v1: Vec4
expect var Mat4.v2: Vec4
expect var Mat4.v3: Vec4
expect var Mat4.v4: Vec4

expect operator fun Mat4.component1(): Vec4
expect operator fun Mat4.component2(): Vec4
expect operator fun Mat4.component3(): Vec4
expect operator fun Mat4.component4(): Vec4

expect operator fun Mat4.get(i: Int): Vec4

fun Mat4(
    q: Quaternion
): Mat4 {
    val m = Mat4()

    val xx = q.x * q.x
    val xy = q.x * q.y
    val xz = q.x * q.z
    val yy = q.y * q.y
    val zz = q.z * q.z
    val yz = q.y * q.z
    val wx = q.w * q.x
    val wy = q.w * q.y
    val wz = q.w * q.z

    m[0][0] = 1.0f - 2.0f * (yy + zz);
    m[1][0] = 2.0f * (xy - wz);
    m[2][0] = 2.0f * (xz + wy);
    m[3][0] = 0f

    m[0][1] = 2.0f * (xy + wz);
    m[1][1] = 1.0f - 2.0f * (xx + zz);
    m[2][1] = 2.0f * (yz - wx);
    m[3][1] = 0f

    m[0][2] = 2.0f * (xz - wy);
    m[1][2] = 2.0f * (yz + wx);
    m[2][2] = 1.0f - 2.0f * (xx + yy);
    m[3][2] = 0f

    m[0][3] = 0f
    m[1][3] = 0f
    m[2][3] = 0f
    m[3][3] = 1f

    return m
}

fun Mat4.model(translation: Vec3, rx: Float, ry: Float, rz: Float, scalar: Vec3): Mat4 = stackPush {
    Mat4()
        .identity()
        .translate(translation)
        .rotate(rx, ry, rz, Vec3(1f, 1f, 1f))
        .scale(scalar)
}

fun Mat4.model(translation: Vec3, quaternion: Quaternion, scalar: Vec3): Mat4 = stackPush {
    Mat4()
        .identity()
        .translate(translation)
        .rotate(quaternion)
        .scale(scalar)
}

fun Mat4.rigid(translation: Vec3, rx: Float, ry: Float, rz: Float): Mat4 = stackPush {
    Mat4()
        .identity()
        .translate(translation)
        .rotate(rx, ry, rz, Vec3(1f, 1f, 1f))
}

fun Mat4.rigid(translation: Vec3, quaternion: Quaternion): Mat4 = stackPush {
    Mat4()
        .identity()
        .translate(translation)
        .rotate(quaternion)
}

fun Mat4.view(position: Vec3, front: Vec3, up: Vec3): Mat4 = stackPush {
    val right = cross(front, up).normalize()
    val f = -front
    val c = cross(right, front)
    val m = Mat4(
        right.x, right.y, right.z, 0f,
        c.x, c.y, c.z, 0f,
        f.x, f.y, f.z, 0f,
        position.x, position.y, position.z, 0f
    )
    return m.transpose().inverse()
}

fun Mat4.ortho(left: Float, right: Float, bottom: Float, top: Float, zNear: Float, zFar: Float): Mat4 = stackPush {
    Mat4(
        2.0f / (right - left), 0.0f, 0.0f, 0.0f,
        0.0f, 2.0f / (bottom - top), 0.0f, 0.0f,
        0.0f, 0.0f, 1.0f / (zNear - zFar), 0.0f,
        -(right + left) / (right - left), -(bottom + top) / (bottom - top), zNear / (zNear - zFar), 1.0f
    )
}

fun Mat4.perspective(aspectRatio: Float, fov: Degree, zNear: Float, zFar: Float): Mat4 = stackPush {
    val f = 1.0f / tan((fov * 0.5f).radians.value)
    Mat4(
        f / aspectRatio, 0.0f, 0.0f, 0.0f,
        0.0f, -f, 0.0f, 0.0f,
        0.0f, 0.0f, zFar / (zNear - zFar), -1.0f,
        0.0f, 0.0f, zNear * zFar / (zNear - zFar), 0.0f
    )
}

fun Mat4.normal(): Mat4 = stackPush {
    return Mat4()
        .identity()
        .inverse()
        .transpose()
}

fun Mat4.identity(): Mat4 {
    reset()
    get(0)[0] = 1f
    get(1)[1] = 1f
    get(2)[2] = 1f
    get(3)[3] = 1f
    return this
}

fun Mat4.transpose(): Mat4 = transpose(this, this)

fun Mat4.inverse(): Mat4 = inverse(this, this)

fun Mat4.translate(translation: Vec3): Mat4 {
    get(0)[3] = translation.x
    get(1)[3] = translation.y
    get(2)[3] = translation.z
    return this
}

fun Mat4.scale(scalar: Vec3): Mat4 {
    get(0)[0] = scalar.x
    get(1)[1] = scalar.y
    get(2)[2] = scalar.z
    return this
}

fun Mat4.rotate(rx: Float, ry: Float, rz: Float, axis: Vec3): Mat4 {
    var m = this
    val mx = stackPush { Mat4() }
    val my = stackPush { Mat4() }
    val mz = stackPush { Mat4() }

    val sinx = sin(rx)
    val cosx = cos(rx)
    mx[1][1] = cosx
    mx[1][2] = -sinx
    mx[2][1] = sinx
    mx[2][2] = cosx
    mx[0][0] = axis.x

    val siny = sin(ry)
    val cosy = cos(ry)
    my[0][0] = cosy
    my[0][2] = siny
    my[2][0] = -siny
    my[2][2] = cosy
    my[1][1] = axis.y

    val sinz = sin(rz)
    val cosz = cos(rz)
    mz[0][0] = cosz
    mz[0][1] = -sinz
    mz[1][0] = sinz
    mz[1][1] = cosz
    mz[2][2] = axis.z

    m *= mz
    m *= my
    m *= mx

    return m
}

fun Mat4.rotate(quaternion: Quaternion): Mat4 = stackPush {
    return this@rotate * Mat4(quaternion)
}

operator fun Mat4.plus(v: Float): Mat4 = stackPush {
    Mat4(v1 + v, v2 + v, v3 + v, v4 + v)
}

operator fun Mat4.minus(v: Float): Mat4 = stackPush {
    Mat4(v1 - v, v2 - v, v3 - v, v4 - v)
}

operator fun Mat4.times(v: Float): Mat4 = stackPush {
    Mat4(v1 * v, v2 * v, v3 * v, v4 * v)
}

operator fun Mat4.div(v: Float): Mat4 = stackPush {
    Mat4(v1 / v, v2 / v, v3 / v, v4 / v)
}

operator fun Mat4.plus(m: Mat4): Mat4 = stackPush {
    Mat4(v1 + m.v1, v2 + m.v2, v3 + m.v3, v4 + m.v4)
}

operator fun Mat4.minus(m: Mat4): Mat4 = stackPush {
    Mat4(v1 - m.v1, v2 - m.v2, v3 - m.v3, v4 - m.v4)
}

operator fun Mat4.div(m: Mat4): Mat4 = stackPush {
    Mat4(v1 / m.v1, v2 / m.v2, v3 / m.v3, v4 / m.v4)
}

operator fun Mat4.times(m: Mat4): Mat4 = stackPush {
    val m1 = this@times
    val m2 = m
    val m3 = Mat4()
    for (r in 0..3) {
        for (c in 0..3) {
            for (i in 0..3) {
                m3[r][c] += m1[r][i] * m2[i][c]
            }
        }
    }
    return m3
}

operator fun Mat4.unaryMinus(): Mat4 = stackPush {
    val m1 = this@unaryMinus
    val m2 = Mat4()
    for (r in 0..3) {
        for (c in 0..3) {
            m2[r][c] = -m1[r][c]
        }
    }
    return m2
}