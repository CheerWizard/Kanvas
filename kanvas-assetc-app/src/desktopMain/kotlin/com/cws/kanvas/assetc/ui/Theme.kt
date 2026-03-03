package com.cws.kanvas.assetc.ui

import androidx.compose.ui.graphics.Color

data class Theme(
    val background: Color = Color(0xFF1B1D22),
    val surface: Color = Color(0xFF26282F),
    val surfaceElevated: Color = Color(0xFF2D3038),
    val border: Color = Color(0xFF34363E),

    val accent: Color = Color(0xFFE0142A),
    val accentHover: Color = Color(0xFFFF3B47),
    val accentPressed: Color = Color(0xFF8B000F),
    val accentLight: Color = Color(0xFFFF6A73),
    val accentSoft: Color = Color(0xFFF2A7AB),

    val textPrimary: Color = Color(0xFFE6E6E6),
    val textSecondary: Color = Color(0xFFA9ABB3),
    val textDisabled: Color = Color(0xFF6B6E77),

    val dockOverlay: Color = Color(0x40E0142A),

    val gridMinor: Color = Color(0xFF2A2C33),
    val gridMajor: Color = Color(0xFF34363E),

    val accentGradientStart: Color = Color(0xFFFF3B47),
    val accentGradientEnd: Color = Color(0xFF8B000F),

    val windowAccentGlow: Color = Color(0x33E0142A),

    val windowButtonStyle: IconButtonStyle = IconButtonStyle(
        backgroundColor = Color(0xFF3A3D45),
        backgroundPressedColor = Color(0xFF2F3138),
        backgroundHoverColor = Color(0xFF4A4E57),
        iconColor = Color(0xFFB8BBC5),
        iconHoverColor = Color(0xFFFFFFFF),
        iconPressedColor = Color(0xFFFFFFFF),
        borderColor = border,
    ),

    val windowCloseButtonStyle: IconButtonStyle = IconButtonStyle(
        backgroundColor = accent,
        backgroundPressedColor = accentPressed,
        backgroundHoverColor = accentHover,
        iconColor = Color(0xFFB8BBC5),
        iconHoverColor = Color(0xFFFFFFFF),
        iconPressedColor = Color(0xFFFFFFFF),
        borderColor = border,
    ),

    val titleBarBackground: Color = Color(0xFF26282F),
    val titleBarBorder: Color = Color(0xFF34363E),
)
