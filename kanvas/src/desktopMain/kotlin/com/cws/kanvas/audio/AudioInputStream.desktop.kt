package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples
import javax.sound.sampled.*

actual class AudioInputStream {

    private var targetDataLine: TargetDataLine? = null

    actual fun init(config: AudioConfig) {
        val format = AudioFormat(
            config.sampleRate.toFloat(),
            config.encoding.toEncoding(),
            config.channel.size,
            true,
            false,
        )

        val info = DataLine.Info(TargetDataLine::class.java, format)

        targetDataLine = AudioSystem.getLine(info) as TargetDataLine
        targetDataLine?.open(format)
    }

    actual fun start() {
        targetDataLine?.start()
    }

    actual fun stop() {
        targetDataLine?.stop()
    }

    actual fun read(samples: AudioSamples, offset: Int, size: Int): Int {
        return targetDataLine?.read(samples.bytes, 0, samples.bytes.size) ?: 0
    }

    actual fun release() {
        targetDataLine?.close()
    }

}