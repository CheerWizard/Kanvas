package com.cws.kanvas.audio

import android.Manifest
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission

actual class AudioRecorder() {

    private var audioRecord: AudioRecord? = null

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    actual suspend fun start(audioData: AudioData) {
        val bufferSize = AudioRecord.getMinBufferSize(
            audioData.sampleRate,
            audioData.channels.toChannelInput(),
            audioData.encoding.toEncoding(),
        )

        audioData.samples = ShortArray(
            (audioData.sampleRate.toLong() * audioData.duration.inWholeMilliseconds / 1000L).toInt()
        )

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            audioData.sampleRate,
            audioData.channels.toChannelInput(),
            audioData.encoding.toEncoding(),
            bufferSize,
        )

        audioRecord?.let { recorder ->
            recorder.startRecording()
            recorder.read(audioData.samples, 0, audioData.samples.size)
        }
    }

    actual suspend fun stop() {
        audioRecord?.stop()
    }

    actual suspend fun release() {
        audioRecord?.release()
        audioRecord = null
    }

}