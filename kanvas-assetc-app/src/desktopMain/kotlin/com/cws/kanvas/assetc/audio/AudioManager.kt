package com.cws.kanvas.assetc.audio

import com.cws.kanvas.assetc.Sound
import com.cws.kanvas.assetc.SoundLoader
import kanvas.kanvas_assetc_app.generated.resources.Res
import java.net.URI

class AudioManager {

    private val context = AudioContext()
    private val soundPool = SoundPool(context, 16)
    private val soundCache = mutableMapOf<String, Sound>()

    fun create() {
        context.create()
        soundPool.create()
    }

    fun destroy() {
        soundCache.forEach { (filepath, sound) -> sound.release() }
        soundPool.destroy()
        context.destroy()
    }

    fun playRes(filepath: String) {
        if (!soundPool.hasSound(filepath)) {
            val uri = Res.getUri(filepath)
            val url = URI(uri).toURL()
            val sound = SoundLoader().loadWAV(url)
            if (sound?.format == null) return
            soundPool.addSound(filepath, sound)
            soundCache[filepath] = sound
        }
        soundPool.play(filepath)
    }

    fun pause(filepath: String) {
        soundPool.pause(filepath)
    }

    fun stop(filepath: String) {
        soundPool.stop(filepath)
    }

}
