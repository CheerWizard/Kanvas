package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples

expect class AudioInputStream() {
    fun init(config: AudioConfig = AudioConfig())
    fun start()
    fun stop()
    // returns how much samples was read
    fun read(samples: AudioSamples, offset: Int, size: Int): Int
    fun release()
}