package com.cws.std.memory

enum class MemoryLayout {
    KOTLIN,
    STD140,
    STD430,
}

val Boolean.Companion.SIZE_BYTES: Int get() = 1

val Boolean.Companion.STD140_SIZE_BYTES: Int get() = 4
val Byte.Companion.STD140_SIZE_BYTES: Int get() = 4
val UByte.Companion.STD140_SIZE_BYTES: Int get() = 4
val Short.Companion.STD140_SIZE_BYTES: Int get() = 4
val UShort.Companion.STD140_SIZE_BYTES: Int get() = 4
val Int.Companion.STD140_SIZE_BYTES: Int get() = 4
val UInt.Companion.STD140_SIZE_BYTES: Int get() = 4
val Long.Companion.STD140_SIZE_BYTES: Int get() = 4
val ULong.Companion.STD140_SIZE_BYTES: Int get() = 4
val Float.Companion.STD140_SIZE_BYTES: Int get() = 4
val Double.Companion.STD140_SIZE_BYTES: Int get() = 4

val Boolean.Companion.STD430_SIZE_BYTES: Int get() = 4
val Byte.Companion.STD430_SIZE_BYTES: Int get() = 4
val UByte.Companion.STD430_SIZE_BYTES: Int get() = 4
val Short.Companion.STD430_SIZE_BYTES: Int get() = 4
val UShort.Companion.STD430_SIZE_BYTES: Int get() = 4
val Int.Companion.STD430_SIZE_BYTES: Int get() = 4
val UInt.Companion.STD430_SIZE_BYTES: Int get() = 4
val Long.Companion.STD430_SIZE_BYTES: Int get() = 4
val ULong.Companion.STD430_SIZE_BYTES: Int get() = 4
val Float.Companion.STD430_SIZE_BYTES: Int get() = 4
val Double.Companion.STD430_SIZE_BYTES: Int get() = 4

fun Boolean.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> Boolean.SIZE_BYTES
    MemoryLayout.STD140 -> Boolean.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> Boolean.STD430_SIZE_BYTES
}

fun Byte.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> Byte.SIZE_BYTES
    MemoryLayout.STD140 -> Byte.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> Byte.STD430_SIZE_BYTES
}

fun UByte.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> UByte.SIZE_BYTES
    MemoryLayout.STD140 -> UByte.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> UByte.STD430_SIZE_BYTES
}

fun Short.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> Short.SIZE_BYTES
    MemoryLayout.STD140 -> Short.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> Short.STD430_SIZE_BYTES
}

fun UShort.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> UShort.SIZE_BYTES
    MemoryLayout.STD140 -> UShort.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> UShort.STD430_SIZE_BYTES
}

fun Int.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> Int.SIZE_BYTES
    MemoryLayout.STD140 -> Int.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> Int.STD430_SIZE_BYTES
}

fun UInt.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> UInt.SIZE_BYTES
    MemoryLayout.STD140 -> UInt.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> UInt.STD430_SIZE_BYTES
}

fun Long.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> Long.SIZE_BYTES
    MemoryLayout.STD140 -> Long.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> Long.STD430_SIZE_BYTES
}

fun ULong.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> ULong.SIZE_BYTES
    MemoryLayout.STD140 -> ULong.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> ULong.STD430_SIZE_BYTES
}

fun Float.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> Float.SIZE_BYTES
    MemoryLayout.STD140 -> Float.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> Float.STD430_SIZE_BYTES
}

fun Double.sizeBytes(layout: MemoryLayout) = when (layout) {
    MemoryLayout.KOTLIN -> Double.SIZE_BYTES
    MemoryLayout.STD140 -> Double.STD140_SIZE_BYTES
    MemoryLayout.STD430 -> Double.STD430_SIZE_BYTES
}