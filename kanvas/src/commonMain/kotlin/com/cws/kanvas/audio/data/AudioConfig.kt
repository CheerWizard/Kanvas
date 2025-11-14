package com.cws.kanvas.audio.data

import kotlinx.serialization.Serializable

@Serializable
data class AudioConfig(
    val channel: AudioChannel = AudioChannel.MONO,
    val encoding: AudioEncoding = AudioEncoding.PCM_16_BIT,
    val sampleRate: Int = DEFAULT_SAMPLE_RATE_KHZ,
)
