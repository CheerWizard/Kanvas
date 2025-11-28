package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples
import com.cws.printer.Printer
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.AVFAudio.AVAudioEngine
import platform.AVFAudio.AVAudioFormat
import platform.AVFAudio.AVAudioInputNode
import platform.AVFAudio.AVAudioPCMBuffer
import platform.Foundation.NSError

@OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
actual class AudioInputStream actual constructor() {

    companion object {
        private const val TAG = "AudioInputStream"
    }

    private var format: AVAudioFormat? = null
    private val audioEngine: AVAudioEngine = AVAudioEngine()
    private val recorder = AVAudioInputNode()
    private var buffer: AVAudioPCMBuffer? = null

    private val audioError = nativeHeap.alloc<ObjCObjectVar<NSError?>>()

    private var bus: ULong = 0u

    actual fun init(config: AudioConfig) {
        format = AVAudioFormat(
            channels = config.channel.size.toUInt(),
            standardFormatWithSampleRate = config.sampleRate.toDouble(),
        )
        audioEngine.attachNode(recorder)
        audioEngine.connect(recorder, audioEngine.mainMixerNode, format)
        audioEngine.prepare()
        startEngine()
    }

    private fun startEngine() {
        if (!audioEngine.isRunning()) {
            val success = audioEngine.startAndReturnError(audioError.ptr)
            if (!success) {
                val error = audioError.value
                if (error != null) {
                    Printer.d(TAG, "AVAudioEngine failed to initialize. ${error.localizedDescription}")
                } else {
                    Printer.d(TAG, "AVAudioEngine failed to initialize. Unknown Error!")
                }
            } else {
                Printer.d(TAG, "AVAudioEngine is initialized!")
            }
        }
    }

    actual fun start() {
        recorder.installTapOnBus(bus, bufferSize = frameCount.toUInt(), format = format) { buf, _ ->
            buffer.floatChannelData?.let { channelData ->
                buf.floatChannelData?.get(0)?.let { src ->
                    src.readBytes(channelData[0], buf.frameLength.toInt())
                }
            }

            val samples = ShortArray(buffer.frameLength.toInt()) { i ->
                (buffer.floatChannelData!![0]!![i] * Short.MAX_VALUE).toInt().toShort()
            }
        }
    }

    actual fun stop() {
        audioEngine.stop()
        recorder.removeTapOnBus(bus)
    }

    actual fun read(samples: AudioSamples, offset: Int, size: Int): Int {

    }

    actual fun release() {
        audioEngine.stop()
    }

}