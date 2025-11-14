package com.cws.kanvas.audio.data

import com.cws.kanvas.audio.data.AudioSamples
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.time.Duration

@Serializable
data class AudioData(
    val id: String,
    val config: AudioConfig,
    val samplesFilepath: String,
    @Transient
    var samples: AudioSamples = AudioSamples(0),
    @Transient
    var flippedSamples: AudioSamples = samples,
) {

    fun flip() {
        if (flippedSamples.size < samples.size) {
            flippedSamples = AudioSamples(samples.size)
        }

        // TODO consider how to concat both loops into single loop to iterate only once
        {
            val lastIndex = samples.size - 1
            for (i in 0..lastIndex) {
                flippedSamples.shorts[lastIndex - i] = samples.shorts[i]
            }
        }

        {
            val lastIndex = samples.bytes.size - 1
            for (i in 0..lastIndex) {
                flippedSamples.bytes[lastIndex - i] = samples.bytes[i]
            }
        }
    }

}

fun createSamples(sampleRate: Int, duration: Duration): ShortArray {
    return ShortArray(
        (sampleRate.toFloat() * duration.inWholeMilliseconds.toFloat() / 1000f).toInt()
    )
}