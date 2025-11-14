package com.cws.kanvas.audio

import android.media.AudioRecord
import android.media.MediaRecorder
import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples

actual class AudioInputStream {

    private var audioRecord: AudioRecord? = null

    actual fun init(config: AudioConfig) {
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            config.sampleRate,
            config.channel.toChannelInput(),
            config.encoding.toEncoding(),
            AudioRecord.getMinBufferSize(
                config.sampleRate,
                config.channel.toChannelInput(),
                config.encoding.toEncoding(),
            ),
        )
    }

    actual fun start() {
        audioRecord?.startRecording()
    }

    actual fun stop() {
        audioRecord?.stop()
    }

    actual fun read(samples: AudioSamples, offset: Int, size: Int): Int {
        return audioRecord?.read(samples.shorts, offset, size) ?: 0
    }

    actual fun release() {
        audioRecord?.release()
    }

}