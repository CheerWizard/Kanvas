package com.cws.kanvas.editor.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.WindowScope

data class DialogState(
    val title: String,
    val size: DpSize,
    val onClose: () -> Unit = {},
    val titleBar: @Composable WindowScope.() -> Unit = {
        DialogWindowTitleBar(title = title)
    },
    val content: @Composable () -> Unit,
)
