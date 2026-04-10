package com.cws.kanvas.editor.logger

import androidx.compose.ui.graphics.Color
import com.cws.kanvas.editor.ui.components.AppTextStyle
import com.cws.print.LogLevel

data class UiLogsStyle(
    val logLevelColors: Map<LogLevel, Color>,
    val textStyle: AppTextStyle,
)
