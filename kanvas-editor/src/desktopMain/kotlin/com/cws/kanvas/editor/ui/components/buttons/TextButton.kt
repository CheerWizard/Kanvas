package com.cws.kanvas.editor.ui.components.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cws.kanvas.editor.ui.components.AppText
import com.cws.kanvas.editor.ui.components.AppTextStyle
import com.cws.kanvas.editor.ui.rememberInteractionState
import com.cws.kanvas.editor.ui.styling.ColorLightness

data class TextButtonStyle(
    val textStyle: AppTextStyle,
    val backgroundColor: Color,
    val lightness: ColorLightness,
)

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    style: TextButtonStyle,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val interactionState = rememberInteractionState(enabled = enabled)

    BaseButton(
        modifier = modifier,
        interactionState = interactionState,
        color = style.backgroundColor,
        lightness = style.lightness,
        onClick = onClick,
    ) {
        AppText(
            text = text,
            style = style.textStyle,
        )
    }
}
