@file:OptIn(ExperimentalComposeUiApi::class)

package com.cws.kanvas.editor.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.cws.kanvas.editor.dialog.DialogRegistry
import com.cws.kanvas.editor.ui.CompositionLocals
import com.cws.kanvas.editor.window.WindowRegistry
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Application(
    onExitApplication: () -> Unit
) {
    CompositionLocals {
        WindowRegistry()
        DialogRegistry()
    }
}

@Preview
@Composable
fun Preview_Application() {
    Application {}
}
