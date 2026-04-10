package com.cws.kanvas.editor.ui.styling

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cws.kanvas.editor.ui.components.AppTextStyle

class AppTextStyles(
    fontFamily: FontFamily,
    theme: Theme,
) {
    val titleLarge = AppTextStyle(
        style = TextStyle(
            fontFamily = fontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
        ),
        primaryColor = theme.textPrimary,
        secondaryColor = theme.textSecondary,
        lightness = DefaultColorLightness,
    )

    val titleMedium = AppTextStyle(
        style = TextStyle(
            fontFamily = fontFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
        ),
        primaryColor = theme.textPrimary,
        secondaryColor = theme.textSecondary,
        lightness = DefaultColorLightness,
    )

    val bodyMedium = AppTextStyle(
        style = TextStyle(
            fontFamily = fontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
        ),
        primaryColor = theme.textPrimary,
        secondaryColor = theme.textSecondary,
        lightness = DefaultColorLightness,
    )

    val labelSmall = AppTextStyle(
        style = TextStyle(
            fontFamily = fontFamily,
            fontSize = 9.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 1.5.sp,
        ),
        primaryColor = theme.textPrimary,
        secondaryColor = theme.textSecondary,
        lightness = DefaultColorLightness,
    )

    val contextMenu = labelSmall

    val titleMenu = labelSmall
}
