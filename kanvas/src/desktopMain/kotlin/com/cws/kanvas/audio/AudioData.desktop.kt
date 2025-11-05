package com.cws.kanvas.audio

fun AudioEncoding.toEncoding(): Int {
    return when (this) {
        AudioEncoding.PCM_16_BIT -> 16
    }
}
