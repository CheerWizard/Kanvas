package com.cws.kanvas.audio

import com.cws.kanvas.audio.data.AudioConfig
import com.cws.kanvas.audio.data.AudioSamples
import com.cws.kanvas.core.PlatformFile

class FileAudioSource : AudioSource {

    private val file = PlatformFile()

    override fun open() {

    }

    override fun close() {

    }

    override fun read(config: AudioConfig) {}

    override fun read(
        samples: AudioSamples,
        offset: Int,
        size: Int,
    ) {}

    override fun write(config: AudioConfig) {

    }

    override fun write(
        samples: AudioSamples,
        offset: Int,
        size: Int,
    ) {}

}