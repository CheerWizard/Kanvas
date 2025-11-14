package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioEncoding

fun AudioEncoding.toEncoding(): Int {
    return when (this) {
        AudioEncoding.PCM_8_BIT -> 8
        AudioEncoding.PCM_16_BIT -> 16
        AudioEncoding.PCM_FLOAT -> 32
    }
}
