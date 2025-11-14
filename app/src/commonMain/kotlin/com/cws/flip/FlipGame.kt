package com.cws.flip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cws.kanvas.audio.data.AudioData
import com.cws.kanvas.core.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlipGame : Game() {

    private val audioData = AudioData(id = "testing")

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun onUpdate(dtMillis: Float) {
    }

    override fun onRender(dtMillis: Float) {
    }

    @Composable
    override fun onRenderUI() {
        val scope = rememberCoroutineScope { Dispatchers.Default }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .height(32.dp)
                            .clickable(
                                onClick = {
                                    scope.launch {
                                        engine.audioRecorder.start(audioData)
                                    }
                                }
                            )
                    ) {
                        BasicText(
                            text = "Start Recording",
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .height(32.dp)
                            .clickable(
                                onClick = {
                                    scope.launch {
                                        engine.audioRecorder.stop()
                                    }
                                }
                            )
                    ) {
                        BasicText(
                            text = "Stop Recording",
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .height(32.dp)
                            .clickable(
                                onClick = {
                                    scope.launch {
                                        engine.audioPlayer.write(audioData)
                                        engine.audioPlayer.play(audioData.id)
                                    }
                                }
                            )
                    ) {
                        BasicText(
                            text = "Start Playing",
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .height(32.dp)
                            .clickable(
                                onClick = {
                                    scope.launch {
                                        engine.audioPlayer.stop(audioData.id)
                                    }
                                }
                            )
                    ) {
                        BasicText(
                            text = "Stop Playing",
                        )
                    }
                }
            }
        }
    }

}