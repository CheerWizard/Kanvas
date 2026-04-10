package com.cws.kanvas.editor.overlay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpOffset

class ClickOverlayState(
    val id: String,
    val content: @Composable () -> Unit,
) {
    var position by mutableStateOf(DpOffset.Zero)
}