package com.cws.kanvas.rendering.backend

abstract class Resource<Handle, Config>(val config: Config) {

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

}