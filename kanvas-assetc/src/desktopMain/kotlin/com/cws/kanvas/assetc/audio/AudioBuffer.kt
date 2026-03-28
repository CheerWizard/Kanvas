package com.cws.kanvas.assetc.audio

import com.cws.print.Print
import org.lwjgl.openal.AL10.*
import java.nio.ByteBuffer

class AudioBuffer(
    private val context: AudioContext,
    private val format: AudioFormat,
    private val sampleRate: Int,
) {

    companion object {
        private const val TAG = "AudioStream"
    }

    var handle = 0
        private set

    fun create() {
        handle = alGenBuffers()
        if (handle == 0) {
            Print.e(TAG, "Failed to create OpenAL buffer!")
            return
        }
    }

    fun destroy() {
        if (handle != 0) {
            alDeleteBuffers(handle)
            handle = 0
        }
    }

    fun write(samples: ByteBuffer) {
        alBufferData(handle, format.toALFormat(), samples, sampleRate)
    }

}