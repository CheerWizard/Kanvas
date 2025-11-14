package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples

interface AudioSource {
    fun open()
    fun close()
    fun read(config: AudioConfig)
    fun read(samples: AudioSamples, offset: Int, size: Int)
    fun write(config: AudioConfig)
    fun write(samples: AudioSamples, offset: Int, size: Int)
}