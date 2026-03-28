package com.cws.kanvas.assetc.overlay

import androidx.compose.runtime.Composable

class HoverOverlayState(
    val id: String,
    val content: @Composable () -> Unit,
)
