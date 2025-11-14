@file:OptIn(ExperimentalForeignApi::class)

package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples
import com.cws.printer.Printer
import kotlinx.cinterop.*
import platform.AudioToolbox.*
import platform.CoreAudio.*
import platform.AVFoundation.*
import platform.CoreAudioTypes.AudioBufferList
import platform.darwin.*
import platform.posix.memset

actual class AudioOutputStream {

    companion object {
        private const val TAG = "PlatformAudioPlayer"

        private val writeSamplesCallback = staticCFunction { ref, _, _, _, frames, data ->
            val instance = ref.asStableRef<AudioOutputStream>().get()
            instance.writeSamples(data, frames)
            noErr
        }
    }

    enum class State {
        PLAYING,
        PAUSED,
        STOPPED,
    }

    private var audioUnit: AudioUnit? = null

    // TODO calculate size based on config
    private val ringBuffer = FloatRingBuffer(16384)

    private var state: State = State.STOPPED

    private var config: AudioConfig? = null

    actual fun init(config: AudioConfig) {
        this.config = config

        memScoped {
            val desc = alloc<AudioComponentDescription>().apply {
                componentType = kAudioUnitType_Output
                componentSubType = kAudioUnitSubType_RemoteIO
                componentManufacturer = kAudioUnitManufacturer_Apple
                componentFlags = 0u
                componentFlagsMask = 0u
            }

            val component = AudioComponentFindNext(null, desc.ptr)

            if (component == null) {
                Printer.e(TAG, "No RemoteIO component found!")
                return
            }

            val unitPtr = alloc<AudioUnitVar>()
            AudioComponentInstanceNew(component, unitPtr.ptr)

            audioUnit = unitPtr.value

            if (audioUnit == null) {
                Printer.e(TAG, "Failed to create AudioUnit!")
            }

            val callbackStruct = alloc<AURenderCallbackStruct>()
            callbackStruct.inputProc = writeSamplesCallback
            callbackStruct.inputProcRefCon = StableRef.create(this).asCPointer()
            AudioUnitSetProperty(
                audioUnit,
                kAudioUnitProperty_SetRenderCallback,
                kAudioUnitScope_Input,
                0u,
                callbackStruct.ptr,
                sizeOf<AURenderCallbackStruct>().toUInt()
            )

            val format = alloc<AudioStreamBasicDescription>().apply {
                mSampleRate = config.sampleRate.toDouble()
                mFormatID = kAudioFormatLinearPCM
                mFormatFlags = (kAudioFormatFlagIsFloat or kAudioFormatFlagIsPacked)
                mFramesPerPacket = 1u
                mChannelsPerFrame = config.channel.size.toUInt()
                mBitsPerChannel = 32u
                mBytesPerFrame = (4 * config.channel.size).toUInt()
                mBytesPerPacket = mBytesPerFrame
            }

            AudioUnitSetProperty(
                audioUnit,
                kAudioUnitProperty_StreamFormat,
                kAudioUnitScope_Input,
                0u,
                format.ptr,
                sizeOf<AudioStreamBasicDescription>().toUInt()
            )

            AudioUnitInitialize(audioUnit)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun write(samples: AudioSamples, offset: Int, size: Int): Int {
        return ringBuffer.write(samples.shorts, offset, size)
    }

    actual fun play() {
        if (state == State.STOPPED) {
            AudioOutputUnitStart(audioUnit)
        }
        state = State.PLAYING
    }

    actual fun pause() {
        state = State.PAUSED
    }

    actual fun stop() {
        if (state == State.PLAYING || state == State.PAUSED) {
            state = State.STOPPED
            AudioOutputUnitStop(audioUnit)
        }
    }

    actual fun release() {
        stop()
    }

    fun writeSamples(data: CPointer<AudioBufferList>?, frames: UInt32) {
        if (data == null || config == null) return

        // Fill with silence to emulate pause/resume
        if (!playing) {
            memset(data.pointed.mBuffers[0].mData, 0, data.pointed.mBuffers[0].mDataByteSize.toInt())
            return
        }

        val frameCount = frames.toInt()
        val dst = data.pointed.mBuffers.reinterpret<FloatVar>() ?: return
        val available = ringBuffer.read(dst, frameCount * channels)

        // If fewer frames than requested, fill remainder with silence
        if (available < frameCount * channels) {
            val silencePtr = dst + available
            silencePtr.memset(0, (frameCount * channels - available) * 4)
        }
    }

}