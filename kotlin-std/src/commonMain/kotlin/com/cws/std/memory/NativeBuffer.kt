package com.cws.std.memory

expect open class NativeBuffer(
    capacity: Int,
    memoryLayout: MemoryLayout = MemoryLayout.KOTLIN,
) {

    constructor(address: Long, capacity: Int)

    var position: Int
    val capacity: Int
    val address: Long
    val memoryLayout: MemoryLayout

    fun view(): Any

    fun release()

    fun resize(newCapacity: Int)

    fun clear()

    fun flip()

    fun setBytes(index: Int, bytes: ByteArray)

    fun clone(): NativeBuffer

    fun copyTo(
        dest: NativeBuffer,
        srcIndex: Int,
        destIndex: Int,
        size: Int,
    )

    fun setTo(value: Byte, destIndex: Int, size: Int)

    fun setByte(index: Int, value: Byte)
    fun getByte(index: Int): Byte

    fun setShort(index: Int, value: Short)
    fun getShort(index: Int): Short

    fun setInt(index: Int, value: Int)
    fun getInt(index: Int): Int

    fun setFloat(index: Int, value: Float)
    fun getFloat(index: Int): Float

}

val NativeBuffer.byte: Byte get() = pop()
val NativeBuffer.boolean: Boolean get() = popBoolean()
val NativeBuffer.short: Short get() = popShort()
val NativeBuffer.int: Int get() = popInt()
val NativeBuffer.float: Float get() = popFloat()
val NativeBuffer.long: Long get() = popLong()
val NativeBuffer.double: Double get() = popDouble()

fun NativeBuffer.push(value: Byte) {
    ensureSize()
    setByte(position, value)
    position += value.sizeBytes(memoryLayout)
}

fun NativeBuffer.pop(): Byte {
    position -= Byte.SIZE_BYTES
    assertPop()
    return getByte(position)
}

fun NativeBuffer.pushBoolean(value: Boolean) = push(if (value) 1 else 0)
fun NativeBuffer.popBoolean() = pop() == 1.toByte()

fun NativeBuffer.pushShort(value: Short) {
    ensureSize()
    setShort(position, value)
    position += value.sizeBytes(memoryLayout)
}
fun NativeBuffer.popShort(): Short {
    position -= Short.SIZE_BYTES
    assertPop()
    return getShort(position)
}

fun NativeBuffer.pushInt(value: Int) {
    ensureSize()
    setInt(position, value)
    position += value.sizeBytes(memoryLayout)
}
fun NativeBuffer.popInt(): Int {
    position -= Int.SIZE_BYTES
    assertPop()
    return getInt(position)
}

fun NativeBuffer.pushLong(value: Long) {
    ensureSize()
    packLong(position, value)
    position += value.sizeBytes(memoryLayout)
}
fun NativeBuffer.popLong(): Long {
    position -= Long.SIZE_BYTES
    assertPop()
    return unpackLong(position)
}

fun NativeBuffer.pushFloat(value: Float) {
    ensureSize()
    setFloat(position, value)
    position += value.sizeBytes(memoryLayout)
}
fun NativeBuffer.popFloat(): Float {
    position -= Float.SIZE_BYTES
    assertPop()
    return getFloat(position)
}

fun NativeBuffer.pushDouble(value: Double) {
    ensureSize()
    packDouble(position, value)
    position += value.sizeBytes(memoryLayout)
}
fun NativeBuffer.popDouble(): Double {
    position -= Double.SIZE_BYTES
    assertPop()
    return unpackDouble(position)
}

fun NativeBuffer.setLong(index: Int, value: Long) = packLong(index, value)
fun NativeBuffer.setDouble(index: Int, value: Double) = packDouble(index, value)

fun NativeBuffer.getLong(index: Int): Long = unpackLong(index)
fun NativeBuffer.getDouble(index: Int): Double = unpackDouble(index)

inline fun <reified T> NativeBuffer.pushArray(value: T) {
    when (T::class) {
        ByteArray::class -> pushBytes(value as ByteArray)
        BooleanArray::class -> (value as BooleanArray).forEach { pushBoolean(it) }
        ShortArray::class -> (value as ShortArray).forEach { pushShort(it) }
        IntArray::class -> (value as IntArray).forEach { pushInt(it) }
        LongArray::class -> (value as LongArray).forEach { pushLong(it) }
        FloatArray::class -> (value as FloatArray).forEach { pushFloat(it) }
        DoubleArray::class -> (value as DoubleArray).forEach { pushDouble(it) }
        else -> throw UnsupportedOperationException("Unsupported type!")
    }
}

