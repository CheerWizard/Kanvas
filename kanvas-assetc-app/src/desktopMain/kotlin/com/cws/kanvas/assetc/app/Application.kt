@file:OptIn(ExperimentalComposeUiApi::class)

package com.cws.kanvas.assetc.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.cws.kanvas.assetc.app.ui.AppWindow
import com.cws.kanvas.assetc.dialog.DialogRegistry
import com.cws.kanvas.assetc.filebrowser.FileBrowserWindow
import com.cws.kanvas.assetc.ui.CompositionLocals
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Application(
    onExitApplication: () -> Unit
) {
    CompositionLocals {
        AppWindow.render(
            onExitApplication = onExitApplication,
        )
        FileBrowserWindow.render()
        DialogRegistry()
    }
}

@Preview
@Composable
fun Preview_Application() {
    CompositionLocals {
        AppWindow.render(
            onExitApplication = {},
        )
    }
}
