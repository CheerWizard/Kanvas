package com.cws.kanvas.assetc.ui.styling

import androidx.compose.ui.graphics.Color

// Base colors
val White = Color(255, 255, 255, 255)
val Grey = Color(128, 128, 128, 255)
val Black = Color(0, 0, 0, 255)
val Transparent = Color(255, 255, 255, 0)

// Grid
val GridMinor = Color(0xFF23252B)
val GridMajor = Color(0xFF3A3D45)

// Dock overlay
val DockOverlay = Color(0x40FF7A1A)

// Active UI, highlights, selection
val MoltenOrange = Color(0xFFFF7A1A)
val MoltenOrangeLight = Color(0xFFFFC06A)
val MoltenOrangeDark = Color(0xFFA82600)
val MoltenGold = Color(0xFFFFC247)
val LavaOrange = Color(0xFFFF5A0A)
val LavaDeep = Color(0xFFD93800)

// Active tab text, highlights, indicators
val EnergyYellow = Color(0xFFF4C14B)
val EnergySoft = Color(0xFFF6D46B)

// Surfaces, background
val SteelDark = Color(0xFF1A1B1E)
val SteelPanel = Color(0xFF232429)
val SteelElevated = Color(0xFF2D2F35)
val SteelBackground = Color(0xFF151619)
val SteelSurface = Color(0xFF1F2126)
val SteelSurfaceElevated = Color(0xFF2A2D33)

// Sliders, outlines, separators UI
val MetalLight = Color(0xFFB8BBC5)
val MetalMid = Color(0xFF9EA2AA)
val MetalDark = Color(0xFF3F3F3F)
val MetalBorder = Color(0xFF3A3D45)

// Text colors
val TextPrimary = Color(0xFFE6E6E6)
val TextSecondary = Color(0xFF9B9B9B)
val TextAccent = Color(0xFFF4C14B)
val TextHot = Color(0xFFFF7A1A)

// Window button colors
val WindowButton = Color(0xFF3F3F3F)

// focused input fields, selected objects, active panels
val AccentGlow = Color(0x33FF7A1A)

val TabTextActive = Color(0xFFFFE2A0)

val ScrollTrack = Color(0xFF1A1B1E)

val ShadowBorder = Color(0xFF15171C)

val BaseBorder = Color(0xFF3A3D46)

val InnerTopHighlight = Color.White.copy(alpha = 0.08f)

val InnerBottomHighLight = Color.Black.copy(alpha = 0.25f)

val PanelBorderShadow = Color(0xFF15171C)
val PanelBorderHighlight = MetalLight
val PanelInnerTopHighlight = EnergyYellow.copy(alpha = 0.5f)
val PanelInnerBottomHighlight = Black.copy(alpha = 0.25f)
val PanelCornerHighlight = EnergySoft.copy(alpha = 0.5f)
val PanelCornerHighlight2 = White.copy(alpha = 0.2f)
const val PanelNoiseAlpha = 0.03f
