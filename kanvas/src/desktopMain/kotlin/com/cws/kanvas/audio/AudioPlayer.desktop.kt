package com.cws.kanvas.audio

import javax.sound.sampled.*

actual class AudioPlayer {

    private val sources = mutableMapOf<String, SourceDataLine>()

    actual suspend fun load(audioData: AudioData) {
        val format = AudioFormat(
            audioData.sampleRate.toFloat(),
            audioData.encoding.toEncoding(),
            audioData.channels.size,
            true,
            false
        )

        val info = DataLine.Info(SourceDataLine::class.java, format)
        val sourceLine = AudioSystem.getLine(info) as SourceDataLine

        sourceLine.open(format)
        sourceLine.start()

        val buffer = ByteArray(audioData.samples.size * 2)
        for (i in audioData.samples.indices) {
            val value = audioData.samples[i].toInt()
            buffer[i * 2] = value.toByte()
            buffer[i * 2 + 1] = (value shr 8).toByte()
        }

        sourceLine.write(buffer, 0, buffer.size)
    }

    actual suspend fun play(audioId: String) {
        sources[audioId]?.drain()
    }

    actual suspend fun pause(audioId: String) {
        // TODO pause not implemented
    }

    actual suspend fun stop(audioId: String) {
        sources[audioId]?.stop()
    }

    actual suspend fun remove(audioId: String) {
        sources[audioId]?.close()
        sources.remove(audioId)
    }

    actual suspend fun release() {
        sources.forEach { id, source ->
            source.close()
        }
        sources.clear()
    }

}