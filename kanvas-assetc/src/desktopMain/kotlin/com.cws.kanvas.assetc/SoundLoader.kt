package com.cws.kanvas.assetc

import com.cws.print.Print
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.pushBytes
import org.lwjgl.stb.STBVorbis.*
import java.io.File
import java.nio.IntBuffer
import javax.sound.sampled.AudioSystem

enum class SoundFormat {
    WAV,
    OGG
}

data class Sound(
    val format: SoundFormat,
    val channels: Int,
    val sampleRate: Int,
    val buffer: NativeBuffer,
)

class SoundLoader {

    companion object {
        private const val TAG = "SoundLoader"
    }

    fun load(filepath: String, format: SoundFormat): Sound? {
        return when (format) {
            SoundFormat.WAV -> loadWAV(filepath)
            SoundFormat.OGG -> loadOGG(filepath)
        }
    }

    private fun loadWAV(filepath: String): Sound? {
        try {
            val file = File(filepath)
            val audioInputStream = AudioSystem.getAudioInputStream(file)
            val bytes = audioInputStream.readAllBytes()
            val buffer = NativeBuffer(bytes.size)
            buffer.pushBytes(bytes)
            return Sound(
                format = SoundFormat.WAV,
                channels = audioInputStream.format.channels,
                sampleRate = audioInputStream.format.sampleRate.toInt(),
                buffer = buffer,
            )
        } catch (e: Exception) {
            Print.e(TAG, "Failed to load WAV sound from $filepath", e)
        }
        return null
    }

    private fun loadOGG(filepath: String): Sound? {
        val sampleRatePtr = IntBuffer.allocate(1)
        val channelsPtr = IntBuffer.allocate(1)
        val shortBuffer = stb_vorbis_decode_filename(filepath, channelsPtr, sampleRatePtr)

        if (shortBuffer == null) {
            Print.e(TAG, "Failed to load sound from $filepath")
            return null
        }

        val buffer = NativeBuffer(shortBuffer.capacity() * Short.SIZE_BYTES)
        buffer.copy(shortBuffer)

        return Sound(
            format = SoundFormat.OGG,
            channels = channelsPtr[0],
            sampleRate = sampleRatePtr[0],
            buffer = buffer,
        )
    }

}
