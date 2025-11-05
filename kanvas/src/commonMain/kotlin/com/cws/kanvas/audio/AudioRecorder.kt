package com.cws.kanvas.audio

expect class AudioRecorder {
    suspend fun start(audioData: AudioData)
    suspend fun stop()
    suspend fun release()
}