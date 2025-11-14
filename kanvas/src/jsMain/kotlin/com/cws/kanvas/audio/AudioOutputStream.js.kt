package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
actual class AudioOutputStream {

    private var context: dynamic = null
    private var player: dynamic = null

    actual fun init(config: AudioConfig) {
        GlobalScope.launch {
            // Load player module and initialize it
            context = js("new (window.AudioContext || window.webkitAudioContext)()")
            context.asDynamic().audioWorklet.addModule("player-processor.js").await()
            player = js("new AudioWorkletNode(context, 'player-processor')")
            // Bind player to audio context
            player.connect(context.destination)
        }
    }

    actual fun write(samples: AudioSamples, offset: Int, size: Int): Int {
        player?.port?.postMessage(samples.shorts, offset, size)
        return size
    }

    actual fun play() {
        if (context?.state == "suspended") {
            context.resume()
        }
        player?.port?.postMessage("resume")
    }

    actual fun pause() {
        player?.port?.postMessage("pause")
    }

    actual fun stop() {
        player?.disconnect()
    }

    actual fun release() {
        stop()
        context?.close()
        context = null
        player = null
    }

}