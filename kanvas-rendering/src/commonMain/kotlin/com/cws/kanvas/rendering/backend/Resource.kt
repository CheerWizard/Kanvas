package com.cws.kanvas.rendering.backend

abstract class Resource<Handle>() {

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

    inline fun <T : Resource<Handle>, R> T.useOnce(block: T.() -> R): R {
        create()
        val result = block()
        destroy()
        return result
    }

}