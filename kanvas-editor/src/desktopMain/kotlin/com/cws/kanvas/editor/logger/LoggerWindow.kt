package com.cws.kanvas.editor.logger

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cws.kanvas.editor.docking.logic.DockWindowState
import com.cws.kanvas.editor.overlay.Overlay
import com.cws.kanvas.editor.ui.LocalTheme
import com.cws.kanvas.editor.ui.components.AppText
import com.cws.kanvas.editor.ui.styling.White
import com.cws.kanvas.editor.window.AppWindow

private val LoggerWindow = object : AppWindow(
    DockWindowState(
        id = "LoggerWindow",
        title = "Logger",
        canUndock = false,
        canClose = false,
    )
) {

    @Composable
    override fun content() {
        val loggerController = provideLoggerController()
        Overlay(
            modifier = Modifier.fillMaxSize(),
        ) {
            LoggerPanel(
                loggerController = loggerController,
            )
        }
    }

    @Composable
    fun LoggerPanel(loggerController: LoggerController) {
        val revision = loggerController.revision
        val style = LocalTheme.current.logsStyle

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(loggerController.getLogSize()) { i ->
                    loggerController.getLog(i)?.let { log ->
                        revision
                        AppText(
                            modifier = Modifier.fillMaxWidth(),
                            text = log.toString(),
                            style = style.textStyle.copy(
                                primaryColor = style.logLevelColors.getOrDefault(log.logLevel, White)
                            ),
                        )
                    }
                }
            }
        }
    }

}
