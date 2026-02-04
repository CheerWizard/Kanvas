package com.cws.kanvas.rendering.backend

import com.cws.std.memory.INativeData

abstract class Resource<Handle, Info : INativeData>(val info: Info) {

    var handle: Handle? = null
        protected set

    fun create() {
        if (handle == null) {
            onCreate()
        }
    }

    protected abstract fun onCreate()

    fun destroy() {
        if (handle != null) {
            onDestroy()
            handle = null
        }
    }

    protected abstract fun onDestroy()

    abstract fun setInfo()

    inline fun <T : Resource<Handle, Info>, R> T.useOnce(block: T.() -> R): R {
        create()
        val result = block()
        destroy()
        return result
    }

}