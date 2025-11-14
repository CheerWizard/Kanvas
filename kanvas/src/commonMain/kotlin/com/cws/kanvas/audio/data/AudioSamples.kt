package com.cws.kanvas.audio.data

class AudioSamples(
    val size: Int,
) {
    var shorts: ShortArray = ShortArray(size)
    var bytes: ByteArray = ByteArray(size * Short.SIZE_BYTES)

    fun fillBytes() {
        for (i in shorts.indices) {
            val value = shorts[i].toInt()
            bytes[i * 2] = (value and 0xFF).toByte()
            bytes[i * 2 + 1] = ((value ushr 8) and 0xFF).toByte()
        }
    }

    fun resize(size: Int) {
        shorts = shorts.copyOf(size)
        bytes = bytes.copyOf(size * Short.SIZE_BYTES)
    }

}