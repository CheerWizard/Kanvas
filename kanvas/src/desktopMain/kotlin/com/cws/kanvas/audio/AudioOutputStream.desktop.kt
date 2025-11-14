package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples
import com.cws.kanvas.audio.data.DEFAULT_SAMPLE_RATE_KHZ
import javax.sound.sampled.*

actual class AudioOutputStream {

    private var sourceDataLine: SourceDataLine? = null

    actual fun init(config: AudioConfig) {
        val format = AudioFormat(
            DEFAULT_SAMPLE_RATE_KHZ.toFloat(),
            config.encoding.toEncoding(),
            config.channel.size,
            true,
            false
        )

        val info = DataLine.Info(SourceDataLine::class.java, format)

        sourceDataLine = AudioSystem.getLine(info) as SourceDataLine
        sourceDataLine?.open(format)
    }

    actual fun write(samples: AudioSamples, offset: Int, size: Int): Int {
        return sourceDataLine?.write(samples.bytes, 0, samples.bytes.size) ?: 0
    }

    actual fun play() {
        sourceDataLine?.start()
    }

    actual fun pause() {
        sourceDataLine?.stop()
    }

    actual fun stop() {
        sourceDataLine?.let {
            it.flush()
            it.stop()
        }
    }

    actual fun release() {
        sourceDataLine?.close()
    }

}