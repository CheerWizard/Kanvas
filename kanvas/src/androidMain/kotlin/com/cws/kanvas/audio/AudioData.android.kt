package com.cws.kanvas.audio

import android.media.AudioFormat
import com.cws.kanvas.audio.data.AudioChannel
import com.cws.kanvas.audio.data.AudioEncoding

fun AudioEncoding.toEncoding(): Int {
    return when (this) {
        AudioEncoding.PCM_8_BIT -> AudioFormat.ENCODING_PCM_8BIT
        AudioEncoding.PCM_16_BIT -> AudioFormat.ENCODING_PCM_16BIT
        AudioEncoding.PCM_FLOAT -> AudioFormat.ENCODING_PCM_FLOAT
    }
}

fun AudioChannel.toChannelInput(): Int {
    return when (this) {
        AudioChannel.MONO -> AudioFormat.CHANNEL_IN_MONO
        AudioChannel.STEREO -> AudioFormat.CHANNEL_IN_STEREO
    }
}

fun AudioChannel.toChannelOutput(): Int {
    return when (this) {
        AudioChannel.MONO -> AudioFormat.CHANNEL_OUT_MONO
        AudioChannel.STEREO -> AudioFormat.CHANNEL_OUT_STEREO
    }
}