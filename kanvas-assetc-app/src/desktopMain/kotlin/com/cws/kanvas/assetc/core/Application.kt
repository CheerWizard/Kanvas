@file:OptIn(ExperimentalComposeUiApi::class)

package com.cws.kanvas.assetc.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.cws.kanvas.assetc.docking.logic.DockSpaceState
import com.cws.kanvas.assetc.docking.ui.DockWindow
import com.cws.kanvas.assetc.shader.provideShaderController
import com.cws.kanvas.assetc.ui.Blue
import com.cws.kanvas.assetc.ui.Green
import com.cws.kanvas.assetc.ui.Pink
import com.cws.kanvas.assetc.ui.Red
import com.cws.kanvas.assetc.ui.White
import com.cws.kanvas.assetc.ui.Yellow
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Application(
    onClose: () -> Unit
) {
    val applicationController = provideApplicationController()
    val shaderController = provideShaderController()

    CompositionLocals {
        val dockController = LocalDockController.current

        DockWindow(
            id = "Host",
            title = "Host",
            dockSpace = DockSpaceState.Host(),
            onClose = onClose,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = White),
            )
        }

        DockWindow(
            id = "Blue",
            title = "Blue",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Blue),
            )
        }

        DockWindow(
            id = "Yellow",
            title = "Yellow",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Yellow),
            )
        }

        DockWindow(
            id = "Red",
            title = "Red",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Red),
            )
        }

        DockWindow(
            id = "Green",
            title = "Green",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Green),
            )
        }

        DockWindow(
            id = "Pink",
            title = "Pink",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Pink),
            )
        }

        LaunchedEffect(Unit) {
//            dockController.dock("Blue", "Host", DockSpaceSlot.Top)
//            dockController.dock("Red", "Host", DockSpaceSlot.Left)
//            dockController.dock("Green", "Host", DockSpaceSlot.Center)
//            dockController.dock("Pink", "Host", DockSpaceSlot.Right)
//            dockController.dock("Yellow", "Host", DockSpaceSlot.Bottom)
//            dockController.saveState()
        }
    }
}

@Preview
@Composable
fun Preview_Application() {
    CompositionLocals {
        DockWindow(
            id = "BlueWindow",
            title = "Blue",
            onClose = {},
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Blue),
            )
        }

        DockWindow(
            id = "YellowWindow",
            title = "Yellow",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Yellow),
            )
        }
    }
}
