package com.cws.kanvas.audio

import android.media.AudioFormat

fun AudioEncoding.toEncoding(): Int {
    return when (this) {
        AudioEncoding.PCM_16_BIT -> AudioFormat.ENCODING_PCM_16BIT
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