package com.cws.kanvas.assetc.audio

import com.cws.kanvas.assetc.Sound
import com.cws.print.Print

class SoundPool(
    private val context: AudioContext,
    size: Int,
) {

    companion object {
        private const val TAG = "SoundPool"
    }

    private val buffers = mutableMapOf<String, AudioBuffer>()
    private val playerPool = Array(size) { AudioPlayer(context) }
    private val activePlayers = mutableMapOf<String, AudioPlayer>()

    fun create() {
        playerPool.forEach { it.create() }
    }

    fun destroy() {
        playerPool.forEach { it.destroy() }
        buffers.forEach { (id, buffer) -> buffer.destroy() }
    }

    fun addSound(soundId: String, sound: Sound) {
        if (sound.format == null) return

        val buffer = AudioBuffer(context, sound.format, sound.sampleRate)
        buffer.create()
        buffer.write(sound.samples.buffer)
        buffers[soundId] = buffer
    }

    fun removeSound(soundId: String) {
        val buffer = buffers.remove(soundId)
        buffer?.destroy()
    }

    fun hasSound(soundId: String) = buffers.contains(soundId)

    fun play(soundId: String) {
        val player = playerPool.firstOrNull { it.getStatus() != AudioPlayerStatus.Playing }
        if (player != null) {
            val buffer = buffers[soundId]
            if (buffer != null) {
                activePlayers[soundId] = player
                player.play(buffer)
            } else {
                Print.w(TAG, "Failed to find suitable buffer to play!")
            }
        } else {
            Print.w(TAG, "Failed to find available player to play! All players are busy!")
        }
    }

    fun pause(soundId: String) {
        val player = activePlayers[soundId]
        player?.pause()
    }

    fun stop(soundId: String) {
        val player = activePlayers[soundId]
        player?.stop()
    }

}
