package com.cws.kanvas.audio

expect class AudioPlayer {
    suspend fun load(audioData: AudioData)
    suspend fun play(audioId: String)
    suspend fun pause(audioId: String)
    suspend fun stop(audioId: String)
    suspend fun remove(audioId: String)
    suspend fun release()
}