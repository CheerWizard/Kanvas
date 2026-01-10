package com.cws.std.memory

interface NativeCollection {

    val size: Int

    fun release()

    fun clear()

}