package com.cws.kanvas.rendering.frontend

import com.cws.kanvas.rendering.backend.Buffer
import com.cws.kanvas.rendering.backend.BufferConfig
import com.cws.kanvas.rendering.backend.Device
import com.cws.std.memory.INativeData
import com.cws.std.memory.NativeDataList

open class TypedBuffer<T : INativeData>(
    device: Device,
    config: BufferConfig,
    factory: () -> T,
) : Buffer(device, config) {

    private val list = NativeDataList(config.size.toInt(), factory)

    fun update(frame: Int, index: Int, data: T) {
        list[frame + index] = data
    }

    fun update(frame: Int, index: Int, vararg array: T) {
        array.forEachIndexed { i, data ->
            list[frame + index + i * data.sizeBytes] = data
        }
    }

    fun flush(frame: Int, index: Int, data: T) {
        val size = data.sizeBytes
        val offset = (frame + index) * size
        write(list.buffer, offset, offset, size)
    }

    fun flush() {
        write(list.buffer, 0, 0, list.capacity)
    }

}