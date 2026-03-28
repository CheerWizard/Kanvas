package com.cws.kanvas.assetc.app.ui

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
import com.cws.kanvas.assetc.docking.logic.DockSpaceState
import com.cws.kanvas.assetc.docking.logic.DockWindowState
import com.cws.kanvas.assetc.docking.ui.DockWindow
import com.cws.kanvas.assetc.ui.LocalTheme
import com.cws.kanvas.assetc.ui.effects.bloom
import com.cws.kanvas.assetc.ui.bloomBorders
import com.cws.kanvas.assetc.ui.styling.Gradient
import com.cws.kanvas.assetc.ui.styling.TopBarBackground
import com.cws.kanvas.assetc.ui.styling.surface

object AppWindow {

    val ID = AppWindow::class.simpleName.orEmpty()

    private val state = DockWindowState(
        id = ID,
        title = "Kanvas",
        dockSpace = DockSpaceState.Host(),
        canUndock = false,
    )

    @Composable
    fun render(
        onExitApplication: () -> Unit,
    ) {
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

        DockWindow(
            state = state,
            onClose = onExitApplication,
            titleBar = { state ->
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
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .surface(LocalTheme.current.elevationLow),
            )
        }
    }

}