inline fun <reified T> NativeBuffer.setArray(index: Int, value: T) {
    when (T::class) {
        ByteArray::class -> setBytes(index, value as ByteArray)

        BooleanArray::class -> (value as BooleanArray).forEachIndexed { i, v ->
            setByte(index + i * v.bytes, if (v) 1 else 0)
        }

        ShortArray::class -> (value as ShortArray).forEachIndexed { i, v ->
            setShort(index + i * v.bytes, v)
        }

        IntArray::class -> (value as IntArray).forEachIndexed { i, v ->
            setInt(index + i * v.bytes, v)
        }

        LongArray::class -> (value as LongArray).forEachIndexed { i, v ->
            setLong(index + i * v.bytes, v)
        }

        FloatArray::class -> (value as FloatArray).forEachIndexed { i, v ->
            setFloat(index + i * v.bytes, v)
        }

        DoubleArray::class -> (value as DoubleArray).forEachIndexed { i, v ->
            setDouble(index + i * v.bytes, v)
        }

        else -> throw UnsupportedOperationException("Unsupported type!")
    }
}

fun NativeBuffer.packLong(index: Int, value: Long) {
    ensureCapacity(index)
    val high = (value shr 32).toInt()
    val low = value.toInt()
    setInt(index, high)
    setInt(index + 1, low)
}

fun NativeBuffer.unpackLong(index: Int): Long {
    ensureCapacity(index)
    val high = getInt(index)
    val low = getInt(index + 1)
    return (high.toLong() shl 32) or (low.toLong() and 0xFFFFFFFF)
}

fun NativeBuffer.packDouble(index: Int, value: Double) {
    ensureCapacity(index)
    val bits = value.toBits()
    val high = (bits shr 32).toInt()
    val low = bits.toInt()
    setFloat(index, Float.fromBits(high))
    setFloat(index + 1, Float.fromBits(low))
}

fun NativeBuffer.unpackDouble(index: Int): Double {
    ensureCapacity(index)
    val high = getFloat(index).toBits()
    val low = getFloat(index + 1).toBits()
    val bits = (high.toLong() shl 32) or (low.toLong() and 0xFFFFFFFF)
    return Double.fromBits(bits)
}

fun NativeBuffer.pushBytes(value: ByteArray) {
    setBytes(position, value)
    position += value.size
}

fun NativeBuffer.ensureSize() {
    if (position == capacity) {
        resize(capacity * 2)
    }
}

fun NativeBuffer.ensureCapacity(i: Int) {
    if (i >= capacity || i < 0) {
        throw IllegalArgumentException("NativeBuffer: Index is out of bounds! i=$i capacity=${capacity}")
    }
}

fun NativeBuffer.assertPop() {
    if (position < 0) {
        throw IllegalArgumentException("NativeBuffer: Position is out of bounds during pop! $position is < 0")
    }
}

fun NativeBuffer.push(data: INativeData) {
    data.pack(this)
    position += data.sizeBytes(memoryLayout)
}

fun NativeBuffer.nextBoolean(): Boolean {
    val value = getByte(position) == 1.toByte()
    position += value.sizeBytes(memoryLayout)
    return value
}

fun NativeBuffer.nextShort(): Short {
    val value = getShort(position)
    position += value.sizeBytes(memoryLayout)
    return value
}

fun NativeBuffer.nextInt(): Int {
    val value = getInt(position)
    position += value.sizeBytes(memoryLayout)
    return value
}

fun NativeBuffer.nextFloat(): Float {
    val value = getFloat(position)
    position += value.sizeBytes(memoryLayout)
    return value
}

fun NativeBuffer.nextLong(): Long {
    val value = getLong(position)
    position += value.sizeBytes(memoryLayout)
    return value
}

fun NativeBuffer.nextDouble(): Double {
    val value = getDouble(position)
    position += value.sizeBytes(memoryLayout)
    return value
}

fun <T : INativeData> NativeBuffer.next(data: INativeData): T {
    val newData = data.unpack(this)
    position += data.sizeBytes(memoryLayout)
    return newData as T
}
