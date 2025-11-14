package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples

expect class AudioOutputStream() {
    fun init(config: AudioConfig = AudioConfig())
    fun play()
    fun pause()
    fun stop()
    fun write(samples: AudioSamples, offset: Int, size: Int): Int
    fun release()
}