package com.cws.kanvas.editor.app.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.cws.kanvas.editor.docking.logic.DockSpaceState
import com.cws.kanvas.editor.docking.logic.DockWindowState
import com.cws.kanvas.editor.ui.LocalTheme
import com.cws.kanvas.editor.ui.effects.bloom
import com.cws.kanvas.editor.ui.bloomBorders
import com.cws.kanvas.editor.ui.styling.Gradient
import com.cws.kanvas.editor.ui.styling.TopBarBackground
import com.cws.kanvas.editor.ui.styling.surface
import com.cws.kanvas.editor.window.AppWindow

private val MainWindow = object : AppWindow(
    DockWindowState(
        id = "MainWindow",
        title = "Kanvas",
        dockSpace = DockSpaceState.Host(),
        canUndock = false,
    )
) {

    @Composable
    override fun titleBar(state: DockWindowState) {
        var topBarGradient by remember { mutableStateOf(TopBarBackground) }

        val infiniteTransition = rememberInfiniteTransition()

        val logoPulseBloom by infiniteTransition.animateFloat(
            initialValue = 0.7f,
            targetValue = 0.75f,
            animationSpec = infiniteRepeatable(
                animation = tween(200),
                repeatMode = RepeatMode.Reverse
            )
        )

        AppWindowTitleBar(
            modifier = Modifier
                .fillMaxWidth()
                .surface(
                    elevation = LocalTheme.current.elevationLow,
                    baseGradient = topBarGradient,
                )
                .bloom(radius = 50.dp * logoPulseBloom, center = Offset(16f, 16f))
                .bloomBorders()
                .onGloballyPositioned {
                    val gradient = topBarGradient as Gradient.Linear
                    topBarGradient = gradient.copy(
                        end = Offset(it.size.width.toFloat(), it.size.height.toFloat())
                    )
                },
            state = state,
        )
    }

    @Composable
    override fun content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .surface(LocalTheme.current.elevationLow),
        )
    }

}
