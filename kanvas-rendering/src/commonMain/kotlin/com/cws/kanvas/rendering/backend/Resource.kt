package com.cws.kanvas.rendering.backend

abstract class Resource<Handle, Info>(val info: Info) {

    var handle: Handle? = null
        protected set

    fun create() {
        if (handle == null) {
            onCreate()
        }
    }

    protected abstract fun onCreate()

    fun release() {
        if (handle != null) {
            onRelease()
            handle = null
        }
    }

    protected abstract fun onRelease()

    inline fun <T : Resource<Handle, Info>, R> T.useOnce(block: T.() -> R): R {
        create()
        val result = block()
        release()
        return result
    }

}