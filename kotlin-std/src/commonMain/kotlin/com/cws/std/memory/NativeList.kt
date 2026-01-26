package com.cws.std.memory

// TODO move to NativeBuffer class to extensions and internals
open class NativeList(
    capacity: Int,
    val typeSize: Int = Byte.SIZE_BYTES
) : NativeCollection {

    var buffer = NativeBuffer(capacity * typeSize)
        protected set

    var position: Int
        protected set(value) {
            buffer.position = value
        }
        get() = buffer.position

    val capacity: Int = buffer.capacity

    override val size: Int get() = position / typeSize

    override fun release() {
        buffer.release()
    }

    inline val Byte.bytes: Int get() = Int.SIZE_BYTES
    inline val Boolean.bytes: Int get() = Int.SIZE_BYTES
    inline val Short.bytes: Int get() = Int.SIZE_BYTES
    inline val Int.bytes: Int get() = Int.SIZE_BYTES
    inline val Long.bytes: Int get() = Long.SIZE_BYTES
    inline val Float.bytes: Int get() = Float.SIZE_BYTES
    inline val Double.bytes: Int get() = Double.SIZE_BYTES

    fun addBoolean(value: Boolean) = addBoolean(value, Int.SIZE_BYTES)

    fun addBoolean(value: Boolean, size: Int) = addInt(if (value) 1 else 0, size)

    fun addShort(value: Short) = addShort(value, Int.SIZE_BYTES)

    fun addShort(value: Short, size: Int) = addInt(value.toInt(), size)

    fun addInt(value: Int) {
        addInt(value, value.bytes)
    }

    fun addInt(value: Int, size: Int) {
        buffer.setInt(position, value)
        position += size
    }

    fun addLong(value: Long) {
        addLong(value, value.bytes)
    }

    fun addLong(value: Long, size: Int) {
        packLong(position, value)
        position += size
    }

    fun addFloat(value: Float) {
        addFloat(value, value.bytes)
    }

    fun addFloat(value: Float, size: Int) {
        buffer.setFloat(position, value)
        position += size
    }

    fun addDouble(value: Double) {
        addDouble(value, value.bytes)
    }

    fun addDouble(value: Double, size: Int) {
        packDouble(position, value)
        position += size
    }

    fun setBoolean(index: Int, value: Boolean) = setInt(index, if (value) 1 else 0)

    fun setShort(index: Int, value: Short) = setInt(index, value.toInt())

    fun setInt(index: Int, value: Int) = buffer.setInt(index, value)

    fun setLong(index: Int, value: Long) = packLong(index, value)

    fun setFloat(index: Int, value: Float) = buffer.setFloat(index, value)

    fun setDouble(index: Int, value: Double) = packDouble(index, value)

    fun getBoolean(index: Int): Boolean = getInt(index) == 1

    fun getShort(index: Int): Short = getInt(index).toShort()

    fun getInt(index: Int): Int = buffer.getInt(index)

    fun getLong(index: Int): Long = unpackLong(index)

    fun getFloat(index: Int): Float = buffer.getFloat(index)

    fun getDouble(index: Int): Double = unpackDouble(index)

    protected fun addNativeObject(handle: MemoryHandle) {
        setNativeObject(position, handle)
        position += typeSize
    }

    protected fun setNativeObject(index: Int, handle: MemoryHandle) {
        Heap.copyTo(this, handle, index, typeSize)
    }

    inline fun <reified T> addArray(value: T) {
        when (T::class) {
            ByteArray::class -> addBytes(value as ByteArray)
            BooleanArray::class -> (value as BooleanArray).forEach { addBoolean(it) }
            ShortArray::class -> (value as ShortArray).forEach { addShort(it) }
            IntArray::class -> (value as IntArray).forEach { addInt(it) }
            LongArray::class -> (value as LongArray).forEach { addLong(it) }
            FloatArray::class -> (value as FloatArray).forEach { addFloat(it) }
            DoubleArray::class -> (value as DoubleArray).forEach { addDouble(it) }
            else -> throw UnsupportedOperationException("Unsupported type!")
        }
    }

    inline fun <reified T> setArray(index: Int, value: T) {
        when (T::class) {
            ByteArray::class -> setBytes(index, value as ByteArray)

            BooleanArray::class -> (value as BooleanArray).forEachIndexed { i, v ->
                setBoolean(index + i * v.bytes, v)
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

    fun addByte(byte: Byte) {
        ensureSize()
        buffer.setByte(position++, byte)
    }

    fun setByte(index: Int, value: Byte) {
        ensureCapacity(index)
        buffer.setByte(index, value)
    }

    fun getByte(index: Int): Byte {
        ensureCapacity(index)
        return buffer.getByte(index)
    }

    private fun packLong(index: Int, value: Long) {
        ensureCapacity(index)
        val high = (value shr 32).toInt()
        val low = value.toInt()
        buffer.setInt(index, high)
        buffer.setInt(index + 1, low)
    }

    private fun unpackLong(index: Int): Long {
        ensureCapacity(index)
        val high = buffer.getInt(index)
        val low = buffer.getInt(index + 1)
        return (high.toLong() shl 32) or (low.toLong() and 0xFFFFFFFF)
    }

    private fun packDouble(index: Int, value: Double) {
        ensureCapacity(index)
        val bits = value.toBits()
        val high = (bits shr 32).toInt()
        val low = bits.toInt()
        buffer.setFloat(index, Float.fromBits(high))
        buffer.setFloat(index + 1, Float.fromBits(low))
    }

    private fun unpackDouble(index: Int): Double {
        ensureCapacity(index)
        val high = buffer.getFloat(index).toBits()
        val low = buffer.getFloat(index + 1).toBits()
        val bits = (high.toLong() shl 32) or (low.toLong() and 0xFFFFFFFF)
        return Double.fromBits(bits)
    }

    fun addBytes(value: ByteArray) {
        setBytes(position, value)
        position += value.size
    }

    fun setBytes(index: Int, value: ByteArray) {
        buffer.setBytes(index, value)
    }

    fun copy(src: Int, dest: Int, size: Int) {
        buffer.copyTo(buffer, src, dest, size)
    }

    fun copyTo(dest: NativeList, srcIndex: Int, destIndex: Int, size: Int) {
        buffer.copyTo(dest.buffer, srcIndex, destIndex, size)
    }

    fun remove(i: Int) {
        if (size > 0) {
            position -= typeSize
        }
    }

    fun setTo(value: Byte, dest: Int, size: Int) {
        buffer.setTo(value, dest, size)
    }

    override fun clear() {
        position = 0
    }

    protected fun ensureSize() {
        if (size == this@NativeList.capacity) {
            resize(this@NativeList.capacity * 2)
        }
    }

    protected fun ensureCapacity(i: Int) {
        if (i >= this@NativeList.capacity || i < 0) {
            throw IllegalArgumentException("Index is out of bounds! i=$i capacity=${this@NativeList.capacity}")
        }
    }

    protected open fun resize(newCapacity: Int) {
        buffer.resize(newCapacity)
    }

}