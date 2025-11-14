package com.cws.kanvas.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples

actual class AudioOutputStream {

    private var track: AudioTrack? = null

    actual fun init(config: AudioConfig) {
        track = AudioTrack(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build(),
            AudioFormat.Builder()
                .setSampleRate(config.sampleRate)
                .setEncoding(config.encoding.toEncoding())
                .setChannelMask(config.channel.toChannelOutput())
                .build(),
            AudioTrack.getMinBufferSize(
                config.sampleRate,
                config.channel.toChannelOutput(),
                config.encoding.toEncoding(),
            ) * 2, // this can be configured to make playback more smooth
            AudioTrack.MODE_STREAM,
            AudioManager.AUDIO_SESSION_ID_GENERATE,
        )
    }

    actual fun write(samples: AudioSamples, offset: Int, size: Int): Int {
        return track?.write(samples.shorts, offset, size) ?: 0
    }

    actual fun play() {
        track?.play()
    }

    actual fun pause() {
        track?.pause()
    }

    actual fun stop() {
        track?.stop()
    }

    actual fun release() {
        track?.release()
        track = null
    }

}