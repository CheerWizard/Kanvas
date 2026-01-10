package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioData
import com.cws.kanvas.audio.data.AudioSamples
import com.cws.std.async.Thread
import kotlin.math.roundToInt

class AudioRecorder {

    enum class State {
        RECORDING,
        STOPPED,
    }

    companion object {
        private const val TAG = "AudioRecorder"
    }

    private val recorderThread = Thread(
        name = TAG,
        priority = 1,
        task = ::runRecorder
    )

    private val audioInputStream = AudioInputStream()

    private var state = State.STOPPED

    private var audioData: AudioData? = null

    fun init(config: AudioConfig = AudioConfig()) {
        audioInputStream.init(config)
    }

    fun release() {
        audioInputStream.release()
    }

    fun start(audioConfig: AudioConfig = AudioConfig()) {
        if (state == State.RECORDING) return

        this.audioData = AudioData(
            id = "Recorded",
            config = audioConfig,
            samplesFilepath = "",
        )
        state = State.RECORDING
        audioInputStream.start()
        recorderThread.start()
    }

    private fun runRecorder() {
        val audioData = this.audioData ?: return

        val sampleRate = audioData.config.sampleRate
        val channels = audioData.config.channel.size
        val minDurationSec = 0.1f
        val minSize = (sampleRate * channels * minDurationSec).toInt()
        val chunkDurationSec = 0.1f
        val chunkSize = (sampleRate * channels * chunkDurationSec).toInt()

        audioData.samples = AudioSamples(minSize)

        var offset = 0
        while (state == State.RECORDING) {
            val readSize = audioInputStream.read(audioData.samples, offset, chunkSize)
            offset += readSize
            if (readSize == 0 && state == State.RECORDING) {
                audioData.samples.resize(
                    (audioData.samples.size.toFloat() * 1.25f).roundToInt()
                )
            }
        }

        audioData.samples.resize(offset)
    }

    fun stop(): AudioData? {
        val audioData = this.audioData
        this.audioData = null
        state = State.STOPPED
        audioInputStream.stop()
        return audioData
    }

}