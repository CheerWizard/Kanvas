package com.cws.kanvas.assetc.audio

import org.lwjgl.openal.AL10.*

enum class AudioPlayerStatus {
    Unknown,
    Initialized,
    Playing,
    Paused,
    Stopped
}

fun Int.toAudioPlayerStatus(): AudioPlayerStatus = when (this) {
    AL_INITIAL -> AudioPlayerStatus.Initialized
    AL_PLAYING -> AudioPlayerStatus.Playing
    AL_PAUSED -> AudioPlayerStatus.Paused
    AL_STOPPED -> AudioPlayerStatus.Stopped
    else -> AudioPlayerStatus.Unknown
}
