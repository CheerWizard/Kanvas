package com.cws.kanvas.audio

import org.w3c.dom.Audio
import org.w3c.dom.url.URL
import org.w3c.files.Blob

actual class AudioPlayer {

    private val audios = mutableMapOf<String, Audio>()

    actual suspend fun load(audioData: AudioData) {
        val blob = Blob(arrayOf(audioData.samples))
        val audio = Audio()
        audio.src = URL.createObjectURL(blob)
    }

    actual suspend fun play(audioId: String) {
        audios[audioId]?.play()
    }

    actual suspend fun pause(audioId: String) {
        audios[audioId]?.pause()
    }

    actual suspend fun stop(audioId: String) {
        audios[audioId]?.pause()
    }

    actual suspend fun remove(audioId: String) {
        audios[audioId]?.pause()
        audios.remove(audioId)
    }

    actual suspend fun release() {
        audios.keys.forEach {
            remove(it)
        }
    }

}