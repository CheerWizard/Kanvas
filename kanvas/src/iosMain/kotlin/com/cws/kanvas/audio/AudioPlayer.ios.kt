package com.cws.kanvas.audio

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import platform.AVFAudio.AVAudioEngine
import platform.AVFAudio.AVAudioFormat
import platform.AVFAudio.AVAudioPCMBuffer
import platform.AVFAudio.AVAudioPlayerNode
import platform.Foundation.NSError

actual class AudioPlayer(
    private val audioEngine: AVAudioEngine,
) {

    private val players = mutableMapOf<String, AVAudioPlayerNode>()
    private val buffers = mutableMapOf<String, AVAudioPCMBuffer>()

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun load(audioData: AudioData) {
        val format = AVAudioFormat(
            standardFormatWithSampleRate = audioData.sampleRate.toDouble(),
            channels = audioData.channels.size.toUInt(),
        )

        val buffer = AVAudioPCMBuffer(
            pCMFormat = format,
            frameCapacity = audioData.samples.size.toUInt(),
        )

        val ptr = buffer.floatChannelData.rawValue
        for (i in audioData.samples.indices) {
            ptr[i] = audioData.samples[i].toFloat() / Short.MAX_VALUE
        }

        buffer.frameLength = audioData.samples.size.toUInt()

        val player = AVAudioPlayerNode()

        audioEngine.attachNode(player)
        audioEngine.connect(player, audioEngine.mainMixerNode, format)
        audioEngine.prepare()

        memScoped {
            val error = alloc<NSError>()
            audioEngine.startAndReturnError(error)
        }

        player.scheduleBuffer(buffer, atTime = null, options = 0, completionHandler = null)

        players[audioData.id] = player
        buffers[audioData.id] = buffer
    }

    actual suspend fun play(audioId: String) {
        players[audioId]?.play()
    }

    actual suspend fun pause(audioId: String) {
        players[audioId]?.pause()
    }

    actual suspend fun stop(audioId: String) {
        players[audioId]?.stop()
    }

    actual suspend fun remove(audioId: String) {
        players[audioId]?.stop()
        players.remove(audioId)
        buffers.remove(audioId)
    }

    actual suspend fun release() {
        players.forEach { id, player ->
            player.stop()
        }
        players.clear()
        buffers.clear()
        audioEngine.stop()
    }

}