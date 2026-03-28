package com.cws.kanvas.assetc.audio

import org.lwjgl.openal.AL10.*

enum class AudioFormat {
    Mono8,
    Mono16,
    Stereo8,
    Stereo16,
}

fun AudioFormat.toALFormat() = when (this) {
    AudioFormat.Mono8 -> AL_FORMAT_MONO8
    AudioFormat.Mono16 -> AL_FORMAT_MONO16
    AudioFormat.Stereo8 -> AL_FORMAT_STEREO8
    AudioFormat.Stereo16 -> AL_FORMAT_STEREO16
}
