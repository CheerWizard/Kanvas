package com.cws.kanvas.assetc.ui

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

class TextStyles(
    fontFamily: FontFamily
) {
    val title = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
    )
    val body = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp,
    )
}
