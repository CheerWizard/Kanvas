package com.cws.kanvas.editor.ui.styling

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cws.kanvas.editor.logger.UiLogsStyle
import com.cws.kanvas.editor.ui.components.DropMenuStyle
import com.cws.kanvas.editor.ui.components.buttons.IconButtonStyle

data class Theme(
    val background: Color = SteelBackground,
    val surface: Color = SteelSurface,
    val surfaceElevated: Color = SteelSurfaceElevated,
    val border: Color = MetalBorder,

    val accent: Color = MoltenOrange,
    val accentLight: Color = MoltenOrangeLight,
    val accentDark: Color = MoltenOrangeDark,

    val textPrimary: Color = TextPrimary,
    val textSecondary: Color = TextSecondary,

    val dockOverlay: Color = DockOverlay,

    val gridMinor: Color = GridMinor,
    val gridMajor: Color = GridMajor,

    val windowAccentGlow: Color = AccentGlow,

    val windowButtonStyle: IconButtonStyle = IconButtonStyle(
        backgroundColor = MetalBorder,
        iconColor = MetalLight,
        lightness = DefaultColorLightness,
    ),

    val windowCloseButtonStyle: IconButtonStyle = IconButtonStyle(
        backgroundColor = MetalBorder,
        iconColor = MetalLight,
        lightness = DefaultColorLightness,
    ),

    val titleBarBackground: Color = SteelSurface,
    val titleBarBorder: Color = MetalBorder,

    val contextMenuStyle: DropMenuStyle = DropMenuStyle(
        backgroundColor = SteelSurfaceElevated,
        itemGradient = ItemGradient,
        itemSelectedGradient = ItemSelectedGradient,
        lightness = DefaultColorLightness,
    ),

    val titleMenuStyle: DropMenuStyle = DropMenuStyle(
        backgroundColor = SteelSurfaceElevated,
        itemGradient = ItemGradient,
        itemSelectedGradient = ItemSelectedGradient,
        lightness = DefaultColorLightness,
    ),

    // Elevations
    val elevationNone: Dp = 0.dp,
    val elevationLow: Dp = 2.dp,
    val elevationMedium: Dp = 6.dp,
    val elevationHigh: Dp = 12.dp,

    // Shadows
    val shadowAmbient: Color = Color.Black.copy(alpha = 0.55f),
    val shadowSpot: Color = Color.Black.copy(alpha = 0.75f),

    // Text selection
    val textSelectionColors: TextSelectionColors = TextSelectionColors(
        handleColor = MoltenGold,
        backgroundColor = MoltenGold.copy(alpha = 0.35f),
    ),

    // Bloom
    val bloomCore: Color = MoltenGold,
    val bloomGlow: Color = LavaOrange,
    val bloomCoreAlpha: Float = 0.9f,
    val bloomGlowAlpha: Float = 0.35f,
    val bloomHaloAlpha: Float = 0.12f,

    // Dividers
    val dividerColors: Array<Pair<Float, Color>> = arrayOf(
        0.0f to SteelDark,
    ),

    val dividerActiveColors: Array<Pair<Float, Color>> = arrayOf(
        0.0f to Transparent,
        0.3f to LavaOrange,
        0.4f to MoltenGold,
        0.6f to MoltenGold,
        0.7f to LavaOrange,
        1.0f to Transparent,
    ),

    val logsStyle: UiLogsStyle = UiLogsStyle(
        textStyle =
    )
)
