package com.cws.kanvas.audio

import platform.AVFAudio.AVAudioEngine

actual class AudioRecorder(
    private val audioEngine: AVAudioEngine,
) {

    val sampleRate = 44100.0
    val format = AVAudioFormat(standardFormatWithSampleRate = sampleRate, channels = 1u)!!
    val frameCount = (sampleRate * durationMillis / 1000.0).toInt()
    val pcmBuffer = AVAudioPCMBuffer(pcmFormat = format, frameCapacity = frameCount.toUInt())!!

    val input = engine.inputNode
    val bus = 0u
    input.installTapOnBus(bus, bufferSize = frameCount.toUInt(), format = format) { buf, _ ->
        pcmBuffer.floatChannelData?.let { channelData ->
            buf.floatChannelData?.get(0)?.let { src ->
                src.readBytes(channelData[0], buf.frameLength.toInt())
            }
        }
        engine.stop()
        input.removeTapOnBus(bus)
        val samples = ShortArray(pcmBuffer.frameLength.toInt()) { i ->
            (pcmBuffer.floatChannelData!![0]!![i] * Short.MAX_VALUE).toInt().toShort()
        }
        cont.resume(PcmAudioData(sampleRate.toInt(), 1, samples))
    }

    try {
        engine.prepare()
        engine.startAndReturnError(null)
    } catch (e: Throwable) {
        cont.resume(PcmAudioData(0, 1, shortArrayOf()))
    }

    actual suspend fun start(audioData: AudioData) {
    }

    actual suspend fun stop() {
    }

    actual suspend fun release() {
    }

}