package com.cws.kanvas.assetc.audio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.cws.kanvas.assetc.core.Controller
import com.cws.kanvas.assetc.core.provideController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

val LocalAudioController = staticCompositionLocalOf<AudioController> {
    error("LocalAudioController is not initialized")
}

abstract class AudioController : Controller() {

    abstract fun play(effect: AudioEffect)

}

class AudioControllerImpl(
    private val audioManager: AudioManager = AudioManager(),
) : AudioController() {

    private var job: Job? = null

    override fun onCreate() {
        audioManager.create()
    }

    override fun onDestroy() {
        audioManager.destroy()
    }

    override fun play(effect: AudioEffect) {
        if (job?.isActive == true) return
        job = scope.launch(Dispatchers.IO) {
            audioManager.playRes(effect.filepath)
        }
    }

}

class AudioControllerPreview : AudioController() {
    override fun onCreate() {}
    override fun onDestroy() {}
    override fun play(effect: AudioEffect) {}
}

@Composable
fun provideAudioController(): AudioController = provideController(
    preview = { AudioControllerPreview() },
    impl = { AudioControllerImpl() },
)