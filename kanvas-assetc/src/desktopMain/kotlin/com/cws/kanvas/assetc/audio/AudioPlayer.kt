package com.cws.kanvas.assetc.audio

import com.cws.print.Print
import org.lwjgl.openal.AL10.*

class AudioPlayer(
    private val context: AudioContext,
) {

    companion object {
        private const val TAG = "AudioPlayer"
    }

    private var handle = 0

    fun create() {
        handle = alGenSources()
        if (handle == 0) {
            Print.e(TAG, "Failed to create OpenAL source!")
            return
        }
    }

    fun destroy() {
        if (handle != 0) {
            alDeleteSources(handle)
            handle = 0
        }
    }

    fun play(buffer: AudioBuffer) {
        if (handle == 0 || buffer.handle == 0) return
        alSourcei(handle, AL_BUFFER, buffer.handle)
        alSourcePlay(handle)
    }

    fun pause() {
        if (handle != 0) {
            alSourcePause(handle)
        }
    }

    fun stop() {
        if (handle != 0) {
            alSourceStop(handle)
        }
    }

    fun getStatus(): AudioPlayerStatus {
        return alGetSourcei(handle, AL_SOURCE_STATE).toAudioPlayerStatus()
    }

}
