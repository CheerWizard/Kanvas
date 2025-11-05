package com.cws.kanvas.audio

import kotlin.time.Duration

data class AudioData(
    val id: String,
    val sampleRate: Int = DEFAULT_SAMPLE_RATE_KHZ,
    val channels: AudioChannel = AudioChannel.MONO,
    val encoding: AudioEncoding = AudioEncoding.PCM_16_BIT,
    val duration: Duration = DEFAULT_RECORD_DURATION,
    var samples: ShortArray = createSamples(sampleRate, duration),
    var flippedSamples: ShortArray = samples,
) {

    fun flip() {
        if (flippedSamples.size < samples.size) {
            flippedSamples = ShortArray(samples.size)
        }

        val lastIndex = samples.lastIndex
        for (i in 0..lastIndex) {
            flippedSamples[lastIndex - i] = samples[i]
        }
    }

}

fun createSamples(sampleRate: Int, duration: Duration): ShortArray {
    return ShortArray(
        (sampleRate.toLong() * duration.inWholeMilliseconds / 1000L).toInt()
    )
}