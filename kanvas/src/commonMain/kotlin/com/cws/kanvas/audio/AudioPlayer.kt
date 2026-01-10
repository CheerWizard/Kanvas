package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioData
import com.cws.std.async.Thread
import com.cws.print.Print

class AudioPlayer {

    enum class State {
        PLAYING,
        PAUSED,
        STOPPED,
    }

    companion object {
        private const val TAG = "AudioPlayer"
    }

    private val playerThread = Thread(
        name = TAG,
        priority = 1,
        task = ::runPlayer,
    )

    private var audioOutputStream = AudioOutputStream()
    private val audioDataMap = mutableMapOf<String, AudioData>()
    private val audioQueue = ArrayDeque<AudioData>()

    private var state = State.STOPPED

    fun init(config: AudioConfig = AudioConfig()) {
        audioOutputStream.init(config)
    }

    fun release() {
        audioOutputStream.release()
    }

    fun add(audioData: AudioData) {
        audioDataMap[audioData.id] = audioData
    }

    fun remove(id: String) {
        audioDataMap.remove(id)
    }

    private fun runPlayer() {
        while (state == State.PLAYING) {
            val audioData = audioQueue.removeLast()
            audioOutputStream.write(audioData.samples, 0, audioData.samples.size)
        }
    }

    fun play(id: String) {
        if (!audioDataMap.containsKey(id)) {
            Print.w(TAG, "Failed to find to play audio $id")
            return
        }
        val audioData = audioDataMap.getValue(id)
        audioQueue.addFirst(audioData)
        play()
    }

    fun play() {
        state = State.PLAYING
        audioOutputStream.play()
        playerThread.start()
    }

    fun pause(id: String) {
        if (!audioDataMap.containsKey(id)) {
            Print.w(TAG, "Failed to find to play audio $id")
            return
        }
        pause()
    }

    fun pause() {
        state = State.PAUSED
        audioOutputStream.pause()
    }

}