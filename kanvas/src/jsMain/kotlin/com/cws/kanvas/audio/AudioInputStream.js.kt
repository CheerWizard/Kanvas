package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.khronos.webgl.Float32Array
import org.khronos.webgl.get
import org.w3c.dom.mediacapture.MediaStream

@OptIn(DelicateCoroutinesApi::class)
actual class AudioInputStream {

    private var context: dynamic = null
    private var audioWorklet: dynamic = null
    private var audioSource: dynamic = null
    private val samplesQueue = ArrayDeque<AudioSamples>()

    actual fun init(config: AudioConfig) {
        GlobalScope.launch {
            // Request microphone access
            val stream = window.navigator.mediaDevices
                .getUserMedia(js("{ audio: true }"))
                .await() as MediaStream

            // Load microphone module and initialize it
            context = js("new (window.AudioContext || window.webkitAudioContext)()")
            context.asDynamic().audioWorklet.addModule("mic-processor.js").await()
            audioWorklet = js("new AudioWorkletNode(context, 'mic-processor')")

            // Callback to listen to and push new samples
            audioWorklet.port.onmessage = { event ->
                val samplesFloat = event.asDynamic().data.unsafeCast<Float32Array>()
                val samples = AudioSamples(samplesFloat.length)
                for (i in 0..samples.size - 1) {
                    samples.shorts[i] = (samplesFloat[i].coerceIn(-1f, 1f) * Short.MAX_VALUE)
                        .toInt().toShort()
                }
                samplesQueue.addFirst(samples)
            }

            // Bind audio source and worklet to context
            audioSource = context.createMediaStreamSource(stream)
            audioSource.connect(audioWorklet)
            audioWorklet.connect(context.destination)
        }
    }

    actual fun start() {
        if (context?.state == "suspended") {
            context.resume()
        }
    }

    actual fun stop() {
        audioSource?.disconnect()
        audioWorklet?.disconnect()
    }

    actual fun read(samples: AudioSamples, offset: Int, size: Int): Int {
        var removed: AudioSamples? = null
        while (removed == null) {
            if (samplesQueue.last().size == size) {
                removed = samplesQueue.removeLast()
                break
            }
        }
        if (removed == null) return 0

        for (i in 0..size - 1) {
            if (i + offset < samples.shorts.size) {
                samples.shorts[i + offset] = removed.shorts[i]
            } else {
                return i
            }
        }

        return size
    }

    actual fun release() {
        stop()
        context?.close()
        context = null
        audioWorklet = null
        audioSource = null
    }

}