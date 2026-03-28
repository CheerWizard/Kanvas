package com.cws.kanvas.assetc.audio

import com.cws.print.Print
import org.lwjgl.openal.AL
import org.lwjgl.openal.ALC
import org.lwjgl.openal.ALC10.*

class AudioContext {

    companion object {
        private const val TAG = "AudioContext"
    }

    private val deviceSpecifier: String? = null
    private val contextAttributes: IntArray? = null

    private var deviceHandle = 0L
    private var contextHandle = 0L

    fun create() {
        deviceHandle = alcOpenDevice(deviceSpecifier)
        Print.assert(deviceHandle != 0L, TAG, "Failed to create OpenAL device!")

        contextHandle = alcCreateContext(deviceHandle, contextAttributes)
        Print.assert(contextHandle != 0L, TAG, "Failed to create OpenAL context!")

        alcMakeContextCurrent(contextHandle)

        try {
            val capabilities = ALC.createCapabilities(deviceHandle)
            try {
                AL.createCapabilities(capabilities)
            } catch (e: Exception) {
                Print.assert(false, TAG, "Failed to create OpenAL capabilities!", e)
            }
        } catch (e: Exception) {
            Print.assert(false, TAG, "Failed to create OpenAL capabilities!", e)
        }
    }

    fun destroy() {
        if (contextHandle != 0L) {
            alcDestroyContext(contextHandle)
            contextHandle = 0L
        }

        if (deviceHandle != 0L) {
            alcCloseDevice(deviceHandle)
            deviceHandle = 0L
        }
    }

}
