package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioData
import com.cws.kanvas.core.`PlatformFile.desktop.kt`

class AudioFile {

    private val decoder = AudioDecoder()

    fun read(
        filepath: String,
        format: AudioFileFormat
    ): AudioData {
        val file = `PlatformFile.desktop.kt`(filepath)
        val audioData = AudioData(

        )
        file.read()
    }

    fun write(filepath: String, format: AudioFileFormat): AudioData{

    }

}
