package com.cws.kanvas.editor.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cws.kanvas.editor.docking.logic.DockWindowState
import com.cws.kanvas.editor.logger.provideLoggerController
import com.cws.kanvas.editor.overlay.Overlay
import com.cws.kanvas.editor.window.AppWindow

private val LoggerWindow = object : AppWindow(
    DockWindowState(
        id = "LoggerWindow",
        title = "Logger",
        canUndock = false,
        canClose = false,
        onDragAndDrop = {},
    )
) {

    @Composable
    override fun content() {
        val loggerController = provideLoggerController()

        Overlay(
            modifier = Modifier.fillMaxSize(),
        ) {
            FileBrowserPanel(
                fileBrowserController = fileBrowserController,
            )
        }
    }

}