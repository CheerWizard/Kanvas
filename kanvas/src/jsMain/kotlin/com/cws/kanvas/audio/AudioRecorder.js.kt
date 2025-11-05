package com.cws.kanvas.audio

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.dom.mediacapture.MediaStream
import org.w3c.dom.mediacapture.MediaStreamConstraints
import org.w3c.files.Blob

external class MediaRecorder(stream: MediaStream) {
    fun start()
    fun stop()
    var ondataavailable: (dynamic) -> Unit
}

actual class AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var blobs = mutableListOf<Blob>()

    actual suspend fun start(audioData: AudioData) {
        val stream = window
            .navigator
            .mediaDevices
            .getUserMedia(MediaStreamConstraints(audio = true))
            .await()

        recorder = MediaRecorder(stream)

        recorder?.let { recorder ->
            recorder.ondataavailable = { event ->
                val blob = event.asDynamic().data as Blob
                blobs.add(blob)
            }
            recorder.start()
        }
    }

    actual suspend fun stop() {
        recorder?.stop()
    }

    actual suspend fun release() {
        blobs.clear()
    }

}