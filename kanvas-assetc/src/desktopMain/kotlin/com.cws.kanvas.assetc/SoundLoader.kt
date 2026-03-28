package com.cws.kanvas.assetc

import com.cws.kanvas.assetc.audio.AudioFormat
import com.cws.print.Print
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.pushBytes
import org.lwjgl.stb.STBVorbis.*
import java.io.File
import java.net.URL
import java.nio.IntBuffer
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem

data class Sound(
    val format: AudioFormat?,
    val channels: Int,
    val sampleRate: Int,
    val samples: NativeBuffer,
) {

    fun release() {
        samples.release()
    }

}

class SoundLoader {

    companion object {
        private const val TAG = "SoundLoader"
    }

    fun loadWAV(filepath: String): Sound? {
        try {
            val file = File(filepath)
            val stream = AudioSystem.getAudioInputStream(file)
            return decodeWAV(stream)
        } catch (e: Exception) {
            Print.e(TAG, "Failed to load WAV sound from $filepath", e)
        }
        return null
    }

    fun loadWAV(url: URL): Sound? {
        try {
            val stream = AudioSystem.getAudioInputStream(url)
            return decodeWAV(stream)
        } catch (e: Exception) {
            Print.e(TAG, "Failed to load WAV sound from $url", e)
        }
        return null
    }

    fun loadWAV(data: ByteArray): Sound? {
        try {
            val stream = AudioSystem.getAudioInputStream(data.inputStream())
            return decodeWAV(stream)
        } catch (e: Exception) {
            Print.e(TAG, "Failed to load WAV sound from bytes $data", e)
        }
        return null
    }

    private fun decodeWAV(stream: AudioInputStream): Sound? {
        try {
            val channels = stream.format.channels
            val sampleSizeInBits = stream.format.sampleSizeInBits
            val sampleRate = stream.format.sampleRate.toInt()
            val bytes = stream.readAllBytes()
            val buffer = NativeBuffer(bytes.size)
            buffer.pushBytes(bytes)

            val format = when {
                channels == 1 && sampleSizeInBits == 8 -> AudioFormat.Mono8
                channels == 1 && sampleSizeInBits == 16 -> AudioFormat.Mono16
                channels == 2 && sampleSizeInBits == 8 -> AudioFormat.Stereo8
                channels == 2 && sampleSizeInBits == 16 -> AudioFormat.Stereo16
                else -> null
            }

            return Sound(
                format = format,
                channels = channels,
                sampleRate = sampleRate,
                samples = buffer,
            )
        } catch (e: Exception) {
            Print.e(TAG, "Failed to decode WAV sound", e)
        }
        return null
    }

    fun loadOGG(filepath: String): Sound? {
        val sampleRatePtr = IntBuffer.allocate(1)
        val channelsPtr = IntBuffer.allocate(1)
        val shortBuffer = stb_vorbis_decode_filename(filepath, channelsPtr, sampleRatePtr)

        if (shortBuffer == null) {
            Print.e(TAG, "Failed to load sound from $filepath")
            return null
        }

        val buffer = NativeBuffer(shortBuffer.capacity() * Short.SIZE_BYTES)
        buffer.copy(shortBuffer)

        val channels = channelsPtr[0]
        val sampleRate = sampleRatePtr[0]
        val format = when (channels) {
            1 -> AudioFormat.Mono16
            2 -> AudioFormat.Stereo16
            else -> null
        }

        return Sound(
            format = format,
            channels = channels,
            sampleRate = sampleRate,
            samples = buffer,
        )
    }

}
