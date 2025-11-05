package com.cws.kanvas.audio

import android.media.AudioFormat
import android.media.AudioTrack

actual class AudioPlayer {

    private val tracks = mutableMapOf<String, AudioTrack>()

    actual suspend fun load(audioData: AudioData) {
        val track = AudioTrack.Builder()
            .setAudioFormat(
                AudioFormat.Builder()
                    .setSampleRate(audioData.sampleRate)
                    .setEncoding(audioData.encoding.toEncoding())
                    .setChannelMask(audioData.channels.toChannelOutput())
                    .build()
            )
            .setBufferSizeInBytes(audioData.samples.size * 2)
            .build()
        tracks[audioData.id] = track
        track.write(audioData.samples, 0, audioData.samples.size)
    }

    actual suspend fun play(audioId: String) {
        tracks[audioId]?.play()
    }

    actual suspend fun pause(audioId: String) {
        tracks[audioId]?.pause()
    }

    actual suspend fun stop(audioId: String) {
        tracks[audioId]?.stop()
    }

    actual suspend fun remove(audioId: String) {
        tracks[audioId]?.let { track ->
            track.stop()
            track.release()
        }
        tracks.remove(audioId)
    }

    actual suspend fun release() {
        tracks.forEach { id, track ->
            track.release()
        }
        tracks.clear()
    }

}