package com.cws.kanvas.assetc.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.cws.kanvas.assetc.core.LocalTheme

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Unspecified,
    enabled: Boolean = true,
    primaryColor: Color = LocalTheme.current.textPrimary,
    secondaryColor: Color = LocalTheme.current.textSecondary,
    disabledColor: Color = LocalTheme.current.textDisabled,
) {
    val color = when {
        !enabled -> disabledColor
        else -> primaryColor
    }

    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style,
        color = color,
    )
}
