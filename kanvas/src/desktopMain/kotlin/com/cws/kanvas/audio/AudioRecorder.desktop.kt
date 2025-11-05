package com.cws.kanvas.audio

import javax.sound.sampled.*

actual class AudioRecorder {

    private var targetDataLine: TargetDataLine? = null
    private var buffer: ByteArray? = null
    private var bytesRead: Int = 0
    private var audioData: AudioData? = null

    actual suspend fun start(audioData: AudioData) {
        val format = AudioFormat(
            audioData.sampleRate.toFloat(),
            audioData.encoding.toEncoding(),
            audioData.channels.size,
            true,
            false,
        )
        val info = DataLine.Info(TargetDataLine::class.java, format)
        val line = AudioSystem.getLine(info) as TargetDataLine
        line.open(format)
        line.start()

        val numBytes = (format.frameSize * format.frameRate *
                (audioData.duration.inWholeMilliseconds / 1000f)).toInt()
        buffer = ByteArray(numBytes)
        bytesRead = line.read(buffer, 0, numBytes)
        this.audioData = audioData
    }

    actual suspend fun stop() {
        targetDataLine?.stop()
    }

    actual suspend fun release() {
        targetDataLine?.close()
    }

}